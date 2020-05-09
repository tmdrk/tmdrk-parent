package com.tmdrk.ace.admin.annotationMapper;

import com.tmdrk.ace.admin.entity.AceUser;
import org.apache.ibatis.annotations.*;

/**
 * @ClassName AnnotationAceUserMapper
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/8 18:01
 * @Version 1.0
 **/
public interface AnnotationAceUserMapper {
    @Select("select * from ACE_USER where id=#{id}")
    AceUser selectById(Integer id);

    @Delete("delete from ACE_USER where id=#{id}")
    int deleteById(Integer id);

    @Options(useGeneratedKeys = true,keyProperty = "id")//返回主键
    @Insert("insert into ACE_USER(user_name,password,sex,age,phone_number,idcard) values(#{userName},#{password},#{sex},#{age},#{phoneNumber},#{idcard})")
    int insert(AceUser aceUser);

    @Update("update ACE_USER set user_name=#{userName},password=#{password},sex=#{sex},age=#{age},phone_number=#{phoneNumber},idcard=#{idcard} where id=#{id}")
    int update(AceUser aceUser);
}
