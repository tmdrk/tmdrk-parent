package org.tmdrk.toturial.spring.es;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Splitter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tmdrk.toturial.spring.es.dto.ProductSalesVolumeDTO;
import org.tmdrk.toturial.spring.es.dto.ProductSpuEsDTO;
import org.tmdrk.toturial.spring.es.dto.ProductSpuQueryDTO;

import java.util.*;

/**
 * The type Es product spu service.
 *
 * @author William
 */
@Slf4j
@Service
public class EsProductSpuServiceImpl extends EsBaseServiceImpl<ProductSpuEsDTO, ProductSpuQueryDTO> implements IEsProductSpuService {

//    @Autowired
//    private IProductSpuService productSpuService;
    @Value("${es.index:ghjf_product}")
    private String esIndex;
    /**
     * 综合排序脚本
     */
    @Value("${es.order.synthesize.script:}")
    private String synthesizeOrderScript;
    @Value("${es.order.synthesize.supplierId:0}")
    private Long synthesizeOrderSupplierId;

    private final List<String> SORT_KEYS = Arrays.asList("pointPrice", "totalSalesVolume", "shelveTime", "createTime",
            "secKillList.sort", "themeList.sort", "monthSalesVolume", "daySalesVolume", "recentSalesVolume", "synthesize");
    /**
     * 默认排序字段
     */
    private final String[] DEFAULT_DESC_KEYS = {"synthesize"};

    private final String SCRIPT_LANG = "painless";

    @Override
    protected StringBuilder getEndPoint(String point) {
        StringBuilder sb = new StringBuilder();
        sb.append("/").append(esIndex).append("/_doc/").append(point);
        return sb;
    }

    @Override
    public Long save(ProductSpuEsDTO spuDTO) {
        typeConvert(spuDTO);
        ignoreField(spuDTO);
        return super.save(spuDTO);
    }

    /**
     * Update long.
     * 改
     *
     * @param spuDTO the t
     * @return the long
     */
    @Override
    public Long update(ProductSpuEsDTO spuDTO) {
        typeConvert(spuDTO);
        ignoreField(spuDTO);
        return super.update(spuDTO);
    }

    @Override
    public ProductSpuEsDTO details(@NonNull Long id) {
        ProductSpuEsDTO details = super.details(id);
        // 当日与当月销量处理，默认为0
        if (details.getMonthSalesVolume() == null) {
            details.setMonthSalesVolume(0);
        }
        if (details.getDaySalesVolume() == null) {
            details.setDaySalesVolume(0);
        }
        if (details.getRecentSalesVolume() == null) {
            details.setRecentSalesVolume(0);
        }
        return details;
    }

    @Override
    public IPage<ProductSpuEsDTO> search(ProductSpuQueryDTO productSpuQueryDTO, IPage<ProductSpuEsDTO> page) {
        IPage<ProductSpuEsDTO> search = super.search(productSpuQueryDTO, page);
        for (ProductSpuEsDTO dto : search.getList()) {
            // 当日、当月与最近销量处理，默认为0
            if (dto.getMonthSalesVolume() == null) {
                dto.setMonthSalesVolume(0);
            }
            if (dto.getDaySalesVolume() == null) {
                dto.setDaySalesVolume(0);
            }
            if (dto.getRecentSalesVolume() == null) {
                dto.setRecentSalesVolume(0);
            }
        }
        return search;
    }

    /**
     * Query params pack string.
     * 结构化查询条件封装 子类实现
     *
     * @param t
     * @param page
     * @return the string
     */
    @Override
    protected JSONObject queryParamsPack(int action, ProductSpuQueryDTO t, IPage<ProductSpuEsDTO> page) {
        boolean specialType = false;
        List<JSONObject> mustArray = new ArrayList<>();
        mustArray.add(createTerm("del", "false"));
        mustArray.add(createTerm("auditStatus", "success"));
        mustArray.add(createTerm("saleStatus", "shelve"));
        if (StringUtils.isBlank(t.getPlatform())) {
            mustArray.add(createTerm("platforms", "1"));
        } else {
            mustArray.add(createTerm("platforms", t.getPlatform()));
        }
        if (t.getSpuType() != null) {
            mustArray.add(createTerm("spuType", t.getSpuType().toString()));
        }
        if (t.getBrandId() != null && t.getBrandId() > 0) {
            mustArray.add(createTerm("brandId", t.getBrandId().toString()));
        }
        if (StringUtils.isNotEmpty(t.getBrandName())) {
            mustArray.add(createTerm("brandName", t.getBrandName()));
        }
        if (t.getCategoryId() != null && t.getCategoryId() > 0) {
            mustArray.add(createTerm("categoryIds", t.getCategoryId().toString()));
        }
        if (StringUtils.isNotEmpty(t.getSpuName())) {
            List<JSONObject> shouldArray = new ArrayList<>();
            shouldArray.add(createMatch("spuName", t.getSpuName(), "and"));
            shouldArray.add(createTerm("labelList.keyword", t.getSpuName()));
            mustArray.add(createBool(null, null, shouldArray, null));
        }
        if (StringUtils.isNotEmpty(t.getThemes())) {
            specialType = true;
            mustArray.add(createNestedTerm("themeList", "id", t.getThemes()));
        }
        if (StringUtils.isNotEmpty(t.getSecKill())) {
            specialType = true;
            mustArray.add(createNestedTerm("secKillList", "id", t.getSecKill()));
        }
        if (t.getIds() != null && t.getIds().length > 0) {
            mustArray.add(createTerms("id", Arrays.asList(t.getIds())));
        }
        if (t.getCategoryIds() != null && t.getCategoryIds().length > 0) {
            mustArray.add(createTerms("categoryId", Arrays.asList(t.getCategoryIds())));
        }
        if (t.getBrandIds() != null && t.getBrandIds().length > 0) {
            mustArray.add(createTerms("brandId", Arrays.asList(t.getBrandIds())));
        }
        if (t.getSupplierIds() != null && t.getSupplierIds().length > 0) {
            mustArray.add(createTerms("supplierId", Arrays.asList(t.getSupplierIds())));
        }
        if (StringUtils.isNotBlank(t.getLabels())) {
            List<String> labelList = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(t.getLabels());
            mustArray.add(createTerms("labelList.keyword", labelList));
        }
        // 特殊类型查询
        if (t.getSpecialType() != null) {
            mustArray.add(createTerm("specialType", t.getSpecialType().toString()));
        } else {
            if (!specialType) {
                mustArray.add(createTerm("specialType", "0"));
            }
        }
        // 特殊范围查询
        if (t.getSpecialScope() != null) {
            mustArray.add(createTerm("specialScope", t.getSpecialScope().toString()));
        }
        // 积分区间
        String gte = t.getMinPrice() == null ? null : t.getMinPrice().toString();
        String lte = t.getMaxPrice() == null ? null : t.getMaxPrice().toString();
        if (StringUtils.isNotEmpty(gte) || StringUtils.isNotEmpty(lte)) {
            mustArray.add(createRange("pointPrice", null, gte, null, lte));
        }
        // 月销区间
        String monthSalesVolumeGte = t.getMinMonthSalesVolume() == null ? null : t.getMinMonthSalesVolume().toString();
        String monthSalesVolumeLte = t.getMaxMonthSalesVolume() == null ? null : t.getMaxMonthSalesVolume().toString();
        if (StringUtils.isNotEmpty(monthSalesVolumeGte) || StringUtils.isNotEmpty(monthSalesVolumeLte)) {
            mustArray.add(createRange("monthSalesVolume", monthSalesVolumeGte, null, monthSalesVolumeLte, null));
        }
        JSONObject params = new JSONObject();
        params.put("query", createBool(mustArray, null, null, null));
        // 查询操作处理分页、排序、返回
        if (action == PARAMS_PAK_SEARCH) {
            // 分页
            Optional.ofNullable(page).ifPresent(p -> params.put("from", p.offset()));
            Optional.ofNullable(page).ifPresent(p -> params.put("size", p.getSize()));
            // 排序
            JSONArray sortArray = new JSONArray();
            addSort(sortArray, page.descs(), true, t);
            addSort(sortArray, page.ascs(), false, t);
            sortArray.add(createOrder("gtWeight", true));
            sortArray.add(createOrder("_score", true));
            params.put("sort", sortArray);
            // 指定返回字段
            JSONArray source = getSource();
            if (!source.isEmpty()) {
                params.put("_source", source);
            }
        }
        return params;
    }

    private JSONArray getSource() {
        JSONArray _source = new JSONArray();
        _source.add("id");
        _source.add("spuCode");
        _source.add("mainPic");
        _source.add("spuName");
        _source.add("spuSubName");
        _source.add("payment");
        _source.add("pointPrice");
        _source.add("beanPrice");
        _source.add("salePrice");
        _source.add("marketPrice");
        _source.add("totalSalesVolume");
        _source.add("monthSalesVolume");
        _source.add("recentSalesVolume");
        _source.add("daySalesVolume");
        _source.add("secKillList");
        _source.add("labelList");
        _source.add("platforms");
        return _source;
    }

    private void addSort(JSONArray sortArray, String[] keys, boolean desc, ProductSpuQueryDTO spuDTO) {
        if (keys == null) {
            return;
        }
        for (String key : keys) {
            if (SORT_KEYS.contains(key)) {
                switch (key) {
                    case "secKillList.sort":
                        if (StringUtils.isNotEmpty(spuDTO.getSecKill())) {
                            sortArray.add(createNestedOrder("secKillList", "sort", desc, "id", spuDTO.getSecKill()));
                        }
                        break;
                    case "themeList.sort":
                        if (StringUtils.isNotEmpty(spuDTO.getThemes())) {
                            sortArray.add(createNestedOrder("themeList", "sort", desc, "id", spuDTO.getThemes()));
                        }
                        break;
                    case "synthesize":
                        // 综合排序
                        if (StringUtils.isNotBlank(synthesizeOrderScript)) {
                            // 注入脚本参数 当前时间与加权供应商
                            HashMap<String, Object> params = new HashMap<>();
                            params.put("now", System.currentTimeMillis());
                            params.put("supplierId", synthesizeOrderSupplierId);
                            // TODO 可以聚合出多种参数注入到脚本运行环境中以实现更精确的算法
                            sortArray.add(createScriptOrder(synthesizeOrderScript, params, desc));
                        }
                        break;
                    default:
                        sortArray.add(createOrder(key, desc));
                }
            }
        }
    }

    @Override
    public void addSalesVolume(ProductSpuEsDTO updateDTO) {
        // 累计总销量与近期销量
        JSONObject script = new JSONObject();
        String source = "ctx._source.totalSalesVolume += params.totalSalesVolume;" +
                "ctx._source.recentSalesVolume += params.totalSalesVolume;";
        script.put("source", source);
        script.put("lang", SCRIPT_LANG);
        script.put("params", updateDTO);
        scriptUpdate(updateDTO, script);
    }

    @Override
    public void rebuildIndex() {
        rebuildIndex(null);
    }

    /**
     * @Description: 临时解决下面方法报错
     * @Author: zhoujie
     * @param spuIds:
     * @return: void
     **/
    @Override
    public void rebuildIndex(String spuIds) {

    }

//    @Override
//    public void rebuildIndex(String spuIds) {
//        ProductSpuQueryDTO query = new ProductSpuQueryDTO();
//        query.setDel(false);
//        if (StringUtils.isNotBlank(spuIds)) {
//            List<String> ids = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(spuIds);
//            if (!ids.isEmpty()) {
//                String[] idArray = new String[ids.size()];
//                ids.toArray(idArray);
//                query.setIds(idArray);
//            }
//        }
//        Page<ProductSpuDTO> page = new Page<>();
//        page.setPage(1);
//        page.setSize(1000L);
//        IPage<ProductSpuDTO> select = productSpuService.select(query, page);
//        long totalPages = page.getTotalPages();
//        StringBuilder body = new StringBuilder();
//        do {
//            log.info("开始重建第{}页", page.getPage());
//            body.delete(0, body.length());
//            for (ProductSpuDTO productSpu : select.getList()) {
//                productSpu = productSpuService.selectById(productSpu.getId());
//                ProductSpuEsDTO productSpuEsDTO = BeanUtils.createBeanByTarget(productSpu, ProductSpuEsDTO.class);
//                productSpuEsDTO.setRecentSalesVolume(0);
//                typeConvert(productSpuEsDTO);
//                ignoreField(productSpuEsDTO);
//                // 不存在构建存在则更新
//                String action = "index";
//                Boolean exist = exist(productSpuEsDTO.getId());
//                if (exist) {
//                    action = "update";
//                }
//                body.append("{\"").append(action).append("\":{\"_index\":\"").append(esIndex).append("\",\"_id\":\"")
//                        .append(productSpuEsDTO.getId())
//                        .append("\"}}\n");
//                if (exist) {
//                    body.append("{\"doc\":").append(JSON.toJSONString(productSpuEsDTO)).append("}").append("\n");
//                } else {
//                    body.append(JSON.toJSONString(productSpuEsDTO)).append("\n");
//                }
//            }
//            if (body.length() > 0) {
//                bulk(body.toString());
//            }
//            page.setPage(page.getPage() + 1);
//            select = productSpuService.select(query, page);
//        } while (totalPages >= page.getPage());
//    }

    @Override
    public void resetMonthSalesVolume() {
        // 月销量大于0的重置为0
        ProductSpuQueryDTO query = new ProductSpuQueryDTO();
        query.setMinMonthSalesVolume(0);
        String source = "ctx._source.monthSalesVolume = 0";
        JSONObject script = createScript(source, null, null);
        scriptUpdateByQuery(query, script);
    }

    @Override
    public void setRecentSalesVolume(Collection<ProductSalesVolumeDTO> salesVolumeDTOS, boolean reset) {
        // 近期销量置为零
        if (reset) {
            resetRecentSalesVolume();
        }
        StringBuilder body = new StringBuilder();
        for (ProductSalesVolumeDTO salesVolumeDTO : salesVolumeDTOS) {
            body.append("{\"update\":{\"_index\":\"").append(esIndex).append("\",\"_id\":\"")
                    .append(salesVolumeDTO.getSpuId())
                    .append("\"}}\n");
            salesVolumeDTO.setSpuId(null);
            body.append("{\"doc\":").append(JSON.toJSONString(salesVolumeDTO)).append("}").append("\n");
        }
        if (body.length() > 0) {
            bulk(body.toString());
        }
    }

    private void resetRecentSalesVolume() {
        try {
            JSONObject query = new JSONObject();
            query.put("query", createMatchAll());
            JSONObject script = createScript("ctx._source.recentSalesVolume = 0", SCRIPT_LANG, null);
            scriptUpdateByQuery(query, script);
        } catch (Exception e) {
            // 有可能个别商品会有版本冲突，重置出错
            log.warn("重置近期销量出错", e);
        }
    }

    private void ignoreField(ProductSpuEsDTO spuDTO) {
        // 移除ES中不需要的属性
        spuDTO.setPlatform(null);
        spuDTO.setLabels(null);
        spuDTO.setPlaceCode(null);
        spuDTO.setPlaceName(null);
        spuDTO.setSupplierSend(null);
        spuDTO.setChannelList(null);
        spuDTO.setProductUserId(null);
        spuDTO.setProductUserName(null);
        spuDTO.setAuditDesc(null);
        spuDTO.setCreateBy(null);
        spuDTO.setUpdateBy(null);
        spuDTO.setUpdateTime(null);
    }

    private void typeConvert(ProductSpuEsDTO spuDTO) {
        // 平台类型
        Splitter splitter = Splitter.on(",").omitEmptyStrings().trimResults();
        Optional.ofNullable(spuDTO.getPlatform()).ifPresent(s -> spuDTO.setPlatforms(splitter.splitToList(s)));
        // 标签类型
        Optional.ofNullable(spuDTO.getLabels()).ifPresent(s -> spuDTO.setLabelList(splitter.splitToList(s)));
        // 产地编码
        Optional.ofNullable(spuDTO.getPlaceCode()).ifPresent(s -> spuDTO.setPlaceCodes(splitter.splitToList(s)));
        // 产地名称
        Optional.ofNullable(spuDTO.getPlaceName()).ifPresent(s -> spuDTO.setPlaceNames(splitter.splitToList(s)));
    }
}
