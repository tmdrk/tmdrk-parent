//package com.tmdrk.chat.server;
//
//import com.tmdrk.chat.dao.entity.ChatUserMessage;
//import com.tmdrk.chat.dao.mapper.ChatUserMessageMapper;
//import com.tmdrk.chat.server.service.ChatUserMessageService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
///**
// * @ClassName DBTest
// * @Description TODO
// * @Author zhoujie
// * @Date 2020/5/13 4:52
// * @Version 1.0
// **/
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class DBTest {
//
//    @Autowired
//    ChatUserMessageService chatUserMessageService;
//
//    @Autowired
//    ChatUserMessageMapper chatUserMessageMapper;
//
//    @Test
//    public void simpleTest() {
//        ChatUserMessage record = new ChatUserMessage();
//        record.setFromId(101);
//        record.setToId(103);
//        record.setMessage("gjsgf");
//        record.setMessageType((byte)1);
//        record.setStatus((byte)0);
//        Long time = System.currentTimeMillis() / 1000;
//        record.setCreateTime(time.intValue());
//        chatUserMessageMapper.insertSelective(record);
//    }
//}
