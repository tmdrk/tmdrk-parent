package org.tmdrk.toturial.hadoop.rpc.server;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RPC.Builder;
import org.apache.hadoop.ipc.Server;

public class Starter {
    public static void main(String[] args) throws Exception{

        Builder builder = new RPC.Builder(new Configuration());

        builder.setBindAddress("localhost").setPort(10006).setProtocol(LoginService.class).setInstance(new LoginServiceImpl());

        Server server = builder.build();

        server.start();

    }
}
