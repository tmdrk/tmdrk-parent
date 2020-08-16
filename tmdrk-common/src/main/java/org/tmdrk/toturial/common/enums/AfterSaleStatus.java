package org.tmdrk.toturial.common.enums;

import java.util.Collections;

public enum AfterSaleStatus {
    AFTER_SALE("售后"),
    NONE(""),
    APPLY_REFUND("申请退款"),
    APPLY_RETURN("申请退货"),
    APPLY_REFUND_REJECT("申请退款拒绝"),
    APPLY_RETURN_REJECT("申请退货拒绝"),
    APPLY_REFUND_SUCCESS("退款中"),
    APPLY_RETURN_SUCCESS("退货中"),
    CONFIRM_RETURN("退款中"),
    CONFIRM_REFUND("确认退款"),
    REFUND_SUCCESS("退款成功"),
    REFUND_FAILURE("退款失败");

    private final String desc;

    AfterSaleStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public boolean in(AfterSaleStatus... statuses) {
        if (statuses == null || statuses.length == 0) {
            throw new RuntimeException("statuses不能为空");
        }
        for (AfterSaleStatus afterSaleStatus : statuses) {
            if (afterSaleStatus == this) {
                return true;
            }
        }
        return false;
    }

    public boolean notIn(AfterSaleStatus... statuses) {
        return !in(statuses);
    }
}

enum PayType {
    /**
     * 现金
     */
    CASH(1 << 4, "现金"),
    /**
     * 积分
     */
    POINTS(1 << 3, "积分"),
    /**
     * 欢趣豆
     */
    JOY_BEANS(1 << 2, "欢趣豆"),
    /**
     * 立减金
     */
    COUPON(1 << 1, "立减金"),

    /**
     * 欢趣金豆
     */
    GOLD_BEANS(1, "欢趣金豆");

    private final int    value;
    private final String desc;

    PayType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return String.format("%" + values().length + "s", Integer.toBinaryString(value)).replace(' ', '0');
    }

    public String and(PayType... payTypes) {
        int i = this.value;
        for (PayType payType : payTypes) {
            i |= payType.value;
        }
        return toString(i);
    }

    public boolean in(String payTypes) {
        // 支付方式增加, 原有的占位左移
        int l = (int) (Math.log(PayType.CASH.value) / Math.log(2)) + 1;
        if (payTypes.length() < l) {
            payTypes += String.join("", Collections.nCopies(l - payTypes.length(), "0"));
        }
        try {
            int i = Integer.parseInt(payTypes, 2);
            return (this.value & i) == this.value;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String and(String payType) {
        // 支付方式增加, 原有的占位左移
        int l = (int) (Math.log(PayType.CASH.value) / Math.log(2)) + 1;
        if (payType.length() < l) {
            payType += String.join("", Collections.nCopies(l - payType.length(), "0"));
        }
        try {
            int i = Integer.parseInt(payType, 2);
            if (i > 0) {
                i |= this.value;
                return toString(i);
            }
        } catch (NumberFormatException e) {
        }
        return payType;
    }

    public static PayType valueOf(int i) {
        for (PayType payType : values()) {
            if (payType.getValue() == i) {
                return payType;
            }
        }
        return null;
    }

    public String toString(int i) {
        return String.format("%"+values().length+"s", Integer.toBinaryString(i)).replace(' ', '0');
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}

class EnumTest{
    public static void main(String[] args) {
        AfterSaleStatus refundSuccess = AfterSaleStatus.REFUND_SUCCESS;
        String desc = refundSuccess.getDesc();
        System.out.println(desc);  //枚举描述
        System.out.println(refundSuccess.name());  //枚举名称
        System.out.println(refundSuccess.ordinal()); //枚举序号，从0开始计数
        System.out.println(AfterSaleStatus.AFTER_SALE.ordinal());

        PayType payType = PayType.POINTS;

        System.out.println(payType.name());
        System.out.println(payType.getValue());
        System.out.println(payType.getDesc());
        System.out.println(payType.ordinal());
    }
}
