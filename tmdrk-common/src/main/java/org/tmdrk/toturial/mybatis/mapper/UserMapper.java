package org.tmdrk.toturial.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.tmdrk.toturial.mybatis.entity.AceUser;

/**
 * @ClassName UserMapper
 * @Description 测试namespace是否可以对应多个mapper接口
 * @Author zhoujie
 * @Date 2020/6/11 13:27
 * @Version 1.0
 **/
public interface UserMapper {
    @Select("SELECT * FROM ACE_USER WHERE id = #{id} and user_name = #{userName}")
    AceUser selectByIdAndName(@Param("id")Integer id, @Param("userName")String userName);
}
