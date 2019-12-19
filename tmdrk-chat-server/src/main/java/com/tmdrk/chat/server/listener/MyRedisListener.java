package com.tmdrk.chat.server.listener;

import com.tmdrk.chat.common.utils.JedisPoolUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName MyRedisListener
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/14 2:05
 * @Version 1.0
 **/
public class MyRedisListener extends JedisPubSub {
    // 取得订阅的消息后的处理
    public void onMessage(String channel, String message) {
        System.out.println(channel + "=" + message);
    }

    // 初始化订阅时候的处理
    public void onSubscribe(String channel, int subscribedChannels) {
        System.out.println(channel + "=" + subscribedChannels);
    }

    // 取消订阅时候的处理
    public void onUnsubscribe(String channel, int subscribedChannels) {
        System.out.println(channel + "=" + subscribedChannels);
    }

    // 初始化按表达式的方式订阅时候的处理
    public void onPSubscribe(String pattern, int subscribedChannels) {
        System.out.println(pattern + "=" + subscribedChannels);
    }

    // 取消按表达式的方式订阅时候的处理
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        System.out.println(pattern + "=" + subscribedChannels);
    }

    // 取得按表达式的方式订阅的消息后的处理
    public void onPMessage(String pattern, String channel, String message) {
        System.out.println(pattern + "=" + channel + "=" + message);
    }

    public static void main(String[] args) {
        Jedis jedis = JedisPoolUtil.getJedis();
        MyRedisListener listener = new MyRedisListener();

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(()->{
            try {
                System.out.println("取消订阅开始");
                Thread.sleep(30000);//30秒后取消订阅
                System.out.println("取消订阅成功");
                listener.unsubscribe("my_channel","you_channel");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("订阅开始");
        jedis.subscribe(listener,"my_channel","you_channel");
        System.out.println("订阅结束");
        System.exit(1);
    }
}
