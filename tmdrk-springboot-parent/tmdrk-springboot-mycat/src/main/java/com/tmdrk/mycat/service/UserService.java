package com.tmdrk.mycat.service;

import com.tmdrk.mycat.annotationMapper.AnnotationUserMapper;
import com.tmdrk.mycat.entity.User;
import com.tmdrk.mycat.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/18 18:51
 * @Version 1.0
 **/
@Service
public class UserService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserMapper userMapper;

    @Autowired
    AnnotationUserMapper annotationUserMapper;

    public User getUser(Integer id){
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    public User getUserForceMaster(Integer id){
        User user = annotationUserMapper.selectByIdForceMaster(id);
        return user;
    }

    public int userAdd(User user){
        int result = annotationUserMapper.insert(user);
        return result;
    }
}
