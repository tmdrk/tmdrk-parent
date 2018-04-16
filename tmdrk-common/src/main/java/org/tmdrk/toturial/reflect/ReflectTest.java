package org.tmdrk.toturial.reflect;

import java.lang.reflect.Constructor;

import org.tmdrk.toturial.service.UserService;

public class ReflectTest {
	@SuppressWarnings({ "unused", "rawtypes" })
	public static void main(String[] args) {
		try {
			Class clazz = Class.forName("com.test.service.impl.UserServiceImpl");
//			UserService userService = (UserService) clazz.newInstance();
			Constructor<?> con = clazz.getDeclaredConstructor((Class[]) null);
			Object[] o = new Object[]{};
			UserService userService = (UserService) con.newInstance(o);
			System.out.println(userService.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
