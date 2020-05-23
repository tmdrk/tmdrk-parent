package com.tmdrk.shiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.tmdrk.shiro.shiro.MyRolesAuthorizationFilter;
import com.tmdrk.shiro.shiro.UserRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName ShiroConfig
 * @Description
 * @Author zhoujie
 * @Date 2020/5/14 17:45
 * @Version 1.0
 **/
@Configuration
public class ShiroConfig {
    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 注入ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager")SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //添加安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //自定义filter拦截器
        Map<String, Filter> filtersMap = new LinkedHashMap<String, Filter>();
        filtersMap.put("roleOrFilter", new MyRolesAuthorizationFilter());//引入自定义AuthorizationFilter
        shiroFilterFactoryBean.setFilters(filtersMap);


        //添加shiro内置过滤器
        //shiro内置过滤器可以实现权限相关拦截
        //  常用的过滤器
        //      anon:无需认证（登录）可以访问
        //      authc:必须认证(登录)才可以访问
        //      user:如果使用rememberMe的功能可以直接访问
        //      perms:该资源必须得到资源权限才可以访问
        //      role:该资源必须得到角色权限才可以访问
        //  拦截顺序，先存入的优先生效  LinkedHashMap有序
        LinkedHashMap<String, String> filterMap = new LinkedHashMap<>();
        // URL 放行
        filterMap.put("/","anon");
        filterMap.put("/welcome","anon");
        filterMap.put("/login", "anon");
        filterMap.put("/logout", "logout");
        filterMap.put("**/css/**", "anon");
        filterMap.put("**/js/**", "anon");
        filterMap.put("**/img/**", "anon");
        filterMap.put("/favicon.ico", "anon");
        //授权,没有权限会跳转到未授权页面
        //参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，例如/admins/user/**=perms["user:add:*,user:modify:*"]，当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法。
        filterMap.put("/level1/1/**","perms[user:all]");//访问该路径，必须user:all权限才能访问
//        filterMap.put("/level1/1","perms[user:query]");//访问该路径，必须有user:query权限才能访问
        filterMap.put("/level1/1/add","perms[user:add]");//访问该路径，必须user:add]权限才能访问
        filterMap.put("/level1/1/update","perms[user:update]");//访问该路径，必须user:update]权限才能访问
        filterMap.put("/level1/2/add","perms[user2:add]");//访问该路径，必须user2:add权限才能访问
        filterMap.put("/level1/2/update","perms[user2:update]");//访问该路径，必须user2:update的权限才能访问
        //参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，当有多个参数时，例如admins/user/**=roles["admin,guest"],每个参数通过才算通过，相当于hasAllRoles()方法。
        filterMap.put("/level1/**","roleOrFilter[admin,guest]");//必须有admin或者guest角色才能访问该路径,使用自定义filter实现
        filterMap.put("/level*/**","authc"); //访问该路径必须要登录才能访问
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        //设置登录页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //设置未授权页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/noauth");

        return shiroFilterFactoryBean;
    }

    /**
     * 注入安全管理器
     */
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        // 将自定义 Realm 加进来
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联userRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     * 创建realm
     */
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

    /**
     * 整合thymeleaf和shiro
     */
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

}
