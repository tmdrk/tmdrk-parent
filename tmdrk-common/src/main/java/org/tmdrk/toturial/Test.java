package org.tmdrk.toturial;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class Test {
    /** 保险计算规则map. */
    public static Map<BigDecimal, String> insurCalMap = new LinkedHashMap<BigDecimal, String>();
    
    static {
    	insurCalMap.put(new BigDecimal("1000"), "基数*比例");
    	insurCalMap.put(new BigDecimal("1001"), "基数无上限*比例");
    	insurCalMap.put(new BigDecimal("1002"), "固定额*子女数");
    	insurCalMap.put(new BigDecimal("1003"), "固定额");
    	insurCalMap.put(new BigDecimal("1004"), "特殊规则");
    }
	public static void main(String[] args) {
		Test t = new Test();
		User u = new User("huanhuan",21);
		User c = new User("child",1);
		String name = "zhoujie";
		int aa = 123;
		u.setU(c);
		System.out.println(u+"|"+name+"|"+aa);
		t.make(u,name,aa);
//		User uu = u;
//		uu = new User("zhouzhou",23);
//		uu.setName("ssss");
//		String name1 = name;
//		name1 = "huihui";
		System.out.println(u+"|"+name+"|"+aa);
		t.make2(u.u);
		System.out.println(u+"|"+name+"|"+aa);
		
		BigDecimal bd =new BigDecimal("1000.00");
//		BigDecimal bd1 =new BigDecimal("");
		System.out.println(Test.insurCalMap.get(bd.intValue()));
	}
	public void make(User uu,String name,int aa){
//		u = new User("zhouzhou",23);
		User c = new User("erzi",3);
		uu.u=c;
		uu.age = 34;
		uu.setName("时间");
		name = "胡来";
		aa = aa+1;
	}
	public void make2(User uuu){
		uuu.age=65;
	}
}
class User{
	String name;
	int age;
	User u;
	public User(String name,int age){
		this.name = name;
		this.age = age;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

	public User getU() {
		return u;
	}

	public void setU(User u) {
		this.u = u;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + ", u=" + u + "]";
	}

}