package org.tmdrk.toturial.hadoop.rpc.server;

import java.util.Date;

public class LoginServiceImpl implements LoginService {

    @Override
    public String login(String username, String password) {
        System.out.println("login..."+new Date());
        return username+" login is success";
    }

    @Override
    public String login2(String username, String password) {
        System.out.println("login2..."+new Date());
        return username+" login2 is success";
    }
}
