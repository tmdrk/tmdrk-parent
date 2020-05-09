package com.tmdrk.springmvc.test1;


import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.File;

/**
 * @ClassName SpringApplication
 * @Description  自定义springboot启动器，模拟springboot启动过程
 *
 * 报Jsp类没有发现异常，需要加maven依赖
 *         <dependency>
 *             <groupId>org.apache.tomcat.embed</groupId>
 *             <artifactId>tomcat-embed-jasper</artifactId>
 *             <version>8.5.34</version>
 *         </dependency>
 *
 * 启动过程
 * servlet3.0规范：共享库 / 运行时可插拔性
 * tomcat容器启动时会扫描META-INF/services/javax.servlet.ServletContainerInitializer文件
 * 获取org.springframework.web.SpringServletContainerInitializer类路径
 * SPI web容器启动会扫描javax.servlet.ServletContainerInitializer，拿到里面的文件全路径，加载SpringServletContainerInitializer
 *  并根据@HandlesTypes(WebApplicationInitializer.class)，传入Set<Class<?>> webAppInitializerClasses，调用onStartUp方法
 *
 * @Author zhoujie
 * @Date 2020/4/28 17:09
 * @Version 1.0
 **/
public class SpringApplication {
    public static void run(String[] args) {
        File file = new File(System.getProperty("java.io.tmpdir"));//操作系统临时目录
        //使用内嵌的tomcat启动容器
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        // 因为不需要触发WebApplicationInitializer实现类的方法，所以用tomcat.addContext方法即可。
        tomcat.addContext("/","F:\\dir1"); //普通的web项目，contextPath:访问根路径，docBase：工作路径
        //addWebapp告诉tomcat这里需要执行web规范，参考注释中的【servlet3.0规范：共享库 / 运行时可插拔性】
        //tomcat.addWebapp("/",file.getAbsolutePath());
        try {
            tomcat.start();


            /*** 此处相比较test的结构，更接近springboot的实现过程，但是，执行会报错，还需要详细研究 ***/
            // Load Spring web application configuration
            AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
            ac.register(Test1Config.class);
            ac.refresh(); //配置类TestConfig加@EnableWebMvc注解后，该行代码必须要删除，不然会报错，原因与父子容器有关，需要深入理解springmvc源码。
            // Create and register the  DispatcherServlet
            DispatcherServlet servlet = new DispatcherServlet(ac);
            Wrapper wrapper = tomcat.addServlet("/", "app1", servlet);
            wrapper.setLoadOnStartup(1);//容器初始化时就加载该servlet对象，1:优先级最高，0:不加载
            wrapper.addMapping("/app1/*");

            tomcat.getServer().await();//阻塞，等待连接，springboot会启动新线程调用阻塞方法。
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }
}
