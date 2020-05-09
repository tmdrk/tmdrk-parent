package com.tmdrk.jpa.entity;

import javax.persistence.*;


/**
 * @ClassName UserInfo
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/9 0:39
 * @Version 1.0
 **/
@Entity  //实体类标识
@Table(name = "USER_INFO")  // 实体与哪个表名映射
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    private Integer id;
    @Column(name = "user_name",length = 64)
    private String userName;
    @Column(length = 64) //默认列名就是属性名
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
