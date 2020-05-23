package com.tmdrk.shiro.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.List;

/**
 * @ClassName MyRolesAuthorizationFilter
 * @Description 自定义roles过滤器,将多个角色默认并列关系，改为或者关系，即多个角色满足一个条件就可以访问
 * @Author zhoujie
 * @Date 2020/5/15 16:05
 * @Version 1.0
 **/
public class MyRolesAuthorizationFilter extends AuthorizationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);

        //配置的访问所需角色
        String[] rolesArray = (String[])  o;
        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }
        //需要修改的地方在这里，判断访问所需的角色是否包含当前用户的角色
        List<String> roles = CollectionUtils.asList(rolesArray);
        boolean[] bo = subject.hasRoles(roles);
        //如果有一个角色满足，则可以访问
        for(boolean b : bo){
            if(b == true){
                return true;
            }
        }
        return false;
    }
}
