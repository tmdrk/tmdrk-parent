package com.tmdrk.ace.admin.controller;

import com.tmdrk.ace.admin.entity.AceMenu;
import com.tmdrk.ace.admin.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @ClassName AceMenuController
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/6 0:45
 * @Version 1.0
 **/
@Controller
public class AceMenuController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MenuService menuService;

    @ResponseBody
    @RequestMapping("menu/{id}")
    public AceMenu getAceMenu(@PathVariable(value = "id")Integer id){
        logger.info("AceMenuController.getAceMenu...");
        return menuService.getAceMenu(id);
    }

    @ResponseBody
    @RequestMapping("menus")
    public List<AceMenu> getAllAceMenu(){
        logger.info("AceMenuController.getAllAceMenu...");
        return menuService.getAllAceMenu();
    }

    @ResponseBody
    @RequestMapping("tree/menus")
    public List<Map<String,Object>> getAllAceMenuTree(){
        logger.info("AceMenuController.getAllAceMenuTree...");
        return menuService.getAllAceMenuTree();
    }
}
