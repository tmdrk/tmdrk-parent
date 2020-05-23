package com.tmdrk.mycat.entity;

import java.math.BigDecimal;

/**
 * @ClassName Order
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/19 17:58
 * @Version 1.0
 **/
public class Order {
    private Long orderId;
    private Integer userId;
    private Byte payMode;
    private BigDecimal amount;
    private Integer orderTime;

    public Order() {
    }

    public Order(Long orderId, Integer userId, Byte payMode, BigDecimal amount, Integer orderTime) {
        this.orderId = orderId;
        this.userId = userId;
        this.payMode = payMode;
        this.amount = amount;
        this.orderTime = orderTime;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Byte getPayMode() {
        return payMode;
    }

    public void setPayMode(Byte payMode) {
        this.payMode = payMode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Integer orderTime) {
        this.orderTime = orderTime;
    }
}
