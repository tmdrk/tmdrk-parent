package org.tmdrk.toturial.mybatis.custommybatis;

import com.google.common.collect.Maps;
import org.apache.ibatis.annotations.Select;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Map;

/**
 * @ClassName CustomMybatis
 * @Description 模拟mybatis动态解析sql过程
 *
 * 保留参数名
 *  如果不开启，method.getParameters()获取到的参数名会是arg0，arg1...
 * 开启方法
 * 1） javac开启方法
 * javac -parameters CustomMybatis.java
 * 2） Idea中开启的方法
 *  File->Settings->Build,Execution,Deployment->Java Compiler下的Additional command line parameters选项中添加-parameters。
 * 3） Maven中开启的办法
 * <plugin>
 *     <groupId>org.apache.maven.plugins</groupId>
 *     <artifactId>maven-compiler-plugin</artifactId>
 *     <version>3.3</version>
 *     <configuration>
 *         <source>1.8</source>
 *         <target>1.8</target>
 *         <compilerArgs>
 *             <arg>-parameters</arg>
 *         </compilerArgs>
 *     </configuration>
 * </plugin>
 *
 * @Author zhoujie
 * @Date 2020/6/10 3:40
 * @Version 1.0
 **/
public class CustomMybatis {
    public static void main(String[] args) {
        UserMapper userMapper = (UserMapper)Proxy.newProxyInstance(UserMapper.class.getClassLoader(), new Class[]{UserMapper.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Select annotation = method.getAnnotation(Select.class);
                Map<String, Object> nameArgMap = buildMethodArg(method, args);
                if(annotation!=null){
                    String[] value = annotation.value();
                    String parseSql = parseSql(value[0], nameArgMap);
                    System.out.println(parseSql);
                }
                return null;
            }
        });
        userMapper.selectUser(1,"jack");
    }

    public static String parseSql(String sql,Map<String, Object> nameArgMap){
        StringBuilder parseSql = new StringBuilder();
        for(int i=0;i<sql.length();i++){
            char c = sql.charAt(i);
            if(c == '#'){
                int nextIndex = i+1;
                char nextChar = sql.charAt(nextIndex);
                if(nextChar!='{'){
                    throw new RuntimeException(String.format("这里应该为#{\nsql:%s\nindex:%d",parseSql.toString(),nextIndex));
                }
                StringBuilder argSB = new StringBuilder();
                i = parseSqlArg(argSB,sql,nextIndex);
                String argName = argSB.toString();
                Object argValue = nameArgMap.get(argName);
                parseSql.append(argValue);
                continue;
            }
            parseSql.append(c);
        }
        return parseSql.toString();
    }

    private static int parseSqlArg(StringBuilder argSB, String sql, int nextIndex) {
        nextIndex++;
        for(;nextIndex<sql.length();nextIndex++){
            char c = sql.charAt(nextIndex);
            if(c != '}'){
                argSB.append(c);
                continue;
            }
            if(c == '}'){
                return nextIndex;
            }
        }
        throw new RuntimeException(String.format("缺少右括号\nindex:%d",nextIndex));
    }

    public static Map<String,Object> buildMethodArg(Method method, Object[] args){
        Map<String, Object> nameArgMap = Maps.newHashMap();
        Parameter[] parameters = method.getParameters();
        int index[] = {0};
        Arrays.asList(parameters).forEach(parameter -> {
            String name = parameter.getName();
            nameArgMap.put(name,args[index[0]]);
            System.out.println(name);
            index[0]++;
            //获取到该方法的参数们
        });
        return nameArgMap;
    }
}

class User{

}

interface UserMapper{
    @Select("SELECT * FROM user WHERE id = #{id} and name = #{name}")
    User selectUser(int id,String name);
}
