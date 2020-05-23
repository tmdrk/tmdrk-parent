package com.tmdrk.shiro.service;

import com.tmdrk.shiro.entity.AclRole;
import com.tmdrk.shiro.entity.AclRoleExample;
import com.tmdrk.shiro.entity.AclUserRole;
import com.tmdrk.shiro.entity.AclUserRoleExample;
import com.tmdrk.shiro.mapper.AclRoleMapper;
import com.tmdrk.shiro.mapper.AclUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName AclRoleService
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/14 22:53
 * @Version 1.0
 **/
@Service
public class AclRoleService {
    @Autowired
    AclRoleMapper aclRoleMapper;

    @Autowired
    AclUserRoleMapper aclUserRoleMapper;

    public List<AclUserRole> getAclUserRoles(int userId){
        AclUserRoleExample example = new AclUserRoleExample();
        AclUserRoleExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        return aclUserRoleMapper.selectByExample(example);
    }

    public List<AclRole> getAclRoles(List<Integer> ids){
        AclRoleExample example = new AclRoleExample();
        AclRoleExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        return aclRoleMapper.selectByExample(example);
    }
}
