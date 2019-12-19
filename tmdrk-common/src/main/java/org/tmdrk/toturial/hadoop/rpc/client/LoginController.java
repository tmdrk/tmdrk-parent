package org.tmdrk.toturial.hadoop.rpc.client;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import java.io.IOException;
import java.net.InetSocketAddress;

public class LoginController {
    public static void main(String[] args) throws IOException {
        org.tmdrk.toturial.hadoop.rpc.server.LoginService proxy = RPC.getProxy(org.tmdrk.toturial.hadoop.rpc.server.LoginService.class, 1L, new InetSocketAddress("localhost", 10006), new Configuration());
        String result = proxy.login2("tom", "123");
        System.out.println(result);
    }

}
