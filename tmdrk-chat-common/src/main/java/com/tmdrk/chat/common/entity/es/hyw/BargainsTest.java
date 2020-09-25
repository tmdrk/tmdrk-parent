package com.tmdrk.chat.common.entity.es.hyw;

import com.tmdrk.chat.common.entity.es.Default;
import com.tmdrk.chat.common.entity.es.Properties;
import com.tmdrk.chat.common.entity.es.TestProduct;
import com.tmdrk.chat.common.entity.es.fieldAnnotation.*;
import com.tmdrk.chat.common.utils.ElasticsearchUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * 砍价记录表(Bargains)实体类
 *
 * @author zhoujie
 * @since 2020-09-14 17:55:57
 */
@Data
@Accessors(chain = true)
public class BargainsTest {
    public static void main(String[] args) throws Exception {
        Field[] fields = BargainsTest.class.getDeclaredFields();
        String methodJson = TestProduct.dealWithProperties(fields);
        System.out.println(methodJson);
    }

    /**
     * id
     */
    @Properties(type = @Type(value="long"))
    private Long id;

    /**
     * 关联活动id
     */
    @Properties(type = @Type(value="long"))
    private Long activityId;

    /**
     * 商品id
     */
    @Properties(type = @Type(value="long"))
    private Long itemId;

    /**
     * 用户ID
     */
    @Properties(type = @Type(value="text"))
    private String customerId;

    /**
     * 客户名称
     */
    @Properties(type = @Type(value="text"),
            fields = @Fields(value= Default.class,use=true))
    private String customerName;

    /**
     * 用户头像
     */
    @Properties
    private String headImg;

    /**
     * 用户手机号
     */
    @Properties
    private String phone;

    /**
     * 砍价状态 0:砍价中、1:待领取、2待支付、3:已领取、4:砍价失败
     */
    @Properties(type = @Type(value="integer"))
    private Integer status;

    /**
     * 关联商品skuCode
     */
    @Properties
    private String skuCode;

    /**
     * 商品名称
     */
    @Properties(type = @Type(value="text"),
            fields = @Fields(value=Default.class,use=true))
    private String skuName;

    /**
     * 商品主图片链接
     */
    @Properties
    private String mainPic;

    /**
     * 砍价限定时间（秒）
     */
    @Properties(type = @Type(value="integer"))
    private Integer limitTime;

    /**
     * 原价
     */
    @Properties(type = @Type(value="integer"))
    private Integer price;

    /**
     * 目标价
     */
    @Properties(type = @Type(value="integer"))
    private Integer bargainPrice;

    /**
     * 已砍价
     */
    @Properties(type = @Type(value="integer"))
    private Integer bargainedPrice;

    /**
     * 收货人
     */
    @Properties(type = @Type(value="text"),
            fields = @Fields(value=Default.class,use=true))
    private String receiverName;

    /**
     * 收货地址
     */
    @Properties(type = @Type(value="text"),
            fields = @Fields(value=Default.class,use=true))
    private String address;

    /**
     * 收货人手机号
     */
    @Properties
    private String mobile;

    /**
     * 省
     */
    @Properties
    private String province;

    /**
     * 市
     */
    @Properties
    private String city;

    /**
     * 区
     */
    @Properties
    private String area;

    /**
     * 创建时间
     */
    @Properties(type = @Type(value="long"))
    private Date createTime;

    /**
     * 数据状态是否删除(0否1是)
     */
    private Boolean del;

}
