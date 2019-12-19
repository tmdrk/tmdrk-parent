package org.tmdrk.toturial.hadoop.rpc.server;

public class LoginServiceImpl implements LoginService {

    @Override
    public String login(String username, String password) {
        return username+" login is success";
    }

    @Override
    public String login2(String username, String password) {
        return username+" login2 is success";
    }
}
