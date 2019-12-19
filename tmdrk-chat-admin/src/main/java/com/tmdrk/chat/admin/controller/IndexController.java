package com.tmdrk.chat.admin.controller;

import com.tmdrk.chat.common.cache.CacheLoader;
import com.tmdrk.chat.common.entity.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @ClassName IndexController
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/7/8 21:39
 * @Version 1.0
 **/
@Controller
public class IndexController {
    private Logger logger = Logger.getLogger(IndexController.class);
    @RequestMapping("/index")
    public String index(){
        logger.info("欢迎来到后台主页");
        return "index";
    }

    @RequestMapping("/mychat")
    public String mychat(){
        logger.info("欢迎来到后台主页");
        return "mychat";
    }


    @ResponseBody
    @RequestMapping("/login")
    public Map login(@RequestParam Map<String,Object> requestMap){
        logger.info("登录开始");
        Map resultMap = new HashMap();

        String userIdStr = (String)requestMap.get("userId");
        if(StringUtils.isEmpty(userIdStr)){
            resultMap.put("code",0);
            resultMap.put("msg","用户名不能为空");
            return resultMap;
        }
        Integer userId = Integer.parseInt(userIdStr);
        List<Integer> userIds = getUserIds();
        if(!userIds.contains(userId)){
            resultMap.put("code",0);
            resultMap.put("msg","用户名不存在");
            return resultMap;
        }
        User userInfo = getUserInfo(userId);
        resultMap.put("code",1);
        resultMap.put("list",userInfo);
        return resultMap;
    }
    private List<Integer> getUserIds(){
        Integer[] userIds = {101,102,103,104};
        return Arrays.asList(userIds);
    }
    private User getUserInfo(Integer userId){
        User user1 = new User(101,"森水",null,"../static/img/friend1.jpg",null);
        User user2 = new User(102,"张三",null,"../static/img/friend2.jpg",null);
        User user3 = new User(103,"李四",null,"../static/img/friend3.jpg",null);
        User user4 = new User(104,"王二麻",null,"../static/img/friend4.jpg",null);
        List<User> list1 = new ArrayList<User>(){{
            add(user2);add(user3);add(user4);
        }};
        List<User> list2 = new ArrayList<User>(){{
            add(user1);add(user3);add(user4);
        }};
        List<User> list3 = new ArrayList<User>(){{
            add(user1);add(user2);add(user4);
        }};
        List<User> list4 = new ArrayList<User>(){{
            add(user1);add(user2);add(user3);
        }};
        Map<Integer,List> map1 = new HashMap<>();
        map1.put(101,list1);
        map1.put(102,list2);
        map1.put(103,list3);
        map1.put(104,list4);

        User retUser = null;
        if(userId==101){
            user1.setFriends(map1.get(userId));
            for (User user:user1.getFriends()) {
                user.setMessages(CacheLoader.get(userId,user.getId()));
            }
            retUser =  user1;
        }else if(userId==102){
            user2.setFriends(map1.get(userId));

            //测试缓存
//            Message message = new Message();
//            message.setFrom(101);
//            message.setTo(102);
//            message.setType("SINGLE_SENDING");
//            message.setMessage("你好，我是一个大苦尽甘来大圣归来看似简单");
//            RedisUtils.put(message);
            for (User user:user2.getFriends()) {
                user.setMessages(CacheLoader.get(userId,user.getId()));
            }

            retUser = user2;
        }else if(userId==103){
            user3.setFriends(map1.get(userId));
            for (User user:user3.getFriends()) {
                user.setMessages(CacheLoader.get(userId,user.getId()));
            }
            retUser = user3;
        }else if(userId==104){
            user4.setFriends(map1.get(userId));
            for (User user:user4.getFriends()) {
                user.setMessages(CacheLoader.get(userId,user.getId()));
            }
            retUser = user4;
        }
        CacheLoader.del(userId);
        return retUser;
    }
}
