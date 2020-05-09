package org.tmdrk.toturial.spring.app;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.*;
import org.springframework.util.StringValueResolver;
import org.tmdrk.toturial.spring.service.redis.RedisService;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * @ClassName MainConfigOfProfile
 * @Description Profile Spring为我们提供的可以根据当前环境，动态的激活和切换一系列组件的功能
 *
 * 开发环境、测试环境、生产环境
 * 数据源 A、B、C
 *
 * 可以作用在【方法上和类上】
 * annocfgappctx.getEnvironment().setActiveProfiles("dev","test","prod");
 * 对于加了Profile注解的，必须在环境中设置激活属性才能被容器加载，没有设置的不受影响
 *
 * @Author zhoujie
 * @Date 2020/1/14 13:11
 * @Version 1.0
 **/
@Profile("test")
@Configuration
//@ComponentScan(value="org.tmdrk.toturial.spring.service")
@PropertySource("classpath:/config/db.properties")
public class MainConfigOfProfile implements EmbeddedValueResolverAware {
    @Value("${db.user}")
    private String user;

    private StringValueResolver valueResolver;

    private String driverClass;

    @Profile("dev")
    @Bean
    public RedisService redisService(){
        System.out.println("创建redisService bean...");
        return new RedisService();
    }

    @Profile("dev")
    @Bean("devDataSource")
    public DataSource dataSourceDev(@Value("${db.password}")String pwd) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(pwd);
        dataSource.setJdbcUrl("jdbc:mysql://192.168.1.11:3306/dev");
        dataSource.setDriverClass(driverClass);
        return dataSource;
    }

    @Profile("test")
    @Bean("testDataSource")
    public DataSource dataSourceTest(@Value("${db.password}")String pwd) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(pwd);
        dataSource.setJdbcUrl("jdbc:mysql://192.168.1.11:3306/test");
        dataSource.setDriverClass(driverClass);
        return dataSource;
    }

    @Profile("prod")
    @Bean("prodDataSource")
    public DataSource dataSourceProd(@Value("${db.password}")String pwd) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(pwd);
        dataSource.setJdbcUrl("jdbc:mysql://192.168.1.11:3306/pro");
        dataSource.setDriverClass(driverClass);
        return dataSource;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.valueResolver = resolver;
        driverClass = valueResolver.resolveStringValue("${db.driverClass}");
    }
}
