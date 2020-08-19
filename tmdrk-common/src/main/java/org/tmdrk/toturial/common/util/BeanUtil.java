package org.tmdrk.toturial.common.util;

import cn.hutool.core.annotation.Alias;
import lombok.AllArgsConstructor;
import lombok.Data;

public class BeanUtil {
	public static void main(String[] args) {
		beanToBean();
	}
	public static  void beanToBean(){
		Person person = new Person(1,"zhoujie","北京市朝阳区");
		People people = new People();
		cn.hutool.core.bean.BeanUtil.copyProperties(person,people);
		System.out.println(people);
	}
}

@Data
@AllArgsConstructor
class Person{
	private int id;
	@Alias("name")
	private String realName;
	@Alias("address")
	private String addr;
}

@Data
class People{
	private int id;
	private String name;
	private String address;
}