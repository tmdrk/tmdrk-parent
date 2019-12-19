package com.tmdrk.chat.server.controller;

import com.tmdrk.chat.common.cache.CacheLoader;
import com.tmdrk.chat.common.entity.User;
import com.tmdrk.chat.common.utils.GsonUtils;
import com.tmdrk.chat.common.utils.IpUtils;
import com.tmdrk.chat.common.utils.JedisUtil;
import com.tmdrk.chat.common.utils.RedisUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName RedisTestController
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/9 17:58
 * @Version 1.0
 **/
@Controller
public class RedisTestController {
    private static Logger logger = Logger.getLogger(IndexController.class);
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @ResponseBody
    @RequestMapping("/redis")
    public Map redis(@RequestParam Map<String,Object> requestMap){
        redisUtil.set("test:name",111);
        System.out.println("test:name="+redisUtil.get("test:name"));
        Map<String,Object> retMap = new HashMap<>();
        User user = new User();
        User user1 = new User();
        List<User> friends = new ArrayList<>();
        user1.setId(456456447);
        friends.add(user1);
        user.setId(23423);
        user.setFriends(friends);
        JedisUtil.set("test:name",user);
        System.out.println("test:name="+JedisUtil.get("test:name",User.class).getId());
        User user2 = JedisUtil.get("test:name",User.class);
        List<User> friends2 = user2.getFriends();

        user.setId(346346);
        redisUtil.set("test:name",user);
        user = (User)redisUtil.get("test:name");
        System.out.println("test:name="+user.getId());
        redisTemplate.opsForValue();
        Map map= JedisUtil.get("CHAT_CHAT_USER_UNREACH_PREFIX_104",HashMap.class);
        System.out.println("test:name="+ GsonUtils.toJson(map).toString());

        Map<String,List> maps = new HashMap<>();
        maps.put("myTest",friends);
        JedisUtil.set("10001",maps);
        maps = JedisUtil.get("10001",HashMap.class);

        Long lo = JedisUtil.incr(CacheLoader.CHAT_NETTY_SERVER_INCR);

        logger.info(IpUtils.getLocalIpAddr());
        return retMap;
    }
}
