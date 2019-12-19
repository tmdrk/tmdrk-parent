package com.tmdrk.chat.admin.controller;

import com.tmdrk.chat.common.entity.MessageInfo;
import com.tmdrk.chat.common.entity.Result;
import com.tmdrk.chat.common.entity.es.TestProduct;
import com.tmdrk.chat.common.utils.ResultUtil;
import com.tmdrk.chat.common.utils.reptile.JDProductDetial;
import com.tmdrk.chat.elasticsearch.service.ElasticSearchServiceImpl;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SearchController
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/19 17:07
 * @Version 1.0
 **/
@Controller
public class ElasticSearchController {
    private Logger logger = Logger.getLogger(ElasticSearchController.class);
    @Autowired
    private ElasticSearchServiceImpl elasticSearchServiceImpl;

    @ResponseBody
    @RequestMapping("/es/index/create")
    public Result createEsIndex(@RequestParam Map<String,Object> requestMap){
        logger.info("createEsIndex...");
        try {
            boolean result = elasticSearchServiceImpl.createIndex(String.valueOf(requestMap.get("indexName")),String.valueOf(requestMap.get("type")), TestProduct.class);
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
    @RequestMapping("/es/index/delete/{idx}")
    public Result deleteEsIndex(@PathVariable Map<String,Object> requestMap){
        logger.info("deleteEsIndex...");
        try {
            boolean result = elasticSearchServiceImpl.deleteIndex(String.valueOf(requestMap.get("indexName")));
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

    @ResponseBody
    @RequestMapping("/es/insert/doc")
    public Result insertEsDoc(@RequestParam Map<String,Object> requestMap){
//    public Result insertDoc(@RequestBody MessageInfo messageInfo){
        //{
        //	"id": "1"
        //	"from": "101"
        //	"to": "102"
        //	"toName": "张三"
        //	"type": "SINGLE_SENDING"
        //	"content": "测试分词器，后边是测试内容：spring cloud实战"
        //}
        logger.info("insertEsDoc...");
        try {
            TestProduct testProduct = new TestProduct();
            BeanUtils.populate(testProduct, requestMap);
            int result = elasticSearchServiceImpl.insertOrUpdate(String.valueOf(requestMap.get("indexName")),String.valueOf(requestMap.get("type")),String.valueOf(requestMap.get("id")),testProduct);
            if(result==1){
                return ResultUtil.success("插入成功");
            }else if(result==2){
                return ResultUtil.success("更新成功");
            }else if(result==3){
                return ResultUtil.success("操作未知");
            }else{
                return ResultUtil.success("操作失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.fail();
    }

    @ResponseBody
    @RequestMapping("/es/insert/docJD")
    public Result insertEsDocFromJD(@RequestParam Map<String,Object> requestMap){
        logger.info("insertEsDoc...");
        try {
            List<TestProduct> productList = JDProductDetial.getProductList();
            int total = productList.size();
            int success = 0;
            int update = 0;
            int unknow = 0;
            int fail = 0;
            for(TestProduct testProduct:productList){
                System.out.println(testProduct.toString());
                BeanUtils.populate(testProduct, requestMap);
                int result = elasticSearchServiceImpl.insertOrUpdate(String.valueOf(requestMap.get("indexName")),String.valueOf(requestMap.get("type")),String.valueOf(testProduct.getId()),testProduct);
                if(result==1){
                    success++;
                }else if(result==2){
                    update++;
                }else if(result==3){
                    unknow++;
                }else{
                    fail++;
                }
            }
            return ResultUtil.success("处理总数："+total+" 插入数："+success+" 更新数："+update+" 未知数："+unknow+" 失败数："+fail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.fail();
    }

    @ResponseBody
    @RequestMapping("/es/delete/doc")
    public Result deleteEsDoc(@RequestParam Map<String,Object> requestMap){
        logger.info("deleteEsDoc...");
        try {
            TestProduct testProduct = new TestProduct();
            BeanUtils.populate(testProduct, requestMap);
            boolean result = elasticSearchServiceImpl.delete(String.valueOf(requestMap.get("indexName")),String.valueOf(requestMap.get("type")),String.valueOf(requestMap.get("id")));
            if(result){
                return ResultUtil.success();
            }else{
                return ResultUtil.fail("删除文档失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.fail();
    }

    @ResponseBody
    @RequestMapping("/es/update/doc")
    public Result updateEsDoc(@RequestParam Map<String,Object> requestMap){
        logger.info("updateEsDoc...");
        try {
            TestProduct testProduct = new TestProduct();
            BeanUtils.populate(testProduct, requestMap);
            boolean result = elasticSearchServiceImpl.update(String.valueOf(requestMap.get("indexName")),String.valueOf(requestMap.get("type")),String.valueOf(requestMap.get("id")),testProduct);
            if(result){
                return ResultUtil.success();
            }else{
                return ResultUtil.fail("更新文档失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.fail();
    }

    @ResponseBody
    @RequestMapping("/es/search/{id}")
    public Result searchEsById(@RequestParam Map<String,Object> requestMap,@PathVariable("id") String id){
        logger.info("searchEsById...");
        try {
            Map<String, Object> resultMap = elasticSearchServiceImpl.searchById(String.valueOf(requestMap.get("indexName")),String.valueOf(requestMap.get("type")),id);
            return ResultUtil.success(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.fail();
    }
}
