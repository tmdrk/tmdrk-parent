package com.tmdrk.chat.elasticsearch.service;

import com.tmdrk.chat.elasticsearch.ElasticsearchApplication;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.io.IOException;

/**
 * @ClassName ChatServiceImplTest
 * @Description
 *
 * springboot单元测试不支持yml文件
 *
 * @Author zhoujie
 * @Date 2019/7/18 15:29
 * @Version 1.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ElasticsearchApplication.class)
public class ChatServiceImplTest {
    private static Logger logger = Logger.getLogger(ChatServiceImplTest.class);
    @Autowired
    private IChatService chatService;

    @Test
    public void createChatIndex() {
        try {
            System.out.println(chatService.test());
//            boolean chatIndex = chatService.createChatIndex();
//            logger.info("createChatIndex:"+chatIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void deleteChatIndex() {
//        try {
//            boolean b = chatService.deleteChatIndex("");
//            logger.info("deleteChatIndex:"+b);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}