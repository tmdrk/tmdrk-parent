package com.tmdrk.myboot.entity;

import org.springframework.beans.factory.annotation.Value;

/**
 * @ClassName Animal
 * @Description 测试@ImportResource
 * @Author zhoujie
 * @Date 2020/4/30 14:46
 * @Version 1.0
 **/
public class Animal {
    @Value("bird")
    private String name;
    @Value("12")
    private Integer speed;
    @Value("2")
    private Integer legs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getLegs() {
        return legs;
    }

    public void setLegs(Integer legs) {
        this.legs = legs;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", speed=" + speed +
                ", legs=" + legs +
                '}';
    }
}
