package com.tmdrk.ace.admin.export.omp;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.tmdrk.ace.admin.entity.OmpAddress;
import com.tmdrk.ace.admin.entity.OmpAddressExample;
import com.tmdrk.ace.admin.mapper.OmpAddressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * OmpAddressDataListener
 *
 * @author Jie.Zhou
 * @date 2021/1/19 16:18
 */
public class OmpAddressDataListener extends AnalysisEventListener<OmpAddressData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoDataListener.class);
    List<OmpAddress> list = new ArrayList<>();
    private OmpAddressMapper ompAddressMapper;

    public OmpAddressDataListener(OmpAddressMapper ompAddressMapper) {
        this.ompAddressMapper = ompAddressMapper;
    }

    @Override
    public void invoke(OmpAddressData data, AnalysisContext analysisContext) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        OmpAddressExample example = new OmpAddressExample();
        OmpAddressExample.Criteria criteria = example.createCriteria();
        criteria.andLevelEqualTo(2);
        criteria.andNameLike("%"+data.getName().replace("县","")+"%");
        List<OmpAddress> ompAddresses = ompAddressMapper.selectByExample(example);
        if(ompAddresses.size()>1){
            LOGGER.info("too many id:{} name:{}",data.getId(),data.getName());
            return;
        }
        if(ompAddresses.size()==0){
            LOGGER.info("empty id:{} name:{}",data.getId(),data.getName());
            return;
        }
        OmpAddress ompAddress = ompAddresses.get(0);
        ompAddress.setShortName(data.getName());
        LOGGER.info("success: name:{} ompName:{}",data.getName(),ompAddress.getName());
        list.add(ompAddress);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        LOGGER.info("所有数据解析完成！");
        list.forEach(a->{
            LOGGER.info("success: name:{} ompName:{}",a.getShortName(),a.getName());
        });
    }
}
