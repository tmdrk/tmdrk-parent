package com.tmdrk.ace.admin.service;

import com.tmdrk.ace.admin.entity.AceMenu;

import java.util.List;
import java.util.Map;

/**
 * @ClassName MenuService
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/6 0:41
 * @Version 1.0
 **/
public interface MenuService {
    AceMenu getAceMenu(int menuId);
    List<AceMenu> getAllAceMenu();
    List<Map<String,Object>> getAllAceMenuTree();
}
