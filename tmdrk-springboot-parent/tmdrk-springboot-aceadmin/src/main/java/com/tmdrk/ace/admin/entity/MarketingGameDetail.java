package com.tmdrk.ace.admin.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * (MarketingGameDetail)实体类
 *
 * @author makejava
 * @since 2020-09-01 21:11:47
 */
public class MarketingGameDetail implements Serializable {
    private static final long serialVersionUID = 387287095935171916L;

    private Long id;

    private Long gameId;

    private String attrName;

    private String attrDesc;

    private String attrValue;

    private Date createTime;

    private Object isDel;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrDesc() {
        return attrDesc;
    }

    public void setAttrDesc(String attrDesc) {
        this.attrDesc = attrDesc;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Object getIsDel() {
        return isDel;
    }

    public void setIsDel(Object isDel) {
        this.isDel = isDel;
    }

    @Override
    public String toString() {
        return "MarketingGameDetail {" +
                "id : " + id + ", " +
                "gameId : " + gameId + ", " +
                "attrName : " + attrName + ", " +
                "attrDesc : " + attrDesc + ", " +
                "attrValue : " + attrValue + ", " +
                "createTime : " + createTime + ", " +
                "isDel : " + isDel + ", " +
                '}';
    }
}