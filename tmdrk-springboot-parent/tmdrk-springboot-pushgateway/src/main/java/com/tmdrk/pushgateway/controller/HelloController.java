package com.tmdrk.pushgateway.controller;

import com.tmdrk.pushgateway.metrics.PushGatewayUtil;
import com.tmdrk.pushgateway.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName HelloController
 * @Description
 * @Author zhoujie
 * @Date 2020/5/24 1:55
 * @Version 1.0
 **/
@RestController
public class HelloController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("hello")
    public ResultUtil.Result hello() {
        logger.info("HelloController.hello...");
        PushGatewayUtil.pushCounter(1);
        return ResultUtil.success();
    }
}
