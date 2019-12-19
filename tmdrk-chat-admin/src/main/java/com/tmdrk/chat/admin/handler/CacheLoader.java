package com.tmdrk.chat.admin.handler;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName CacheLoader
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/7/9 0:01
 * @Version 1.0
 **/
public class CacheLoader {
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static Map<String, Channel> channelMap = new ConcurrentHashMap<>();


}
