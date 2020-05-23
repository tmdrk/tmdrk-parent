package com.tmdrk.ace.admin.controller;

import com.tmdrk.ace.admin.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @ClassName JvmController
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/22 19:14
 * @Version 1.0
 **/
@RestController
public class JvmController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("thread/{count}")
    public ResultUtil.Result thread(@PathVariable("count")Integer count) {
        logger.info("JvmController.thread...");
        for(int i=0;i<count;i++){
            Thread thread = new Thread(()->{
                Random random = new Random();
                int times = 100+random.nextInt(200); //线程存活时间times秒 （100秒-300秒）
                for (int j=0;j<times;j++){
                    logger.info(Thread.currentThread()+" 输出："+j);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                logger.info(Thread.currentThread()+" 结束：");
            });
            thread.start();
        }
        return ResultUtil.success();
    }

    @RequestMapping("load/{count}")
    public ResultUtil.Result load(@PathVariable("count")Integer count) {
        logger.info("JvmController.load...");
        for(int i=0;i<10;i++){
            Thread thread = new Thread(()->{
                for (int j=0;j<count;j++){
                    Byte[] b = new Byte[1024*1024];
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
        return ResultUtil.success();
    }

    @RequestMapping("oom/{count}")
    public ResultUtil.Result oom(@PathVariable("count")Integer count) {
        logger.info("JvmController.oom...");
        try{
            List list = new ArrayList<>();
            for (int j=0;j<count;j++){
                Byte[] b = new Byte[1024*1024*20];
                list.add(b);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultUtil.success();
    }
}
