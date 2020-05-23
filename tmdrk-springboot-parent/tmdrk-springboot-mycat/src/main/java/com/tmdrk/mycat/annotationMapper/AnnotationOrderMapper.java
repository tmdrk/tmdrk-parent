package com.tmdrk.mycat.annotationMapper;

import com.tmdrk.mycat.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

/**
 * @ClassName AnnotationOrderMapper
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/19 17:56
 * @Version 1.0
 **/
public interface AnnotationOrderMapper {

    @Options(useGeneratedKeys = true,keyProperty = "id")//返回主键
    @Insert("insert into T_ORDER(order_id,user_id,pay_mode,amount,order_time) values(#{orderId},#{userId},#{payMode},#{amount},#{orderTime})")
    int insert(Order order);
}
