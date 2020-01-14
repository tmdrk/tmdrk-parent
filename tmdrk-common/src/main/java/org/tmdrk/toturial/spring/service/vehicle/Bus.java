package org.tmdrk.toturial.spring.service.vehicle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * @ClassName Bus
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/12 15:25
 * @Version 1.0
 **/
@Service
public class Bus extends Vehicle implements InitializingBean, DisposableBean {
    public Bus(){
        System.out.println("bus constructor...");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("bus afterPropertiesSet...");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("bus destroy...");
    }
}
