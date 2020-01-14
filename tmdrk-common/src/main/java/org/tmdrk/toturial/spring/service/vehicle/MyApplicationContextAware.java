package org.tmdrk.toturial.spring.service.vehicle;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @ClassName MyApplicationContextAware
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/12 16:59
 * @Version 1.0
 **/
@Component
public class MyApplicationContextAware implements ApplicationContextAware {
    ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
