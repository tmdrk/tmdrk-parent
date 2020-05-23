package com.tmdrk.shiro.service;

import com.tmdrk.shiro.entity.*;
import com.tmdrk.shiro.mapper.AclPermissionMapper;
import com.tmdrk.shiro.mapper.AclRoleMapper;
import com.tmdrk.shiro.mapper.AclRolePermissionMapper;
import com.tmdrk.shiro.mapper.AclUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName AclPermissionService
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/15 13:34
 * @Version 1.0
 **/
@Service
public class AclPermissionService {
    @Autowired
    AclPermissionMapper aclPermissionMapper;

    @Autowired
    AclRolePermissionMapper aclRolePermissionMapper;

    public List<AclRolePermission> getAclRolePermissions(List<Integer> roleIds){
        AclRolePermissionExample example = new AclRolePermissionExample();
        AclRolePermissionExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdIn(roleIds);
        return aclRolePermissionMapper.selectByExample(example);
    }

    public List<AclPermission> getAclPermissions(List<Integer> ids){
        AclPermissionExample example = new AclPermissionExample();
        AclPermissionExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        return aclPermissionMapper.selectByExample(example);
    }
}
