package org.tmdrk.toturial.spring.app;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.tmdrk.toturial.spring.aop.MathCalculator;
import org.tmdrk.toturial.spring.dao.IndexDao;
import org.tmdrk.toturial.spring.service.IndexService;
import org.tmdrk.toturial.spring.service.color.Red;
import org.tmdrk.toturial.spring.service.color.Yellow;
import org.tmdrk.toturial.spring.service.redis.CodisService;
import org.tmdrk.toturial.spring.service.redis.RedisService;
import org.tmdrk.toturial.spring.service.vehicle.Car;
import org.tmdrk.toturial.spring.service.vehicle.Jeep;
import org.tmdrk.toturial.spring.service.vehicle.Vehicle;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @ClassName TestSpring
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/12 12:28
 * @Version 1.0
 **/
public class TestSpring {
    public AnnotationConfigApplicationContext init(Class<?>... classes){
        return init(false,false,classes);
    }
    public AnnotationConfigApplicationContext init(boolean b,Class<?>... classes){
        return init(b,false,classes);
    }
    public AnnotationConfigApplicationContext init(boolean b,boolean d,Class<?>... classes){
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(classes);
        if(b){
            String[] beanDefinitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();
            System.out.println("print beanDefinitionNames -------------------------start");
            Arrays.stream(beanDefinitionNames).forEach(str->{
                if(d){
                    str +=  "=========================="+annotationConfigApplicationContext.getBeanDefinition(str);
                }
                System.out.println(str);      });
            System.out.println("print beanDefinitionNames -------------------------end");
        }
        return annotationConfigApplicationContext;
    }
    @Test
    public void baseOperate(){
        AnnotationConfigApplicationContext annocfgappctx = init(true,AppConfig.class);
        //获取操作系统名称
        ConfigurableEnvironment environment = annocfgappctx.getEnvironment();
        System.out.println(environment.getProperty("os.name"));

        Map<String, CodisService> beansOfType = annocfgappctx.getBeansOfType(CodisService.class);
        beansOfType.forEach((key,value)->{
            System.out.println(key+"="+value);
        });

        System.out.println(annocfgappctx.getBean(IndexService.class).getClass().getName());
        IndexService indexService = (IndexService)annocfgappctx.getBean("indexService");
        indexService.query();

        RedisService redisService1 = (RedisService)annocfgappctx.getBean("redisService");
        RedisService redisService2 = (RedisService)annocfgappctx.getBean("redisService");
        System.out.println(redisService1==redisService2);

    }

    /**
     * 给容器中注册组件的方式
     * 1 包扫描+组件注释
     * 2 @Bean[导入第三方包使用]
     * 3
     *      @Import  输入要导入的组件，默认id是全@Import[快速的给容器中导入组件]类名
     *      ImportSelector  返回需要导入组件的全类名数组
     *      ImportBeanDefinitionRegistrar
     * 4 使用spring提供的FactoryBean
     *      默认获取到的是工场bean调用getObject方法返回的对象
     *      要获取工场bean本身，我们需要给id前面加前缀&
     **/
    @Test
    public void importTest(){
        AnnotationConfigApplicationContext annocfgappctx = init(true,true,App1Config.class);
        //获取import导入的bean
        Yellow yellow = (Yellow)annocfgappctx.getBean("yellow");
        System.out.println(yellow);

        //测试ColorFactoryBean 示例具体对象和是否单例由FactoryBean决定
        Object factoryBean1 = annocfgappctx.getBean("colorFactoryBean");
        Object factoryBean2 = annocfgappctx.getBean("colorFactoryBean");
        Object factoryBean3 = annocfgappctx.getBean("&colorFactoryBean"); //获取ColorFactoryBean对象本身
        System.out.println(factoryBean1);
        System.out.println(factoryBean2);
        System.out.println(factoryBean3);

    }

    /**
     * @Author zhoujie
     * @Description 生命周期测试
     * @Date 16:54 2020/1/21
     * @Param []
     * @return void
     **/
    @Test
    public void lifeCycleTest(){
        AnnotationConfigApplicationContext annocfgappctx = init(true,MainConfigOfLifeCycle.class);
        //获取import导入的bean
//        Car car1 = (Car)annocfgappctx.getBean("car");
//        Car car2 = (Car)annocfgappctx.getBean("car");
//        System.out.println(car1);
//        System.out.println(car2);

        Jeep jeep = (Jeep)annocfgappctx.getBean("jeep");
        //关闭容器，调用销毁方法
        annocfgappctx.close();
    }

    /**
     * @Author zhoujie
     * @Description 属性赋值
     * @Date 17:43 2020/1/12
     * @Param []
     * @return void
     **/
    @Test
    public void propertyValuesTest(){
        AnnotationConfigApplicationContext annocfgappctx = init(true,MainConfigOfPropertyValues.class);
        ConfigurableEnvironment environment = annocfgappctx.getEnvironment();
        String nickName = environment.getProperty("nickName");
        System.out.println("nickName="+nickName);

        Vehicle vehicle = (Vehicle)annocfgappctx.getBean("vehicle");
        System.out.println(vehicle);

    }

    /**
     * @Author zhoujie
     * @Description 自动注入
     * @Date 10:50 2020/1/13
     * @Param []
     * @return void
     **/
    @Test
    public void autowiredTest(){
        AnnotationConfigApplicationContext annocfgappctx = init(true,MainConfigOfAutowired.class);
        IndexService indexService = (IndexService)annocfgappctx.getBean("indexService");
        System.out.println(indexService.query());
//        IndexDao bean = annocfgappctx.getBean(IndexDao.class);
//        System.out.println(bean);

        annocfgappctx.publishEvent("我的事件");
        annocfgappctx.close();
    }

    /**
     * @Author zhoujie
     * @Description //profile 控制数据源
     *  1.run configuration 设置虚拟机启动参数 -Dspring.profiles.active=dev
     *
     *
     * @Date 14:34 2020/1/14
     * @Param []
     * @return void
     **/
    @Test
    public void profileTest(){
        //创建一个ApplicationContext
        AnnotationConfigApplicationContext annocfgappctx = new AnnotationConfigApplicationContext();
        //设置需要激活的环境
        annocfgappctx.getEnvironment().setActiveProfiles("dev","test","prod");
        //注册配置类
        annocfgappctx.register(MainConfigOfProfile.class);
        //启动容器刷新
        annocfgappctx.refresh();

        String[] beanNamesForType = annocfgappctx.getBeanNamesForType(DataSource.class);
        Arrays.asList(beanNamesForType).forEach(name->{
            System.out.println(name);
            ComboPooledDataSource dataSource = (ComboPooledDataSource)annocfgappctx.getBean(name);
            System.out.println(dataSource.getJdbcUrl());
        });
    }

    @Test
    public void aopTest(){
        AnnotationConfigApplicationContext annocfgappctx = init(true,MainConfigOfAOP.class);
        MathCalculator mathCalculator = (MathCalculator)annocfgappctx.getBean("mathCalculator");
        System.out.println(mathCalculator.div(12,6));
        System.out.println("-----------------------------");
        mathCalculator.divoid(12,6);
        System.out.println("-----------------------------");
        System.out.println(mathCalculator.div(17,0));
    }

    @Test
    public void circleTest(){
        AnnotationConfigApplicationContext annocfgappctx = init(true,MainConfigOfCircleDepdence.class);
    }
}