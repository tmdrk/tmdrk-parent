package com.tmdrk.chat.elasticsearch.service;

import com.tmdrk.chat.elasticsearch.ElasticsearchApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Map;

/**
 * @ClassName ElasticsearchTest
 * @Description
 *
 * springboot单元测试不支持yml文件
 *
 * @Author zhoujie
 * @Date 2019/12/19 14:08
 * @Version 1.0
 **/
//@RunWith(SpringRunner.class)
//@SpringBootTest
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElasticsearchApplication.class)
public class ElasticsearchTest {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ElasticSearchServiceImpl elasticSearchServiceImpl;

    @Test
    public void test() {
        logger.info("test...");
    }

    @Test
    public void searchById() {
        try {
            String indexName = "test_product_2";
            String type =  "doc";
            Map map = elasticSearchServiceImpl.searchById(indexName, type, "10389036289");
            logger.info("map:"+map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
