package org.tmdrk.toturial.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.tmdrk.toturial.spring.service.IndexService;

/**
 * @ClassName IndexController
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/10 23:35
 * @Version 1.0
 **/
@Controller
public class IndexController {
    @Autowired
    IndexService indexService;
    public String getIndex(){
        return indexService.query();
    }
}
