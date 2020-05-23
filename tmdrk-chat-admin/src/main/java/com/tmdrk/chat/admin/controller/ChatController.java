package com.tmdrk.chat.admin.controller;

import com.tmdrk.chat.common.entity.MessageInfo;
import com.tmdrk.chat.common.entity.Result;
import com.tmdrk.chat.common.utils.ResultUtil;
import com.tmdrk.chat.elasticsearch.service.IChatService;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

/**
 * @ClassName ChatController
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/7/18 16:07
 * @Version 1.0
 **/
@Controller
public class ChatController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IChatService chatServiceImpl;

    @ResponseBody
    @RequestMapping("/index/create")
    public Result indexCreate(@RequestParam Map<String,Object> requestMap){
        logger.info("indexCreate...");
        try {
            boolean result = chatServiceImpl.createChatIndex();
            if(result){
                return ResultUtil.success();
            }else{
                return ResultUtil.fail("创建索引失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultUtil.fail();
    }

    @ResponseBody
    @RequestMapping("/index/delete/{idx}")
    public Result indexDelete(@PathVariable String idx){
        logger.info("indexDelete...");
        try {
            boolean result = chatServiceImpl.deleteChatIndex(idx);
            if(result){
                return ResultUtil.success();
            }else{
                return ResultUtil.fail("删除索引失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultUtil.fail();
    }

    /**
     * @Author zhoujie
     * @Description //新增或更新单个文档
     * @Date 18:31 2019/7/19
     * @Param [requestMap]
     * @return
     **/
    @ResponseBody
    @RequestMapping("/doc/insert")
//    @RequestMapping(value = "/doc/insert", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Result insertDoc(@RequestParam Map<String,Object> requestMap){
//    public Result insertDoc(@RequestBody MessageInfo messageInfo){
        //{
        //	"id": "1"
        //	"from": "101"
        //	"to": "102"
        //	"toName": "张三"
        //	"type": "SINGLE_SENDING"
        //	"content": "测试分词器，后边是测试内容：spring cloud实战"
        //}
        logger.info("insertDoc...");
        try {
            MessageInfo messageInfo = new MessageInfo();
            BeanUtils.populate(messageInfo, requestMap);
            boolean result = chatServiceImpl.insertDoc(messageInfo);
            if(result){
                return ResultUtil.success();
            }else{
                return ResultUtil.fail("创建索引失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.fail();
    }

    @ResponseBody
    @RequestMapping("/doc/update/{id}")
    public Result updateDoc(@PathVariable String id,@RequestParam Map<String,Object> requestMap){
        logger.info("insertdoc...");
        try {
            MessageInfo messageInfo = new MessageInfo();
            messageInfo.setId(Integer.parseInt(id));
            BeanUtils.populate(messageInfo, requestMap);
            boolean result = chatServiceImpl.updateDoc(messageInfo);
            if(result){
                return ResultUtil.success();
            }else{
                return ResultUtil.fail("文档更新失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.fail();
    }

    @ResponseBody
    @RequestMapping("/doc/search/{id}")
    public Result searchdoc(@PathVariable String id){
        try {
            Map<String, Object> doc = chatServiceImpl.searchdoc(id);
            if(doc==null){
                return ResultUtil.fail("文档不存在");
            }
            return ResultUtil.success(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("searchdoc...");
        return ResultUtil.fail();
    }

    @ResponseBody
    @RequestMapping("/doc/delete/{id}")
    public Result deleteDoc(@PathVariable String id){
        logger.info("indexDelete...");
        try {
            boolean result = chatServiceImpl.docDelete(id);
            if(result){
                return ResultUtil.success();
            }else{
                return ResultUtil.fail("删除文档失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultUtil.fail();
    }

    @ResponseBody
    @RequestMapping("/doc/batch")
    public Result batchDoc(){
        logger.info("batchDoc...");
        try {
            boolean result = chatServiceImpl.batchDoc();
            if(result){
                return ResultUtil.success();
            }else{
                return ResultUtil.fail("批量文档失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultUtil.fail();
    }
}
