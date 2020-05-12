package com.tmdrk.task.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @ClassName AsyncService
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/10 23:25
 * @Version 1.0
 **/
@Service
public class AsyncService {
    Logger logger = LoggerFactory.getLogger(getClass());

    //异步处理
    @Async
    public String hello(){
        logger.info("数据处理中");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("数据处理完成");
        return "success";
    }
}
