package org.tmdrk.toturial.spring.condition;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.tmdrk.toturial.spring.service.color.Yellow;

/**
 * @ClassName MyImportBeanDefinitionRegistrar
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/12 13:59
 * @Version 1.0
 **/
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     * @Author zhoujie
     * @Description
     * @Date 14:00 2020/1/12
     * @Param [
     * importingClassMetadata : 当前类的注解信息,
     * registry : beanDefination注册类,可手动注册bean
     * ]
     * @return void
     **/
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean red = registry.containsBeanDefinition("org.tmdrk.toturial.spring.service.color.Red");
        boolean blue = registry.containsBeanDefinition("org.tmdrk.toturial.spring.service.color.Blue");
        RootBeanDefinition beanDefinition = new RootBeanDefinition(Yellow.class);
        if(red && blue){
            registry.registerBeanDefinition("yellow",beanDefinition);
        }
    }
}
