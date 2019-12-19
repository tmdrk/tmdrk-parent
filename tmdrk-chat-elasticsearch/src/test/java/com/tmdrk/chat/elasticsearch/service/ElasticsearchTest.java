package com.tmdrk.chat.elasticsearch.service;

import com.tmdrk.chat.common.entity.es.TestProduct;
import com.tmdrk.chat.elasticsearch.ElasticsearchApplication;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

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
    private static Logger logger = Logger.getLogger(ChatServiceImplTest.class);
    @Autowired
    private ElasticSearchServiceImpl elasticSearchServiceImpl;

    @Test
    public void createChatIndex() {
        try {
            String indexName = "test_product";
            String type =  "doc";
            boolean createIndex = elasticSearchServiceImpl.createIndex(indexName,type, TestProduct.class);
            logger.info("createIndex:"+createIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
