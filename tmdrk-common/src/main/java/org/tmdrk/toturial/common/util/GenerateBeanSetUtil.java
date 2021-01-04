package org.tmdrk.toturial.common.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * GenerateBeanSetUtil
 *
 * @author Jie.Zhou
 * @date 2020/12/28 13:31
 */
public class GenerateBeanSetUtil {
    public static void main(String[] args) {
//        generateSetCode(User.class,"dto");

        generateSetCode(JDOrderDetailDTO.class,"detailDTO","details");
    }
    public static void generateSetCode(Class clazz,String dto,String ori){
        Field[] fields = clazz.getDeclaredFields();
        String simpleName = dto==null?clazz.getSimpleName():dto;
        StringBuilder sb = new StringBuilder();
        for (Field field:fields){
            String name = field.getName();
            String pre = name.substring(0,1).toUpperCase();
            String sub = name.substring(1);
            sb.append(simpleName).append(".").append("set").append(pre).append(sub)
                    .append("(").append(ori==null?"xxxxx":ori).append(")").append(";").append("\n");
        }
        System.out.println(sb.toString());
    }
}

class JDOrderDTO implements Serializable {
    /**
     * 渠道订单
     */
    private String orderId;

    /**
     * 下单时间
     */
    private Date orderTime;

    /**
     * 收货人姓名
     */
    private String userNanme;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 渠道Code
     */
    private String platform;
    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区域
     */
    private String area;

    /**
     * 地址
     */
    private String address;

    /**
     * 邮编
     */
    private String zipCode;

    /**
     * 备注
     */
    private String remark;


    /**
     * 身份证号
     */
    private String userCardId;

    /**
     * 发票抬头
     */
    private String fpName;

    /**
     * 发票税号
     */
    private String fpTaxid;

    /**
     * 发票电话
     */
    private String fpPhone;

    /**
     * 发票类型<0:无需发票,1:电子发票,2:纸质发票>
     */
    private Integer fpType;

    /**
     * 分期数
     */
    private Integer stage;

    /**
     * 退换货时，上个订单ID
     */
    private Long oldDetailId;
    /**
     * 换货范围：0：整单，1：子单
     */
    private Integer range;

    /**
     * 渠道商品总价
     */
    private Double total;

    /**
     * 项目编号
     */
    private String projectNo;

    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 是否创意定制标识
     */
    private Boolean originalitySign;
    /**
     * 标记主单是否同时出现 实物+权益
     */
    private Boolean flag;

    private List<JDOrderDetailDTO> details;
}

class JDOrderDetailDTO implements Serializable {
    /**
     * 渠道子订单号
     */
    private String subOrderId;
    /**
     * 渠道商品code
     */
    private String itemCode;
    /**
     * 渠道商品名称
     */
    private String itemName;
    /**
     * 渠道商品属性
     */
    private String itemPpecs;

    /**
     * 渠道商品数量
     */
    private Integer count;

    /**
     * 渠道商品单价
     */
    private Double price;

    /**
     * 渠道商品总价
     */
    private Double total;

    /**
     * 原价
     */
    private Double normalPrice;

    /**
     * 订单详情备注，某些渠道需要使用
     */
    private String remark;
    /**
     * 订单是否暂停出库
     */
    private Boolean isSuspend;
    /**
     * 积分抵扣
     */
    private Integer integral;
    /**
     * 佣金
     */
    private BigDecimal commission;
    /**
     * 银行优惠
     */
    private BigDecimal  bankDiscounts;
    /**
     * 公司优惠
     */
    private BigDecimal  companyDiscounts;
}