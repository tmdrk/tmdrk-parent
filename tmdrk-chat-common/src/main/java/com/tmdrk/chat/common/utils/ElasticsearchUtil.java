package com.tmdrk.chat.common.utils;

import com.tmdrk.chat.common.entity.es.Properties;
import com.tmdrk.chat.common.entity.es.PropertiesNew;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @ClassName ElasticsearchUtil
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/19 13:32
 * @Version 1.0
 **/
public class ElasticsearchUtil {

    public static String dealWithMappings(Field[] declaredFields) throws Exception {
        String json = "{\"mappings\":{"+"\"_doc\":";
        json += dealWithProperties(declaredFields);
        json += "}"+"}";
        return json;
    }

    public static String dealWithProperties(Field[] declaredFields) throws Exception {
        String json = "{\"properties\":{";
        json += dealWithFields(declaredFields);
        json += "}}";
        return json;
    }

    /**
     * @Author zhoujie
     * @Description //处理所有声明Properties注解的字段
     * @Date 13:04 2019/12/19
     * @Param [declaredFields]
     * @return java.java.lang.String
     **/
    public static String dealWithFields(Field[] declaredFields) throws Exception {
        StringBuilder fieldsJson = new StringBuilder();
        for(Field field:declaredFields){
            fieldsJson.append("\""+field.getName()+"\":{");
            if (field.isAnnotationPresent(PropertiesNew.class)) {
                PropertiesNew properties = field.getAnnotation(PropertiesNew.class);
                Method[] declaredMethods = properties.getClass().getDeclaredMethods();
                String pro = dealWithProperties(properties, declaredMethods);
                fieldsJson.append(pro);
            }
            fieldsJson.append("},");
        }
        if(fieldsJson.length()>0){
            fieldsJson.delete(fieldsJson.length() - 1,fieldsJson.length());
        }
        return fieldsJson.toString();
    }

    /**
     * @Author zhoujie
     * @Description //处理单个声明了注解属性值
     * @Date 13:04 2019/12/19
     * @Param [properties, declaredMethods]
     * @return java.java.lang.String
     **/
    public static String dealWithProperties(Object properties,Method[] declaredMethods) throws Exception {
        StringBuilder fieldJson = new StringBuilder();
        //循环处理属性值所声明的注解内容
        for (Method method:declaredMethods){
            String methodName = method.getName();
            if(!(methodName.equals("equals")||methodName.equals("hashCode")||
                    methodName.equals("toString")||methodName.equals("annotationType"))){
                Object obj = method.invoke(properties);
                Method[] methods = obj.getClass().getDeclaredMethods();
                boolean use = false; //注解是否生效
                Object value = null; //注解vlaue
                Class cla = null; //注解vlaue明确为class
                //循环获取注解内use，和value值
                for (Method md:methods){
                    if(md.getName().equals("use")){
                        use = (Boolean) md.invoke(obj);
                        if(!use){
                            break;//如果注解不生效则直接中止本字段其他操作；
                        }
                    } else if(md.getName().equals("value")){
                        if(methodName.equals("fields")){
                            cla = (Class) md.invoke(obj);
                        }else{
                            value = md.invoke(obj);
                        }
                    }else{
                    }
                }
                //单独处理fields类型，因为Fields注解value返回值为Class
                if(methodName.equals("fields")){
                    if(use){
                        fieldJson.append("\""+methodName+"\":{");
                        Object object = cla.newInstance();
                        Field[] declaredFields1 = object.getClass().getDeclaredFields();
                        //递归dealWithProperties
                        fieldJson.append(dealWithFields(declaredFields1));
                        fieldJson.append("},");
                    }
                }else{
                    if(use){
                        fieldJson.append("\""+methodName +"\":\"" + value +"\",");
                    }
                }
            }
        }
        if(fieldJson.length()>0){
            fieldJson.delete(fieldJson.length() - 1,fieldJson.length());
        }
        return fieldJson.toString();
    }
}
