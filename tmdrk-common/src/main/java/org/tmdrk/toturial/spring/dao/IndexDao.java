package org.tmdrk.toturial.spring.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @ClassName IndexDao
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/9 17:23
 * @Version 1.0
 **/
@Repository
//@Component //该注解会令ComponentScan includeFilters包含它时，把@Controller也给加载进来
public class IndexDao {
    private String lable = "0";
    public String getName(){
        System.out.println("IndexDao.getName");
        return "zhoujie";
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }
}
