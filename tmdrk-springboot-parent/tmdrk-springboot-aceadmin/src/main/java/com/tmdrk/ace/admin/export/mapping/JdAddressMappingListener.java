package com.tmdrk.ace.admin.export.mapping;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.tmdrk.ace.admin.entity.OmpAddress;
import com.tmdrk.ace.admin.entity.OmpAddressExample;
import com.tmdrk.ace.admin.export.omp.DemoDataListener;
import com.tmdrk.ace.admin.mapper.OmpAddressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OmpAddressDataListener
 *
 * @author Jie.Zhou
 * @date 2021/1/19 16:18
 */
public class JdAddressMappingListener extends AnalysisEventListener<JdAddressMapping> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoDataListener.class);
    private Map<String,String> param = new HashMap<>();
    private RedisTemplate redisTemplate;

    public JdAddressMappingListener(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void invoke(JdAddressMapping data, AnalysisContext analysisContext) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        if(!StringUtils.hasText(data.getName())||!StringUtils.hasText(data.getJdName())){
            return;
        }
        param.put(data.getName()+"_"+data.getLevel(),data.getJdName());
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        LOGGER.info("所有数据解析完成！");
        redisTemplate.delete("icbc:address:jd:mapping");
        redisTemplate.opsForHash().putAll("icbc:address:jd:mapping",param);
        param.clear();
        LOGGER.info("更新redis完成！");
    }
}
