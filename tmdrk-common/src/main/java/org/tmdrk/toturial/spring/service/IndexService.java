package org.tmdrk.toturial.spring.service;

import io.netty.util.internal.SuppressJava6Requirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.tmdrk.toturial.spring.dao.IndexDao;
import org.tmdrk.toturial.spring.service.vehicle.Vehicle;

import javax.annotation.Resource;

/**
 * @ClassName IndexService
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/9 16:07
 * @Version 1.0
 **/
@Service
public class IndexService {
//    @Autowired(required=false)
//    @Qualifier("indexDao") //多个bean时，指定某个
//    @Autowired
    @Resource(name="indexDao1")
    IndexDao indexDao;

    public String query(){
        System.out.println("IndexService.query");
        String name = indexDao.getName();
        System.out.println("name="+name);
        System.out.println("indexDao.lable="+indexDao.getLable());
        return name;
    }
}
