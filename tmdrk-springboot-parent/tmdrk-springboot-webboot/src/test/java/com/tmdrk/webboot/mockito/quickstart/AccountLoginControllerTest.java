package com.tmdrk.webboot.mockito.quickstart;

import com.tmdrk.webboot.mockito.common.Account;
import com.tmdrk.webboot.mockito.common.AccountDao;
import com.tmdrk.webboot.mockito.common.AccountLoginController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @ClassName AccountLoginControllerTest
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/29 17:05
 * @Version 1.0
 **/
@RunWith(MockitoJUnitRunner.class)
public class AccountLoginControllerTest {
    private AccountDao accountDao;
    private HttpServletRequest request;
    private AccountLoginController accountLoginController;

    @Before
    public void setUp(){
        this.accountDao = Mockito.mock(AccountDao.class);
        this.request = Mockito.mock(HttpServletRequest.class);
        this.accountLoginController = new AccountLoginController(accountDao);
    }

    @Test
    public void testLoginSuccess(){
        Account account = new Account();
        when(request.getParameter("username")).thenReturn("alex");
        when(request.getParameter("password")).thenReturn("123456");
        when(accountDao.findAccount(anyString(),anyString())).thenReturn(account);
        String result = accountLoginController.login(request);
        assertThat(result,equalTo("/success"));
    }

    @Test
    public void testLoginFail(){
        Account account = new Account();
        when(request.getParameter("username")).thenReturn("alex");
        when(request.getParameter("password")).thenReturn("123456");
        when(accountDao.findAccount(anyString(),anyString())).thenReturn(null);
        assertThat(accountLoginController.login(request),equalTo("/login"));
    }

    @Test
    public void testLogin505(){
        Account account = new Account();
        when(request.getParameter("username")).thenReturn("alex");
        when(request.getParameter("password")).thenReturn("123456");
        when(accountDao.findAccount(anyString(),anyString())).thenThrow(UnsupportedOperationException.class);
        assertThat(accountLoginController.login(request),equalTo("/505"));
    }
}
