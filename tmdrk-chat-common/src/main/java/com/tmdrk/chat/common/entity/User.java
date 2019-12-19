package com.tmdrk.chat.common.entity;

import java.util.List;

/**
 * @ClassName User
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/7/9 19:59
 * @Version 1.0
 **/
public class User {

    private Integer id;
    private String name;
    private List<User> friends;
    private String img;
    private List<Message> messages;

    public User(){

    }

    public User(Integer id,String name,List friends,String img,List messages){
        this.id = id;
        this.name = name;
        this.friends = friends;
        this.img = img;
        this.messages = messages;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", friends=" + friends +
                ", img='" + img + '\'' +
                ", messages=" + messages +
                '}';
    }
}
