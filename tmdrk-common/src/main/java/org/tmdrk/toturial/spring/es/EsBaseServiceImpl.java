package org.tmdrk.toturial.spring.es;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The type Es base service.
 *
 * @param <T> the type parameter
 * @author William
 */
@Slf4j
public abstract class EsBaseServiceImpl<T extends Entity<Long>, Q> implements IEsBaseService<T, Q> {

    protected final int PARAMS_PAK_SEARCH = 1;
    protected final int PARAMS_PAK_UPDATE = 2;

    /**
     * The Rest client.
     */
    @Autowired
    protected RestClient restClient;
    @Autowired
    protected RestHighLevelClient restHighLevelClient;

    /**
     * The Clazz.
     */
    protected Class<T> clazz;

    /**
     * Instantiates a new Es base service.
     */
    public EsBaseServiceImpl() {
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        clazz = (Class<T>) actualTypeArguments[0];
    }

    /**
     * Save long.
     * 增
     *
     * @param t the t
     * @return the long
     */
    @Override
    public Long save(T t) {
        StringBuilder endPoint = getEndPoint(t.getId().toString());
        Request request = new Request(HttpMethod.POST.name(), endPoint.toString());
        request.setJsonEntity(JSON.toJSONString(t));
        request(request);
        return t.getId();
    }

    /**
     * Del long.
     * 删
     *
     * @param id the id
     * @return the long
     */
    @Override
    public Long del(@NonNull Long id) {
        StringBuilder endPoint = getEndPoint(id.toString());
        Request request = new Request(HttpMethod.DELETE.name(), endPoint.toString());
        request(request);
        return id;
    }

    @Override
    public Boolean exist(Long id) {
        StringBuilder endPoint = getEndPoint(id.toString());
        Request request = new Request(HttpMethod.HEAD.name(), endPoint.toString());
        Response response = request(request);
        // 获取状态码
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 404) {
            return false;
        }
        return true;
    }

    /**
     * Update long.
     * 改
     *
     * @param t the t
     * @return the long
     */
    @Override
    public Long update(T t) {
        StringBuilder endPoint = getEndPoint(t.getId().toString());
        Request request = new Request(HttpMethod.POST.name(), endPoint.toString().replace("/_doc", "/_update"));
        JSONObject doc = new JSONObject();
        doc.put("doc", t);
        request.setJsonEntity(doc.toJSONString());
        request(request);
        return t.getId();
    }

    @Override
    public Long scriptUpdate(T t, JSONObject script) {
        StringBuilder endPoint = getEndPoint(t.getId().toString());
        String point = endPoint.toString().replace("_doc", "_update");
        Request request = new Request(HttpMethod.POST.name(), point);
        JSONObject doc = new JSONObject();
        doc.put("script", script);
        request.setJsonEntity(doc.toJSONString());
        request(request);
        return t.getId();
    }

    @Override
    public Long scriptUpdateByQuery(Q q, JSONObject script) {
        // 结构化查询条件封装
        JSONObject query = queryParamsPack(PARAMS_PAK_UPDATE, q, null);
        return scriptUpdateByQuery(query, script);
    }

    protected Long scriptUpdateByQuery(JSONObject query, JSONObject script) {
        StringBuilder endPoint = getEndPoint("");
        Request request = new Request(HttpMethod.POST.name(), endPoint.toString().replace("/_doc", "/_update_by_query"));
        query.put("script", script);
        request.setJsonEntity(query.toJSONString());
        Response response = request(request);
        String responseBody = null;
        try {
            responseBody = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            String msg = "Es请求响应解析出错";
            log.error(msg, e);
            throw new BusinessException(5000, msg);
        }
        JSONObject json = JSON.parseObject(responseBody);
        return json.getLong("updated");
    }

    @Override
    public void bulk(String jsonBody) {
        String point = getEndPoint("").toString().replace("_doc", "_bulk");
        Request request = new Request(HttpMethod.POST.name(), point);
        if (jsonBody != null) {
            request.setJsonEntity(jsonBody);
        }
        request(request);
    }

    /**
     * Details t.
     * 详情
     *
     * @param id the id
     * @return the t
     */
    @Override
    public T details(@NonNull Long id) {
        StringBuilder endPoint = getEndPoint(id.toString());
        Request request = new Request(HttpMethod.GET.name(), endPoint.toString());
        Response response = request(request);
        // 获取状态码
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 404) {
            throw new BusinessException(statusCode, "资源不存在！");
        }
        String responseBody;
        try {
            responseBody = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            String msg = "Es请求响应解析出错";
            log.error(msg, e);
            throw new BusinessException(5000, msg);
        }
        JSONObject json = JSON.parseObject(responseBody);
        JSONObject source = json.getJSONObject("_source");
        if (source == null) {
            return null;
        }
        return source.toJavaObject(clazz);
    }

    /**
     * Search list.
     * 查询
     *
     * @param q    the q
     * @param page the page
     * @return the list
     */
    @Override
    public IPage<T> search(Q q, IPage<T> page) {
        Assert.notNull(page, "分页对象不能为空");
        StringBuilder endPoint = getEndPoint("_search");
        int start = endPoint.indexOf("/_doc");
        endPoint.delete(start, start + 5);
        // 分页条件
        endPoint.append("?").append("size=").append(page.getSize())
                .append("&").append("from=").append(page.getSize() * (page.getPage() - 1));
        Request request = new Request(HttpMethod.GET.name(), endPoint.toString());
        // 结构化查询条件封装
        JSONObject query = queryParamsPack(PARAMS_PAK_SEARCH, q, page);
        if (query != null && !query.isEmpty()) {
            request.setJsonEntity(query.toJSONString());
        }
        Response response = request(request);
        String responseBody = null;
        try {
            responseBody = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            String msg = "Es请求响应解析出错";
            log.error(msg, e);
            throw new BusinessException(5000, msg);
        }
        JSONObject json = JSON.parseObject(responseBody);
        JSONObject hits = json.getJSONObject("hits");
        Integer total = hits.getJSONObject("total").getInteger("value");
        page.setTotal(total);
        JSONArray array = hits.getJSONArray("hits");
        List<T> list = new ArrayList<>(array.size());
        for (int i = 0; i < array.size(); i++) {
            JSONObject source = array.getJSONObject(i).getJSONObject("_source");
            list.add(source.toJavaObject(clazz));
        }
        page.setList(list);
        return page;
    }

    /**
     * Gets end point.
     * 获取操作符
     *
     * @param point the point
     * @return the end point
     */
    protected abstract StringBuilder getEndPoint(String point);

    /**
     * Query params pack string.
     * 结构化查询条件封装 子类实现
     *
     * @param action the action
     * @param q      the q
     * @param page   the page
     * @return the JSONObject
     */
    protected abstract JSONObject queryParamsPack(int action, Q q, IPage<T> page);

    /**
     * Request response.
     *
     * @param request the request
     * @return the response
     */
    protected Response request(Request request) {
        String requestId = IdUtil.simpleUUID();
        String endpoint = request.getEndpoint();
        String entityStr = "";
        HttpEntity entity = request.getEntity();
        if (entity != null) {
            try {
                entityStr = EntityUtils.toString(entity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info("Es request[{}]: {}\n{}", requestId, endpoint, entityStr);
        Response response = null;
        try {
            response = restClient.performRequest(request);
        } catch (ResponseException e) {
            response = e.getResponse();
        } catch (Exception e) {
            log.error("ES请求异常", e);
        }
        if (response == null) {
            throw new BusinessException(5000, "Es请求出错");
        }
        // 获取状态码
        int statusCode = response.getStatusLine().getStatusCode();
        switch (statusCode) {
            case 200:
            case 201:
            case 404:
                break;
            default:
                String responseBody = null;
                try {
                    if (response.getEntity() != null) {
                        responseBody = EntityUtils.toString(response.getEntity());
                    }
                } catch (IOException e) {
                    log.error("Es请求响应解析出错", e);
                }
                log.info("Es response[{}:{}]: {}", requestId, statusCode, responseBody);
                throw new BusinessException(statusCode, "Es请求失败");
        }
        return response;
    }

    protected JSONObject createScript(String source, String lang, Object params) {
        JSONObject script = new JSONObject();
        script.put("source", source);
        if (StringUtils.isNotBlank(lang)) {
            script.put("lang", lang);
        }
        Optional.ofNullable(params).ifPresent(o -> script.put("params", o));
        return script;
    }

    protected JSONObject createMatchAll() {
        String json = "{\"match_all\":{}}";
        return JSON.parseObject(json);
    }

    protected JSONObject createMatch(String key, String value) {
        String json = "{\"match\":{\"" + key + "\":\"" + value + "\"}}";
        return JSON.parseObject(json);
    }

    protected JSONObject createMatch(String key, String value, String operator) {
        String json = "{\"match\":{\"" + key + "\":{\"query\":\"" + value + "\",\"operator\":\"" + operator + "\"}}}";
        return JSON.parseObject(json);
    }

    protected JSONObject createTerm(String key, String value) {
        String json = "{\"term\":{\"" + key + "\":\"" + value + "\"}}";
        return JSON.parseObject(json);
    }

    protected JSONObject createTerms(String key, List<String> values) {
        String json = "{\"terms\":{\"" + key + "\":" + JSON.toJSONString(values) + "}}";
        return JSON.parseObject(json);
    }

    protected JSONObject createRange(String key, String gtValue, String gteValue, String ltValue, String lteValue) {
        JSONObject condition = new JSONObject();
        if (StringUtils.isNotEmpty(gtValue)) {
            condition.put("gt", gtValue);
        }
        if (StringUtils.isNotEmpty(gteValue)) {
            condition.put("gte", gteValue);
        }
        if (StringUtils.isNotEmpty(ltValue)) {
            condition.put("lt", ltValue);
        }
        if (StringUtils.isNotEmpty(lteValue)) {
            condition.put("lte", lteValue);
        }
        if (condition.isEmpty()) {
            return null;
        }
        JSONObject range = new JSONObject();
        range.put(key, condition);
        JSONObject json = new JSONObject();
        json.put("range", range);
        return json;
    }

    protected JSONObject createBool(List<JSONObject> mustArray, List<JSONObject> notMustArray, List<JSONObject> shouldArray, JSONObject filter) {
        JSONObject params = new JSONObject(4);
        if (mustArray != null && !mustArray.isEmpty()) {
            params.put("must", mustArray);
        }
        if (notMustArray != null && !notMustArray.isEmpty()) {
            params.put("must_not", notMustArray);
        }
        if (shouldArray != null && !shouldArray.isEmpty()) {
            params.put("should", shouldArray);
        }
        if (filter != null && !filter.isEmpty()) {
            params.put("filter", filter);
        }
        JSONObject boolQuery = new JSONObject(1);
        boolQuery.put("bool", params);
        return boolQuery;
    }

    protected JSONObject createNestedMatch(String path, String key, String value) {
        String json = "{\"nested\":{\"path\":\"" + path + "\",\"query\":{\"match\":{\"" + path + "." + key + "\":\"" + value + "\"}}}}";
        return JSON.parseObject(json);
    }

    protected JSONObject createNestedTerm(String path, String key, String value) {
        String json = "{\"nested\":{\"path\":\"" + path + "\",\"query\":{\"term\":{\"" + path + "." + key + "\":\"" + value + "\"}}}}";
        return JSON.parseObject(json);
    }

    protected JSONObject createNestedBool(String path, List<JSONObject> mustArray, List<JSONObject> notMustArray, List<JSONObject> shouldArray, JSONObject filter) {
        JSONObject params = new JSONObject(1);
        JSONObject nested = new JSONObject(2);
        params.put("nested", nested);
        nested.put("path", path);
        nested.put("query", createBool(mustArray, notMustArray, shouldArray, filter));
        return params;
    }

    protected JSONObject createOrder(String key, boolean desc) {
        String json = "{\"" + key + "\":{\"order\":\"" + (desc ? "desc" : "asc") + "\"}}";
        return JSON.parseObject(json);
    }

    /**
     * 嵌套排序
     *
     * @param key
     * @param desc
     * @return
     */
    protected JSONObject createNestedOrder(String path, String key, boolean desc, String filterKey, String filterValue) {
        String json = "{\"" + path + "." + key + "\":{\"order\":\"" + (desc ? "desc" : "asc") + "\"}}";
        JSONObject param = JSON.parseObject(json);
        JSONObject order = param.getJSONObject(path + "." + key);
        JSONObject nested = new JSONObject();
        nested.put("path", path);
        JSONObject filter = createTerm(path + "." + filterKey, filterValue);
        nested.put("filter", filter);
        order.put("nested", nested);
        return param;
    }

    /**
     * 脚本排序
     *
     * @param desc
     * @return
     */
    protected JSONObject createScriptOrder(String script, Map<String, Object> params, boolean desc) {
        String json = "{\"_script\":{\"script\":{\"source\":\""
                + script + "\",\"params\":" + JSON.toJSONString(params) + "},\"type\":\"number\",\"order\":\""
                + (desc ? "desc" : "asc")
                + "\"}}";
        return JSON.parseObject(json);
    }
}