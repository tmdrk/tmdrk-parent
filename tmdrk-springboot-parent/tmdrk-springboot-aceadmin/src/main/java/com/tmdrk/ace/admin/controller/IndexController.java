package com.tmdrk.ace.admin.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.tmdrk.ace.admin.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

/**
 * @ClassName IndexController
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/5 0:48
 * @Version 1.0
 **/
@Controller
public class IndexController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MenuService menuService;

    @GetMapping("home")
    public String home(Model model){
        logger.info("IndexController.home...");

//        List<Map<String, Object>> menuList = new ArrayList<>();
//        String m1 = "{\"menuId\":1,\"menuName\":\"一级\",\"menuDesc\":\"一级菜单\",\"parentMenuId\":\"0\",\"menuUrl\":null,\"menuIcon\":\"\",\"weight\":1}";
//        String m2 = "{\"menuId\":2,\"menuName\":\"二级\",\"menuDesc\":\"二级菜单\",\"parentMenuId\":\"0\",\"menuUrl\":null,\"menuIcon\":\"\",\"weight\":2}";
//        String m3 = "{\"menuId\":3,\"menuName\":\"三级\",\"menuDesc\":\"三级菜单\",\"parentMenuId\":\"0\",\"menuUrl\":null,\"menuIcon\":\"\",\"weight\":3}";
//        String m11 = "{\"menuId\":4,\"menuName\":\"一级一级\",\"menuDesc\":\"一级一级\",\"parentMenuId\":\"1\",\"menuUrl\":\"/users\",\"weight\":1,\"menuIcon\":\"\"}";
//        Map menu1 = JSON.parseObject(m1, Map.class);
//        Map menu2 = JSON.parseObject(m2, Map.class);
//        Map menu3 = JSON.parseObject(m3, Map.class);
//        Map menu11 = JSON.parseObject(m11, Map.class);
//        Map<String, Object> me1 = Maps.newHashMap();
//        Map<String, Object> me2 = Maps.newHashMap();
//        Map<String, Object> me3 = Maps.newHashMap();
//        Map<String, Object> me11 = Maps.newHashMap();
//        me11.put("node",menu11);
//        me11.put("childNodes", Collections.EMPTY_LIST);
//        List<Map<String, Object>> menuList1 = new ArrayList<>();
//        menuList1.add(me11);
//        me1.put("node",menu1);
//        me1.put("childNodes",menuList1);
//        me2.put("node",menu2);
//        me2.put("childNodes",Collections.EMPTY_LIST);
//        me3.put("node",menu3);
//        me3.put("childNodes",Collections.EMPTY_LIST);
//        menuList.add(me1);
//        menuList.add(me2);
//        menuList.add(me3);
        model.addAttribute("menuJson", menuService.getAllAceMenuTree());
        return "home";
    }
}


