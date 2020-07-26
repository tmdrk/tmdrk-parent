package org.tmdrk.toturial.spring.service.color;

import org.springframework.beans.factory.FactoryBean;

/**
 * @ClassName ColorFactoryBean
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/12 14:45
 * @Version 1.0
 **/
public class ColorFactoryBean implements FactoryBean<Color> {
    //返回一个Color对象，这个对象会添加到容器中
    @Override
    public Color getObject() throws Exception {
        return new Color();
    }

    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }

    //true 单例 false多例
    @Override
    public boolean isSingleton() {
        return true;
    }
}
