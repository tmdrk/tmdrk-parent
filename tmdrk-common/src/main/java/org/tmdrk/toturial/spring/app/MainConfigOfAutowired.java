package org.tmdrk.toturial.spring.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.tmdrk.toturial.spring.dao.IndexDao;

/**
 * @ClassName MainConfigOfAutowired
 * @Description 自动装配
 * 1 @Autowired
 *      1) 默认按照类型去找相应的组件
 *      2) 如果找到多个相同类型的组件，再将属性的名称作为组件的id去容器中查找
 *      3) @Qualifier("indexDao1") //多个bean时，指定某个名称
 *      4) 自动装配默认一定要将属性赋好值，没有就报错,@Autowired(required=false)则不会报错，但属性可能为null
 *      5) @Primary 默认指定首选的bean，也可以继续使用@Qualifier指定bean
 * 2 @Resource
 *      JSR250规范
 *      默认装配组件名称bean,@Resource(name="indexDao1")指定名称
 * 2 @Inject
 *      JSR330规范
 *      与@Autowired一致，没有required=false功能
 * @Author zhoujie
 * @Date 2020/1/13 1:35
 * @Version 1.0
 **/
@Configuration
@ComponentScan(value={"org.tmdrk.toturial.spring.service","org.tmdrk.toturial.spring.dao","org.tmdrk.toturial.spring.listenner"})
public class MainConfigOfAutowired {
    @Bean(name="indexDao1")
    @Primary
//    @Bean
    public IndexDao indexDao(){
        IndexDao indexDao = new IndexDao();
        indexDao.setLable("1");
        return indexDao;
    }
}
