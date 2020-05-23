package com.tmdrk.shiro.shiro;

import com.tmdrk.shiro.entity.*;
import com.tmdrk.shiro.service.AceUserService;
import com.tmdrk.shiro.service.AclPermissionService;
import com.tmdrk.shiro.service.AclRoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName UserRealm
 * @Description 自定义realm
 * @Author zhoujie
 * @Date 2020/5/14 17:57
 * @Version 1.0
 **/
public class UserRealm extends AuthorizingRealm {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    AceUserService aceUserService;

    @Autowired
    AclRoleService aclRoleService;

    @Autowired
    AclPermissionService aclPermissionService;

    /**
     * @Author zhoujie
     * @Description 执行授权逻辑
     *  由于每次都要授权，可以考虑使用缓存
     * @Date 17:57 2020/5/14
     * @Param [principalCollection]
     * @return org.apache.shiro.authz.AuthorizationInfo
     **/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("执行授权逻辑");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission("user:search");
        //拿到当前登录的对象
        Subject subject = SecurityUtils.getSubject();
        AceUser aceUser = (AceUser)subject.getPrincipal();

        if(aceUser==null){
            return null;
        }
        List<AclUserRole> aclUserRoles = aclRoleService.getAclUserRoles(aceUser.getId());
        if(CollectionUtils.isEmpty(aclUserRoles)){
            return null;
        }
        List<Integer> ids = aclUserRoles.stream().map(AclUserRole::getRoleId).collect(Collectors.toList());
        List<AclRole> aclRoles = aclRoleService.getAclRoles(ids);
        if(CollectionUtils.isEmpty(aclRoles)){
            return null;
        }

        List<Integer> roleIds = aclRoles.stream().map(AclRole::getId).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(roleIds)){
            return null;
        }
        //角色赋值
        List<String> roleNames = aclRoles.stream().map(AclRole::getName).collect(Collectors.toList());
        info.addRoles(roleNames);

        List<AclRolePermission> aclRolePermissions = aclPermissionService.getAclRolePermissions(roleIds);
        if(CollectionUtils.isEmpty(roleIds)){
            return info;
        }
        List<Integer> permissionIds = aclRolePermissions.stream().map(AclRolePermission::getPermissionId).collect(Collectors.toList());
        List<AclPermission> aclPermissions = aclPermissionService.getAclPermissions(permissionIds);
        //权限赋值
        List<String> permissionNames = aclPermissions.stream().map(AclPermission::getName).collect(Collectors.toList());
        info.addStringPermissions(permissionNames);
        return info;
    }

    /**
     * @Author zhoujie
     * @Description 执行认证逻辑
     * @Date 17:57 2020/5/14
     * @Param [authenticationToken]
     * @return org.apache.shiro.authc.AuthenticationInfo
     **/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("执行认证逻辑");
        //用户密码数据库中获取
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        AceUser aceUser = aceUserService.getAceUserByName(token.getUsername());
        if(!(aceUser!=null&&token.getUsername().equals(aceUser.getUserName()))){
            return null; //会抛出异常 UnknownAccountException
        }
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute("loginUser",aceUser);
        //密码认证，默认不加密 可以选择MD5 MD5盐值加密等
        return new SimpleAuthenticationInfo(aceUser,aceUser.getPassword(),"");
    }
}
