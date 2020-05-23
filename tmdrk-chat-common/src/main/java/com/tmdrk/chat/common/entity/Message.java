package com.tmdrk.chat.common.entity;

/**
 * @ClassName Message
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/7/10 14:16
 * @Version 1.0
 **/
public class Message {
    private Integer from;
    private Integer to;
    private String message;
    private String type;

    public Message(Integer from, Integer to, String message, String type) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.type = type;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
