package org.tmdrk.toturial.spring.listenner;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Repository;

/**
 * @ClassName MyApplicatonListener
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/7/12 10:08
 * @Version 1.0
 **/
@Repository
public class MyApplicatonListener implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("事件监听：" + event);
    }
}
