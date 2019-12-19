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

    public static Map<String, Channel> channelMap = new ConcurrentHashMap<>();

    public static final String CHAT_USER_UNREACH_PREFIX = "chat_user_unreach_prefix_";

    public static final String CHAT_USER_ID_PREFIX = "chat_user_id_prefix_";

    public static final String CHAT_CHANNEL_ID_PREFIX = "chat_channel_id_prefix_";

    public static final String CHAT_NETTY_SERVER_INCR = "chat_netty_server_incr_";

    public static final String CHAT_NETTY_SERVER_SEQ = "chat_netty_server_seq_";

    private static Long chat_netty_server_seq;

    public static String nettyIpAddr = IpUtils.getLocalIpAddr();

    public static Integer nettyPort = 8888;

    public static Long getChat_netty_server_seq() {
        return chat_netty_server_seq;
    }

    public static void setChat_netty_server_seq(Long chat_netty_server_seq) {
        CacheLoader.chat_netty_server_seq = chat_netty_server_seq;
    }

    public static void put(Message message){
        try {
            Map<String, List> map;
            List<Message> list;
            if (JedisUtil.exists(CHAT_USER_UNREACH_PREFIX + message.getTo())) {
                map = JedisUtil.getMap(CHAT_USER_UNREACH_PREFIX + message.getTo(), List.class);
                if (map.containsKey(message.getFrom().toString())) {
                    list = map.get(message.getFrom().toString());
                    list.add(message);
                } else {
                    list = new ArrayList<>();
                    list.add(message);
                    map.put(message.getFrom().toString(), list);
                }
                JedisUtil.setMap(CHAT_USER_UNREACH_PREFIX + message.getTo(), map);
            } else {
                map = new HashMap<>();
                list = new ArrayList<>();
                list.add(message);
                map.put(message.getFrom().toString(), list);
                JedisUtil.setMap(CHAT_USER_UNREACH_PREFIX + message.getTo(), map);
            }
            map = JedisUtil.getMap(CHAT_USER_UNREACH_PREFIX + message.getTo(), List.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static List<Message> get(Integer to,Integer from){
        if(JedisUtil.exists(CHAT_USER_UNREACH_PREFIX+to)){
            Map<String, List> map = JedisUtil.getMap(CHAT_USER_UNREACH_PREFIX + to,List.class);
            if(map.containsKey(from.toString())){
                return map.get(from.toString());
            }
        }
        return new ArrayList<>();
    }

    public static void del(Integer key){
        if(JedisUtil.exists(CHAT_USER_UNREACH_PREFIX + key)){
            JedisUtil.del(CHAT_USER_UNREACH_PREFIX + key);
        }
    }

    public static long initSeq(){
        Long seq = JedisUtil.incr(CHAT_NETTY_SERVER_INCR);
        chat_netty_server_seq = seq;
        setChat_netty_server_seq(seq);
        return seq;
    }

}
