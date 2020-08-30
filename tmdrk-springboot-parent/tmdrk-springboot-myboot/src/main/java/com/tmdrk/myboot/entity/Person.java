package com.tmdrk.myboot.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName Person
 * @Description 人测试
 * 两种配置文件读取方式
 * 1 @ConfigurationProperties 优先级更高
 * 2 @Value
 *
 * @Author zhoujie
 * @Date 2020/4/30 0:40
 * @Version 1.0
 **/
@PropertySource(value="classpath:person.properties")
@Component
@ConfigurationProperties(prefix="person")
@Validated   //开启字段校验
public class Person implements Serializable {
    @Value("${person.lastName}")
    private String lastName;
    @Value("#{11*3}")       //spel表达式
    private Integer age;
    @Value("true")
    private Boolean boss;
    @Email //开启字段校验后，会验证邮箱
    private String email;
    private Date birth;

    private Map<String,Object> maps;
    private List<Object> lists;

    private Home home;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getBoss() {
        return boss;
    }

    public void setBoss(Boolean boss) {
        this.boss = boss;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Map<String, Object> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, Object> maps) {
        this.maps = maps;
    }

    public List<Object> getLists() {
        return lists;
    }

    public void setLists(List<Object> lists) {
        this.lists = lists;
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "lastName='" + lastName + '\'' +
                ", age=" + age +
                ", boss=" + boss +
                ", email='" + email + '\'' +
                ", birth=" + birth +
                ", maps=" + maps +
                ", lists=" + lists +
                ", home=" + home +
                '}';
    }
}
