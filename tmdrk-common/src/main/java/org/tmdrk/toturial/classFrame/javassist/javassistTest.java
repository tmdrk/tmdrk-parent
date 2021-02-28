package org.tmdrk.toturial.classFrame.javassist;


import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import javassist.*;
import javassist.util.HotSwapper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;


/**
 * @ClassName javassistTest
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/7/2 16:06
 * @Version 1.0
 **/
public class javassistTest {
    public static void main(String[] args) throws Exception {
//        createClass();
//        extendClass();
//        dynamicInsert();
        dynamicInsert();
//        Hello.sayHello();
        doHotSwapper();
    }


    /**
     * @Author zhoujie
     * @Description //创建类
     * @Date 16:33 2019/7/2
     * @Param []
     * @return void
     **/
    public static void createClass() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        //定义类
        CtClass stuClass = pool.makeClass("org.tmdrk.toturial.classFrame.javassist.Student");
        //加载类
//      CtClass cc =  pool.get(classname);
        //id属性
        CtField idField = new CtField(CtClass.longType, "id", stuClass);
        stuClass.addField(idField);

        //name属性
        CtField nameField = new CtField(pool.get("java.java.lang.String"), "name", stuClass);
        stuClass.addField(nameField);

        //age属性
        CtField ageField = new CtField(CtClass.intType, "age", stuClass);
        stuClass.addField(ageField);

        CtMethod getMethod = CtNewMethod.make("public int getAge() { return this.age;}", stuClass);
        CtMethod setMethod = CtNewMethod.make("public void setAge(int age) { this.age = age;}", stuClass);

        stuClass.addMethod(getMethod);
        stuClass.addMethod(setMethod);

        stuClass.writeFile("F:\\javassist");

        Class<?> clazz = stuClass.toClass();
        System.out.println("class:"+clazz.getName());

        System.out.println("------------属性列表------------");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getType()+"\t"+field.getName());
        }

        System.out.println("------------方法列表------------");
        //方法
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method: methods){
            System.out.println(method.getReturnType()+"\t"+method.getName()+"\t"+ Arrays.toString(method.getParameterTypes()));
        }
    }

    /**
     * @Author zhoujie
     * @Description //继承父类
     * @Date 16:33 2019/7/2
     * @Param []
     * @return void
     **/
    public static void extendClass() throws Exception {
        ClassPool pool = ClassPool.getDefault();

        //定义类
        CtClass stuClass = pool.makeClass("org.tmdrk.toturial.classFrame.javassist.Student");

        //设置父类
        stuClass.setSuperclass(pool.get("org.tmdrk.toturial.classFrame.javassist.Hello"));

        //hobbies属性
        CtField ageField = new CtField(pool.getCtClass("java.util.List"), "hobbies", stuClass);
        stuClass.addField(ageField);

        stuClass.writeFile("F:\\javassist");

        Class<?> clazz = stuClass.toClass();
        System.out.println("class:"+clazz.getName());

        System.out.println("------------属性列表------------");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getType()+"\t"+field.getName());
        }

        System.out.println("------------方法列表------------");
        //方法
        Method[] methods = clazz.getMethods();
        for (Method method: methods){
            System.out.println(method.getReturnType()+"\t"+method.getName()+"\t"+Arrays.toString(method.getParameterTypes()));
        }
    }

    /**
     * @Author zhoujie
     * @Description //动态注入
     * @Date 16:40 2019/7/2
     * @Param []
     * @return void
     **/
    public static void dynamicInsert() throws Exception {
//        Hello hello = new Hello();
//        Hello.sayHello();
//        hello.sayGoodbye();

        ClassPool pool = ClassPool.getDefault();

        // 获取类
        CtClass ctClass = pool.get("org.tmdrk.toturial.classFrame.javassist.Hello");

        // 需要修改的方法名称
        String mname = "sayHello";
        CtMethod mold = ctClass.getDeclaredMethod(mname);
        // 修改原有的方法名称
        String nname = mname + "$World";
        mold.setName(nname);

        //创建新的方法，复制原来的方法
        CtMethod mnew = CtNewMethod.copy(mold, mname, ctClass, null);
        // 主要的注入代码
        StringBuffer body = new StringBuffer();
        body.append("{\nlong start = System.currentTimeMillis();\n");
        // 调用原有代码，类似于method();($$)表示所有的参数
        body.append(nname + "($$);\n");
        body.append("System.out.println(\"Call to method " + mname
                + " took \" +\n (System.currentTimeMillis()-start) + " + "\" ms.\");\n");
        body.append("}");
        // 替换新方法
        mnew.setBody(body.toString());
        // 增加新方法
        ctClass.addMethod(mnew);

//        ctClass.writeFile("F:\\javassist");

        Hello hello =(Hello)ctClass.toClass().newInstance();

        hello.sayHello();
    }

    /**
     * @Author zhoujie
     * @Description //热替换 注意热替换只适合于修改，对于方法的新增和删除并不支持
     * jvm启动参数：-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000
     * @Date 12:26 2019/7/3
     * @Param []
     * @return void
     **/
    public static void doHotSwapper(){
        Standard standard = new Standard();
        standard.doSomething();
        ClassPool pool = ClassPool.getDefault();
        try {
            CtClass clazz = pool.get("org.tmdrk.toturial.classFrame.javassist.Standard");
            CtMethod cm = clazz.getDeclaredMethod("doSomething");
            cm.insertAt(1,"{System.out.println(\"hello HotSwapper.\");}");  // clazz完全可以是全新的，这里只是为了测试方便而已
            HotSwapper swap = new HotSwapper(8000);
            swap.reload("org.tmdrk.toturial.classFrame.javassist.Standard", clazz.toBytecode());
            standard.doSomething();
        } catch (CannotCompileException | IOException | IllegalConnectorArgumentsException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
class Standard {

    /**
     *
     */
    public void doSomething() {
        System.out.println("doSomething");
    }

}