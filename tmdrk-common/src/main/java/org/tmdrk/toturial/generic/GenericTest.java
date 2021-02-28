package org.tmdrk.toturial.generic;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.tmdrk.toturial.entity.UserPO;
import org.tmdrk.toturial.entity.UserVO;

/**
 * 参数化类型，实际类型参数
 */
public class GenericTest {
	Apple<UserPO,UserVO> apple;
	public static void main(String[] args) throws NoSuchFieldException, SecurityException {
//		Apple<String> fru = new Apple<String>();
		Field field = GenericTest.class.getDeclaredField("apple");
		Type t = field.getGenericType();
		ParameterizedType pt = (ParameterizedType) t;
		Type[] ts = pt.getActualTypeArguments();
		for(int i = 0; i < ts.length; ++ i){
            System.out.println(i + " apple中的泛型为：" + ts[i]);  
            Class<?> c = (Class<?>) ts[i];//如果需要使用这个类型 进行强转即可
            System.out.println(i + " 强转后类型为：" + c);  
        }  
	}
}
