package org.tmdrk.toturial.spring.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.tmdrk.toturial.spring.service.vehicle.Car;

/**
 * @ClassName MainConfigOfLifeCycle
 * @Description
 * bean的生命周期
 *      bean的创建---初始化---销毁过程
 * 容器管理bean的生命周期
 * 我们可以自定义初始化和销毁方法，容器在bean进行到当前生命周期的时候调用我们自定义初始化和销毁方法
 *
 * 构造（创建对象）
 *      单实例，容器启动的时候创建
 *      多实例，在每次获取的时候创建对象
 * 初始化
 *      对象创建完成，并赋值好，调用初始化方法
 * 销毁
 *      单实例，容器关闭的时候销毁
 *      多实例，容器不会调用销毁方法
 *
 * 1 指定初始化和销毁方法
 *      @Bean指定init-method和destory-method方法
 *
 * 2 通过让bean实现InitializingBean(定义初始化逻辑),DisposableBean(定义销毁逻辑)
 *
 * 3 可以使用JSR250：
 *      @PostConstruct 在bean创建完成，属性赋值完，来执行初始化方法
 *      @PreDestory 在容器销毁之前通知我们进行清理工作
 * 4 BeanPostProcessor【interface】:bean的后置处理器
 *      bean初始化前后进行一些处理
 *      postProcessBeforeInitialization 初始化之前工作
 *      postProcessAfterInitialization 初始化之后工作
 *
 *      原理 AbstractAutowireCapableBeanFactory
 *      populateBean(beanName, bd, bw);给bean属性赋值
 *      {
 *		if (mbd == null || !mbd.isSynthetic()) {
 *	    //  遍历执行容器中所有的BeanPostProcessor，一旦有一个返回null，就跳出循环
 * 			wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
 * 		}
 *
 *      invokeInitMethods(beanName, wrappedBean, mbd);//执行初始化方法
 *
 *		if (mbd == null || !mbd.isSynthetic()) {
 * 			wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
 * 		}
 *      }
 *
 * spring底层对 BeanPostProcessor 的使用
 *      bean赋值，注入其他组件，@Autowired，生命周期注解功能，@Async，XXX都是使用BeanPostProcessor完成的
 *
 * @Author zhoujie
 * @Date 2020/1/12 14:59
 * @Version 1.0
 **/
@Configuration
@ComponentScan(value="org.tmdrk.toturial.spring.service.vehicle")
public class MainConfigOfLifeCycle {
    @Scope(value="prototype")
    @Bean(initMethod = "init",destroyMethod = "destory")
    public Car car(){
        return new Car();
    }
}
