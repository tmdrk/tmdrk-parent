package com.tmdrk.ace.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author zhoujie
 * @Description
 * 	@EnableCaching 开启缓存
 * 		1 @Cacheable 	标记方法可以缓存
 * 		2 @CacheEvict 	标记方法清空缓存
 * 		3 @CachePut 	标记方法更新缓存
 * @Date 1:13 2020/5/10
 * @Param
 * @return
 **/
@MapperScan(value={"com.tmdrk.ace.admin.mapper","com.tmdrk.ace.admin.annotationMapper"})
@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
public class TmdrkSpringbootAceadminApplication {

	public static void main(String[] args) {
		SpringApplication.run(TmdrkSpringbootAceadminApplication.class, args);
	}

}
