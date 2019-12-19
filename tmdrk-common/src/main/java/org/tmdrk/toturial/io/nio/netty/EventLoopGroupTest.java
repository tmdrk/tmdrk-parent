package org.tmdrk.toturial.io.nio.netty;

import io.netty.util.NettyRuntime;
import io.netty.util.internal.SystemPropertyUtil;

/**
 * @ClassName EventLoopGroupTest
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/16 4:35
 * @Version 1.0
 **/
public class EventLoopGroupTest {
    public static void main(String[] args) {
        int count = Math.max(1, SystemPropertyUtil.getInt(
                "io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
        System.out.println(count);
    }
}
