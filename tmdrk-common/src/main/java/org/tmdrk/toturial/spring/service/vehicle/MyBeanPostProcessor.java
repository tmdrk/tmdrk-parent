package org.tmdrk.toturial.spring.service.vehicle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @ClassName MyBeanPostProcessor
 * @Description 后置处理器
 * 将后置处理器加入到容器中
 * @Author zhoujie
 * @Date 2020/1/12 15:43
 * @Version 1.0
// **/
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization "+beanName+"=>"+bean);
        //可以返回包装后的bean，也可以返回原始bean
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization "+beanName+"=>"+bean);
        return bean;
    }
}
