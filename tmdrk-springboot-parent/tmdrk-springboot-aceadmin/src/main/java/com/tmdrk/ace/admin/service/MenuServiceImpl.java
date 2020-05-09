package com.tmdrk.ace.admin.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tmdrk.ace.admin.entity.AceMenu;
import com.tmdrk.ace.admin.entity.AceMenuExample;
import com.tmdrk.ace.admin.mapper.AceMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName MenuServiceImpl
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/6 0:42
 * @Version 1.0
 **/
@Service
public class MenuServiceImpl implements MenuService{
    @Autowired
    AceMenuMapper aceMenuMapper;

    @Override
    public AceMenu getAceMenu(int menuId) {
        AceMenu aceMenu = aceMenuMapper.selectByPrimaryKey(menuId);
        return aceMenu;
    }

    /**
     * @Author zhoujie
     * @Description 查询所有菜单
     * @Date 13:47 2020/5/6
     * @Param []
     * @return java.util.List<com.tmdrk.ace.admin.entity.AceMenu>
     **/
    @Override
    public List<AceMenu> getAllAceMenu() {
        AceMenuExample aceMenuExample = new AceMenuExample();
        List<AceMenu> aceMenus = aceMenuMapper.selectByExample(aceMenuExample);
        return aceMenus;
    }

    /**
     * @Author zhoujie
     * @Description 获取菜单树结构
     * @Date 13:46 2020/5/6
     * @Param []
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public List<Map<String, Object>> getAllAceMenuTree() {
        String tree = "tree-";
        List<AceMenu> aceMenus = getAllAceMenu();
        Map<String,Map<String, Object>> rootMap = Maps.newHashMap();
        for(AceMenu aceMenu:aceMenus) {
            if (!rootMap.containsKey(tree + aceMenu.getParentMenuId())) {
                Map<String, Object> treeNode = Maps.newHashMap();
                List<Map<String, Object>> list = Lists.newArrayList();
                treeNode.put("node",null);
                treeNode.put("childNodes",list);
                rootMap.put(tree + aceMenu.getParentMenuId(), treeNode);
            }
            if (!rootMap.containsKey(tree + aceMenu.getMenuId())) {
                Map<String, Object> treeNode = Maps.newHashMap();
                List<Map<String, Object>> list = Lists.newArrayList();
                treeNode.put("node",aceMenu);
                treeNode.put("childNodes",list);
                rootMap.put(tree + aceMenu.getMenuId(), treeNode);

                Map<String, Object> parentTreeNode = rootMap.get(tree +aceMenu.getParentMenuId());
                List<Map<String, Object>> parentChildNodes = (List)parentTreeNode.get("childNodes");
                parentChildNodes.add(treeNode);
            }else{
                Map<String, Object> treeNode = rootMap.get(aceMenu.getMenuId());
                treeNode.put("node",aceMenu);
                Map<String, Object> parentTreeNode = rootMap.get(tree +aceMenu.getParentMenuId());
                List<Map<String, Object>> parentChildNodes = (List)parentTreeNode.get("childNodes");
                parentChildNodes.add(treeNode);
            }
        }
        List<Map<String, Object>> menuList = (List)rootMap.get(tree +"0").get("childNodes");
        return menuList;
    }
}
