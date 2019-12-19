package com.tmdrk.chat.common.entity;

import java.util.Date;

/**
 * @ClassName MessageInfo
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/7/18 18:17
 * @Version 1.0
 **/
public class MessageInfo {
    private Integer id;
    private Integer from;
    private Integer to;
    private String toName;
    private String content;
    private String type;
    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
