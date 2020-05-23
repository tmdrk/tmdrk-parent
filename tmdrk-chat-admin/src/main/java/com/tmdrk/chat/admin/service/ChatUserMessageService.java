package com.tmdrk.chat.admin.service;

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

    public List<ChatUserMessage> getUnReadList(int toId){
        ChatUserMessageExample example = new ChatUserMessageExample();
        ChatUserMessageExample.Criteria criteria = example.createCriteria();
        criteria.andToIdEqualTo(toId);
        criteria.andStatusEqualTo((byte)0); //未读
        List<ChatUserMessage> chatUserMessages = chatUserMessageMapper.selectByExample(example);
        return chatUserMessages;
    }

    public int updateByIds(List<Integer> msgIds){
        ChatUserMessageExample example = new ChatUserMessageExample();
        ChatUserMessageExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(msgIds);
        ChatUserMessage chatUserMessage = new ChatUserMessage();
        chatUserMessage.setStatus((byte)1);
        int i = chatUserMessageMapper.updateByExampleSelective(chatUserMessage, example);
        return i;
    }

}
