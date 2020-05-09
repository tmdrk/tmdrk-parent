package com.tmdrk.ace.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@MapperScan(value={"com.tmdrk.ace.admin.mapper","com.tmdrk.ace.admin.annotationMapper"})
@SpringBootApplication
public class TmdrkSpringbootAceadminApplication {

	public static void main(String[] args) {
		SpringApplication.run(TmdrkSpringbootAceadminApplication.class, args);
	}

}
