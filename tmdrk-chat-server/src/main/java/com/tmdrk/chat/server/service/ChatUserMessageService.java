package com.tmdrk.chat.server.service;

import com.tmdrk.chat.dao.entity.ChatUserMessage;
import com.tmdrk.chat.dao.entity.ChatUserMessageExample;
import com.tmdrk.chat.dao.mapper.ChatUserMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ChatUserMessage
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/13 0:58
 * @Version 1.0
 **/
@Service
public class ChatUserMessageService {
    @Autowired
    ChatUserMessageMapper chatUserMessageMapper;

    public int insertSelective(ChatUserMessage record){
        int i = chatUserMessageMapper.insertSelective(record);
        return i;
    }

}
