package org.tmdrk.toturial.spring.app;

import org.springframework.context.annotation.*;
import org.tmdrk.toturial.spring.service.vehicle.Vehicle;

/**
 * @ClassName MainConfigOfPropertyValues
 * @Description 属性赋值
 * @Author zhoujie
 * @Date 2020/1/12 17:41
 * @Version 1.0
 **/
//读取配置文件,加载到运行环境变量里面
@Configuration
@PropertySource({"classpath:/config/vehicle.properties"})
public class MainConfigOfPropertyValues {
    @Bean
    public Vehicle vehicle(){
        return new Vehicle();
    }
}
