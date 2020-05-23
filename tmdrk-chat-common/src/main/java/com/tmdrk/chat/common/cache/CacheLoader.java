package com.tmdrk.chat.common.cache;

import com.tmdrk.chat.common.entity.Message;
import com.tmdrk.chat.common.utils.IpUtils;
import com.tmdrk.chat.common.utils.JedisUtil;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    //用户与channel对应关系map
    public static Map<String, Channel> userChannelMap = new ConcurrentHashMap<>();
    //channel与用户对应关系map
    public static Map<Channel, String> channelUserMap = new ConcurrentHashMap<>();

    //本机IP地址
    public static final String LOCAL_IP_ADDR = IpUtils.getIpAddress();

    //聊天服务器名（前缀 + 服务器ip）
    public static final String TMDRK_CHAT_SERVER_NAME = "tmdrk-chat-server-"+LOCAL_IP_ADDR;

    //聊天服务器ip地址redis缓存键
    public static final String TMDRK_CHAT_SERVER_IPS = "tmdrk:chat:server:ips";

    //聊天交换器名称
    public static final String TMDRK_CHAT_EXCHANGE = "tmdrk-chat-exchange";

    //聊天队列前缀
    public static final String TMDRK_CHAT_QUEUE_PRFIX = "tmdrk-chat-queue-";

    //聊天队列名称（前缀 + 服务器ip）
    public static final String TMDRK_CHAT_QUEUE_NAME = TMDRK_CHAT_QUEUE_PRFIX+LOCAL_IP_ADDR;

    //netty服务端口
    public static Integer TMDRK_CHAT_SERVER_PORT = 8888;

    //聊天服务用户redis缓存前缀  key(用户标识)->value(服务器ip地址)
    public static final String TMDRK_CHAT_SERVER_USERID_PRFIX = "tmdrk:chat:server:userid:";

    //webSocket请求路径
    public static final String TMDRK_CHAT_HTTP_WS = "/ws";


//    public static final String CHAT_USER_UNREACH_PREFIX = "chat_user_unreach_prefix_";
//
//    public static final String CHAT_USER_ID_PREFIX = "chat_user_id_prefix_";
//
//    public static final String CHAT_CHANNEL_ID_PREFIX = "chat_channel_id_prefix_";
//
//    public static final String CHAT_NETTY_SERVER_INCR = "chat_netty_server_incr_";
//
//    public static final String CHAT_NETTY_SERVER_SEQ = "chat_netty_server_seq_";
//
//    private static Long chat_netty_server_seq;
//
//
//    public static Long getChat_netty_server_seq() {
//        return chat_netty_server_seq;
//    }
//
//    public static void setChat_netty_server_seq(Long chat_netty_server_seq) {
//        CacheLoader.chat_netty_server_seq = chat_netty_server_seq;
//    }

//    public static void put(Message message){
//        try {
//            Map<String, List> map;
//            List<Message> list;
//            if (JedisUtil.exists(CHAT_USER_UNREACH_PREFIX + message.getTo())) {
//                map = JedisUtil.getMap(CHAT_USER_UNREACH_PREFIX + message.getTo(), List.class);
//                if (map.containsKey(message.getFrom().toString())) {
//                    list = map.get(message.getFrom().toString());
//                    list.add(message);
//                } else {
//                    list = new ArrayList<>();
//                    list.add(message);
//                    map.put(message.getFrom().toString(), list);
//                }
//                JedisUtil.setMap(CHAT_USER_UNREACH_PREFIX + message.getTo(), map);
//            } else {
//                map = new HashMap<>();
//                list = new ArrayList<>();
//                list.add(message);
//                map.put(message.getFrom().toString(), list);
//                JedisUtil.setMap(CHAT_USER_UNREACH_PREFIX + message.getTo(), map);
//            }
//            map = JedisUtil.getMap(CHAT_USER_UNREACH_PREFIX + message.getTo(), List.class);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//    public static List<Message> get(Integer to,Integer from){
//        if(JedisUtil.exists(CHAT_USER_UNREACH_PREFIX+to)){
//            Map<String, List> map = JedisUtil.getMap(CHAT_USER_UNREACH_PREFIX + to,List.class);
//            if(map.containsKey(from.toString())){
//                return map.get(from.toString());
//            }
//        }
//        return new ArrayList<>();
//    }
//
//    public static void del(Integer key){
//        if(JedisUtil.exists(CHAT_USER_UNREACH_PREFIX + key)){
//            JedisUtil.del(CHAT_USER_UNREACH_PREFIX + key);
//        }
//    }

//    public static long initSeq(){
//        Long seq = JedisUtil.incr(CHAT_NETTY_SERVER_INCR);
//        chat_netty_server_seq = seq;
//        setChat_netty_server_seq(seq);
//        return seq;
//    }

}
