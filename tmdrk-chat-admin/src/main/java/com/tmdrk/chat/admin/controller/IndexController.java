package com.tmdrk.chat.admin.controller;

import com.tmdrk.chat.admin.service.ChatUserFriendService;
import com.tmdrk.chat.admin.service.ChatUserMessageService;
import com.tmdrk.chat.admin.service.ChatUserService;
import com.tmdrk.chat.common.cache.CacheLoader;
import com.tmdrk.chat.common.entity.Message;
import com.tmdrk.chat.common.entity.Result;
import com.tmdrk.chat.common.entity.User;
import com.tmdrk.chat.common.utils.JedisUtil;
import com.tmdrk.chat.common.utils.ResultUtil;
import com.tmdrk.chat.common.utils.TelnetUtil;
import com.tmdrk.chat.dao.entity.ChatUser;
import com.tmdrk.chat.dao.entity.ChatUserFriend;
import com.tmdrk.chat.dao.entity.ChatUserMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName IndexController
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/7/8 21:39
 * @Version 1.0
 **/
@Controller
public class IndexController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ChatUserService chatUserService;
    @Autowired
    ChatUserFriendService chatUserFriendService;
    @Autowired
    ChatUserMessageService chatUserMessageService;


    @RequestMapping("/index")
    public String index(){
        logger.info("欢迎来到后台主页");
        return "index";
    }

    @RequestMapping("/mychat")
    public String mychat(Model model){
        logger.info("欢迎来到后台主页");
        Set<String> set = JedisUtil.getSet(CacheLoader.TMDRK_CHAT_SERVER_IPS);
        List<String> list = new ArrayList(set);


        String wsUrl = getWsUrl(list);


        model.addAttribute("wsUrl",wsUrl);
        logger.info("本次获取wsUrl:{}",wsUrl);
        return "mychat";
    }

    private String getWsUrl(List<String> list) {
        String wsUrl = "";
        Random random = new Random();
        int ranInt = random.nextInt(list.size());
        for(int i=0;i<list.size();i++){
            wsUrl = list.get(ranInt);
            logger.info("检测wsUrl="+wsUrl+"是否通畅");
            String hostName;
            int port;
            String[] split1 = wsUrl.split(":");
            if(split1.length!=2){
                continue;
            }
            hostName = split1[0];
            String[] split2 = split1[1].split("/");
            if(split2.length!=2){
                continue;
            }
            port = Integer.parseInt(split2[0]);
            boolean open = TelnetUtil.telnet(hostName, port, 200);
            if(open){
                break;
            }
            //这里可以记录某个链接失败次数，若达到阈值，则从缓存中删除该链接或告警，但是服务重启时需要清空该失败次数
            ranInt = (ranInt+1)%list.size();
        }
        return wsUrl;
    }

    @ResponseBody
    @RequestMapping("/login")
    public Result login(@RequestParam Map<String,Object> requestMap){
        logger.info("登录开始"+requestMap);

        String userName = (String)requestMap.get("userId");
        if(StringUtils.isEmpty(userName)){
            return ResultUtil.fail("用户名不能为空");
        }
        ChatUser chatUser = chatUserService.selectByUserName(userName);
        if(chatUser==null){

            return ResultUtil.fail("用户名不存在");
        }
        Integer userId =  chatUser.getId();
        //获取好友id
        List<ChatUserFriend> chatUserFriends = chatUserFriendService.selectByUserId(userId);
        List<Integer> ids = chatUserFriends.stream().map(ChatUserFriend::getFriendId).collect(Collectors.toList());

        //获取好友信息
        List<ChatUser> chatUsers = chatUserService.selectByIds(ids);

        //获取未读消息
        List<ChatUserMessage> unReadList = chatUserMessageService.getUnReadList(userId);
        List<Integer> msgIds = unReadList.stream().map(ChatUserMessage::getId).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(msgIds)){
            int i = chatUserMessageService.updateByIds(msgIds);
            logger.info("将["+i+"]条数据更新为已读");
        }

        User userInfo = new User(chatUser.getId(),chatUser.getNickName(),null,chatUser.getHeadImgUrl(),null);
        List<User> list = new ArrayList<>();
        chatUsers.stream().forEach(us->list.add(new User(us.getId(),us.getNickName(),null,us.getHeadImgUrl(),null)));
        //设置好友属性
        userInfo.setFriends(list);

        //设置好友未读消息属性
        Map<Integer, User> userMap = list.stream().collect(Collectors.toMap(User::getId, user -> user));
        unReadList.stream().forEach(ur->{
            Integer fromId = ur.getFromId();
            User user = userMap.get(fromId);
            List<Message> messages = user.getMessages();
            if(messages==null){
                messages = new ArrayList<>();
                user.setMessages(messages);
            }
            Message message = new Message(ur.getFromId(),ur.getToId(),ur.getMessage(),ur.getMessageType().toString());
            messages.add(message);
        });

        return ResultUtil.success(userInfo);
    }
}
