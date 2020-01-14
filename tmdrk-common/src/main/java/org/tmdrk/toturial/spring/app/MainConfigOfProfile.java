package org.tmdrk.toturial.spring.app;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MainConfigOfProfile
 * @Description Profile Spring为我们提供的可以根据当前环境，动态的激活和切换一系列组件的功能
 *
 * 开发环境、测试环境、生产环境
 * 数据源 A、B、C
 * @Author zhoujie
 * @Date 2020/1/14 13:11
 * @Version 1.0
 **/
@Configuration
@ComponentScan(value="org.tmdrk.toturial.spring.service")
public class MainConfigOfProfile {
}
