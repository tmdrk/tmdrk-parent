package com.tmdrk.webboot.mockito.common;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName AccountLoginController
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/29 16:55
 * @Version 1.0
 **/
public class AccountLoginController {
    private final AccountDao accountDao;
    public AccountLoginController(AccountDao accountDao){
        this.accountDao = accountDao;
    }

    public String login(HttpServletRequest request){
        final String username = request.getParameter("username");
        final String password = request.getParameter("password");
        try{
            Account account = accountDao.findAccount(username, password);
            if(account==null){
                return "/login";
            }else{
                return "/success";
            }
        }catch (Exception e){
            return "/505";
        }
    }
}
