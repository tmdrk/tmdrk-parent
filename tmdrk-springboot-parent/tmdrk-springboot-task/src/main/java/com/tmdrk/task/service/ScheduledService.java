package com.tmdrk.task.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @ClassName ScheduledService
 * @Description
 * @Author zhoujie
 * @Date 2020/5/10 23:33
 * @Version 1.0
 **/
@Service
public class ScheduledService {
    Logger logger = LoggerFactory.getLogger(getClass());

    //注意，springboot默认是使用单线程处理任务，使用时需要自定义线程池。
    //second , minute, hour, day of month(日), month , day of week(周)
    // 0 * * * * MON-FRI (周一到周五，每一分钟开始执行一次)
    //@Scheduled(cron = "0,1,2,3,4 * * * * *")
    //@Scheduled(cron = "0-8 * * * * *")
    //@Scheduled(cron = "0 0 2 LW * ?") //每个月最后一个工作日，凌晨两点指定一次
    //@Scheduled(cron = "0 0 2 4 ? 1#2") //每个月第二个周一凌晨2-4点，每个整点执行一次
    //@Scheduled(cron = "0 0 2 ? * 6L") //每个月最后一个周六凌晨两点执行一次
    @Scheduled(cron = "0/4 * * * * *") //每4秒执行一次
    public void hello(){
        logger.info("hello...");
    }
}
