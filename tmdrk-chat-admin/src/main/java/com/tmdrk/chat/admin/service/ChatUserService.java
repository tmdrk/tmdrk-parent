package com.tmdrk.chat.admin.service;

import com.tmdrk.chat.dao.entity.ChatUser;
import com.tmdrk.chat.dao.entity.ChatUserExample;
import com.tmdrk.chat.dao.mapper.ChatUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName ChatUserService
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/13 0:58
 * @Version 1.0
 **/
@Service
public class ChatUserService {
    @Autowired
    ChatUserMapper chatUserMapper;

    public ChatUser selectByPrimaryKey(int id){
        ChatUser chatUser = chatUserMapper.selectByPrimaryKey(id);
        return chatUser;
    }

    public ChatUser selectByUserName(String userName){
        ChatUserExample example = new ChatUserExample();
        ChatUserExample.Criteria criteria = example.createCriteria();
        criteria.andUserNameEqualTo(userName);
        List<ChatUser> chatUsers = chatUserMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(chatUsers)){
            return chatUsers.get(0);
        }
        return null;
    }

    public List<ChatUser> selectByIds(List<Integer> ids){
        ChatUserExample example = new ChatUserExample();
        ChatUserExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        List<ChatUser> chatUsers = chatUserMapper.selectByExample(example);
        return chatUsers;
    }
}
