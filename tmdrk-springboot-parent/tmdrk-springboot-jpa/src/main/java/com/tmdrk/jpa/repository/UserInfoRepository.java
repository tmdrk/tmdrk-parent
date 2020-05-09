package com.tmdrk.jpa.repository;

import com.tmdrk.jpa.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName UserInfoRepository
 * @Description
 * @Author zhoujie
 * @Date 2020/5/9 0:52
 * @Version 1.0
 **/
public interface UserInfoRepository extends JpaRepository<UserInfo,Integer> { //第一个泛型实体类，第二个是主键类型
}
