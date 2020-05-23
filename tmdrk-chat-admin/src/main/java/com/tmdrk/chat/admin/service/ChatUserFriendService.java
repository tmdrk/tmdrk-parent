package com.tmdrk.chat.admin.service;

import com.tmdrk.chat.dao.entity.ChatUserFriend;
import com.tmdrk.chat.dao.entity.ChatUserFriendExample;
import com.tmdrk.chat.dao.mapper.ChatUserFriendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ChatUserFriend
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/13 0:58
 * @Version 1.0
 **/
@Service
public class ChatUserFriendService {
    @Autowired
    ChatUserFriendMapper chatUserFriendMapper;

    public List<ChatUserFriend> selectByUserId(int userId){
        ChatUserFriendExample example = new ChatUserFriendExample();
        ChatUserFriendExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<ChatUserFriend> chatUserFriends = chatUserFriendMapper.selectByExample(example);
        return chatUserFriends;
    }
}
