package com.tmdrk.myboot.entity;

import org.springframework.stereotype.Component;

/**
 * @ClassName Home
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/4/30 0:56
 * @Version 1.0
 **/
@Component
public class Home {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private Integer id;
    private String address;
}
