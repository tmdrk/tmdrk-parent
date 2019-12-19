package com.tmdrk.chat.server.controller;

import com.tmdrk.chat.common.cache.CacheLoader;
import com.tmdrk.chat.common.entity.User;
import com.tmdrk.chat.common.utils.GsonUtils;
import com.tmdrk.chat.common.utils.IpUtils;
import com.tmdrk.chat.common.utils.JedisUtil;
import com.tmdrk.chat.common.utils.RedisUtil;
import com.tmdrk.chat.server.handler.websocket.TextWebSocketFrameHandler;
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
 * @ClassName IndexController
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/7/12 15:51
 * @Version 1.0
 **/
@Controller
public class IndexController {
    private static Logger logger = Logger.getLogger(IndexController.class);
    @ResponseBody
    @RequestMapping("/login")
    public Map login(@RequestParam Map<String,Object> requestMap){
        Map<String,Object> retMap = new HashMap<>();
        return retMap;
    }
}
