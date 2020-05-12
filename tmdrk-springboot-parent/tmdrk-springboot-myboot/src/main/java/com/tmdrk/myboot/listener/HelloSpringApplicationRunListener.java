package com.tmdrk.myboot.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @ClassName HelloSpringApplicationRunListener
 * @Description
 * @Author zhoujie
 * @Date 2020/5/9 19:24
 * @Version 1.0
 **/
public class HelloSpringApplicationRunListener implements SpringApplicationRunListener {
    private final SpringApplication application;

    private final String[] args;

    private final SimpleApplicationEventMulticaster initialMulticaster;

    public HelloSpringApplicationRunListener(SpringApplication application, String[] args){
        this.application = application;
        this.args = args;
        this.initialMulticaster = new SimpleApplicationEventMulticaster();
        for (ApplicationListener<?> listener : application.getListeners()) {
            this.initialMulticaster.addApplicationListener(listener);
        }
    }

    @Override
    public void starting() {
        System.out.println("HelloSpringApplicationRunListener.starting...");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        System.out.println("HelloSpringApplicationRunListener.environmentPrepared..."+environment);
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println("HelloSpringApplicationRunListener.contextPrepared..."+context);
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        System.out.println("HelloSpringApplicationRunListener.contextLoaded..."+context);
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        System.out.println("HelloSpringApplicationRunListener.started..."+context);
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        System.out.println("HelloSpringApplicationRunListener.running..."+context);
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        System.out.println("HelloSpringApplicationRunListener.failed..."+context+" "+exception);
    }
}
