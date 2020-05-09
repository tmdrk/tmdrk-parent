package com.tmdrk.myboot;

//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class TmdrkSpringbootMybootApplicationTests {
//
//    @Test
//    void contextLoads() {
//    }
//
//}

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TmdrkSpringbootMybootApplicationTests {

    @Test
    public void contextLoads() {
        Logger logger = LoggerFactory.getLogger(TmdrkSpringbootMybootApplicationTests.class);
        //日志级别由低到高 trace<debug<info<warn<error
        //springboot默认输出info级别的日志
        //logging.level.com.tmdrk=debug
        logger.trace("这时{}日志...","trace");
        logger.debug("这时{}日志...","debug");
        logger.info("这时{}日志...","info");
        logger.warn("这时{}日志...","warn");
        logger.error("这时{}日志...","error");
    }

}
