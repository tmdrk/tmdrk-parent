package org.tmdrk.toturial.common.util;

import cn.hutool.core.annotation.Alias;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReflectUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.*;

public class BeanUtil<T> {
	public static void main(String[] args) {
		beanToBean();
		User user = new User(2,5,"上海市杨浦区",new Date());
		BeanUtil beanUtil = new BeanUtil();
		List<Detail> list = beanUtil.getDetails(user);
		System.out.println(list);

		User user1 = getUser(list,User.class);
		System.out.println(user1);
	}

	private static <T> T getUser(List<Detail> list,Class<T> clazz){
		T target = ReflectUtil.newInstance(clazz, new Object[0]);
		Map<String, Field> fieldMap = ReflectUtil.getFieldMap(User.class);
		list.stream().forEach(d->{
			Field field = fieldMap.get(d.getAttrName());
			ReflectUtil.setAccessible(field);
			try {
				if(field.getType() == Integer.class){
					field.set(target,Integer.parseInt(d.getAttrValue()));
				}else if(field.getType() ==  String.class){
					field.set(target,d.getAttrValue());
				}else if(field.getType() == Date.class){
					field.set(target, DateUtil.parse(d.getAttrValue(), DatePattern.NORM_DATETIME_MS_PATTERN));
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		});
		return target;
	}

	private List<Detail> getDetails(T user) {
		Field[] fieldsDirectly = ReflectUtil.getFieldsDirectly(user.getClass(), false);
		List<Detail> list = new ArrayList<>();
		Arrays.stream(fieldsDirectly).forEach(f->{
			ReflectUtil.setAccessible(f);
			try {
				if(f.getType() == Date.class){
					list.add(new Detail(null,f.getName(), DateUtil.format((Date) f.get(user), DatePattern.NORM_DATETIME_MS_PATTERN)));
				}else{
					list.add(new Detail(null,f.getName(),f.get(user).toString()));
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		});
		return list;
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

@Data
@AllArgsConstructor
@NoArgsConstructor
class User extends  People{
	private Integer num;
	private Integer limitNum;
	private String addr;
	private Date startTime;
}

@Data
@AllArgsConstructor
class Detail{
	private Integer id;
	private String attrName;
	private String attrValue;
}