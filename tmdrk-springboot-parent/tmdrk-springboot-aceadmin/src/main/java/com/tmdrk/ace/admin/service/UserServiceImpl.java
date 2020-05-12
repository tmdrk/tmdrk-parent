package com.tmdrk.ace.admin.service;

import com.tmdrk.ace.admin.entity.AceUser;
import com.tmdrk.ace.admin.mapper.AceUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/5 4:49
 * @Version 1.0
 **/
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    AceUserMapper aceUserMapper;

//    @Cacheable
    @Override
    public AceUser getUser(int id) {
        AceUser aceUser = aceUserMapper.selectByPrimaryKey(id);
        return aceUser;
    }
}
