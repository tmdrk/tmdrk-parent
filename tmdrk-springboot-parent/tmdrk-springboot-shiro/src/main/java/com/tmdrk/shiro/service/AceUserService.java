package com.tmdrk.shiro.service;

import com.tmdrk.shiro.entity.AceUser;
import com.tmdrk.shiro.entity.AceUserExample;
import com.tmdrk.shiro.mapper.AceUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @ClassName AceUserService
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/14 21:53
 * @Version 1.0
 **/
@Service
public class AceUserService {
    @Autowired
    AceUserMapper aceUserMapper;

    public AceUser getAceUserByName(String userName){
        AceUserExample example = new AceUserExample();
        AceUserExample.Criteria criteria = example.createCriteria();
        criteria.andUserNameEqualTo(userName);
        List<AceUser> aceUsers = aceUserMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(aceUsers)){
            return aceUsers.get(0);
        }
        return null;
    }
}
