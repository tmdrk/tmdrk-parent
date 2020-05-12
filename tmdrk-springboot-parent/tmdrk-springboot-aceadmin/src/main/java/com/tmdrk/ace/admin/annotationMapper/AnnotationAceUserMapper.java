package com.tmdrk.ace.admin.annotationMapper;

import com.tmdrk.ace.admin.entity.AceUser;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.Cacheable;

/**
 * @ClassName AnnotationAceUserMapper
 * @Description
 * 	@EnableCaching 开启缓存
 * 		1 @Cacheable 	标记方法可以缓存
 * 	        //cacheNames 指定缓存组件的名字（cacheManage）
 *          //key缓存数据使用的key，默认方法参数的值 SpEl表达式（cache） #id #a0 #p0 #root.args[0] 表示方法第一个参数
 *          //keyGenerator：key生成器，与key只能二选一
 *          //cacheManager:指定缓存管理器(map，redis)，或者使用cacheResolver，也是二选一
 *          //condition:指定符合条件才缓存
 *          //unless 与condition相反
 *          //sysn:是否异步
 * 		2 @CacheEvict 	标记方法清空缓存
 * 		3 @CachePut 	标记方法更新缓存
 * @Author zhoujie
 * @Date 2020/5/8 18:01
 * @Version 1.0
 **/
public interface AnnotationAceUserMapper {

    @Cacheable(cacheNames = "aceUser",key = "#id",condition ="#id>0",unless = "#result == null")
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
