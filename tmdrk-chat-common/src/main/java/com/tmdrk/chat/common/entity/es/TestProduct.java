package com.tmdrk.chat.common.entity.es;

import com.tmdrk.chat.common.entity.es.fieldAnnotation.Analyzer;
import com.tmdrk.chat.common.entity.es.fieldAnnotation.Fields;
import com.tmdrk.chat.common.entity.es.fieldAnnotation.IndexOptions;
import com.tmdrk.chat.common.entity.es.fieldAnnotation.Type;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @ClassName TestProduct
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/18 17:05
 * @Version 1.0
 **/
@EsIndex(settings=@Settings(numberOfShards=2,numberOfReplicas = 1),needMapping = true,aliases = {"test_p_1","test_p_2"})
public class TestProduct {
    public static void main(String[] args) throws Exception {
        TestProduct testProduct = new TestProduct();
        Class clazz = testProduct.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (clazz.isAnnotationPresent(EsIndex.class)) {
            EsIndex indexAnno = (EsIndex)clazz.getAnnotation(EsIndex.class);
            Settings settings = indexAnno.settings();
            int numberOfShards = settings.numberOfShards();
            int numberOfReplicas = settings.numberOfReplicas();
            System.out.println(indexAnno);
            System.out.println(settings);
            System.out.println(numberOfShards);
            System.out.println(numberOfReplicas);

            String[] aliases = indexAnno.aliases();
            if(aliases.length>0){
                System.out.println(aliases[0]);
            }

            if(!indexAnno.needMapping()){
                System.out.println("mapping no need");
                return;
            }
            String methodJson = "{\"properties\":{";
            methodJson += dealWithProperties(fields);
            methodJson += "}}";
            System.out.println(methodJson);
        }
    }

    /**
     * @Author zhoujie
     * @Description //处理所有声明Properties注解的字段
     * @Date 13:04 2019/12/19
     * @Param [declaredFields]
     * @return java.lang.String
     **/
    public static String dealWithProperties(Field[] declaredFields) throws Exception {
        StringBuilder propertiesJson = new StringBuilder();
        for(Field field:declaredFields){
            propertiesJson.append("\""+field.getName()+"\":{");
            if (field.isAnnotationPresent(Properties.class)) {
                Properties properties = field.getAnnotation(Properties.class);
                Method[] declaredMethods = properties.getClass().getDeclaredMethods();
                String pro = dealWith(properties, declaredMethods);
                propertiesJson.append(pro);
            }
            propertiesJson.append("},");
        }
        return propertiesJson.substring(0, propertiesJson.length() - 1);
    }

    /**
     * @Author zhoujie
     * @Description //单独处理声明了注解属性值
     * @Date 13:04 2019/12/19
     * @Param [properties, declaredMethods]
     * @return java.lang.String
     **/
    public static String dealWith(Object properties,Method[] declaredMethods) throws Exception {
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
                        fieldJson.append(dealWithProperties(declaredFields1));
                        fieldJson.append("},");
                    }
                }else{
                    if(use){
                        fieldJson.append("\""+methodName +"\":\"" + value +"\",");
                    }
                }
            }
        }
        return fieldJson.substring(0,fieldJson.length()-1);
    }
    /** 商品id **/
    @Properties(type = @Type(value="long"))
    public long id;
    /** 商品简称 **/
    @Properties()
    public String procuctShortName;
    /** 商品全称 **/
    @Properties(type = @Type(value="text"),analyzer = @Analyzer(value="ik_max_word",use=true),fields = @Fields(value=FieldsProperties.class,use=true),
            index_options = @IndexOptions(value=IndexOption.positions))
//    @Properties(type = @Type(value="text"),analyzer = @Analyzer(value="ik_max_word",use=true))
    public String procuctFullName;
    /** 商品sku **/
    @Properties()
    public String procuctSku;
    /** 商品价格 **/
    @Properties(type = @Type(value="double"))
    public double procuctPrice;
    /** 商品库存状态 **/
    @Properties(type = @Type(value="long"))
    public long stockState;
    /** 商品库存状态名称 **/
    @Properties()
    public String stockStateName;
    /** 商品重量 **/
    @Properties()
    public String procuctWeight;
    /** 商品图片地址 **/
    @Properties()
    public String procuctImgPath;
    /** 商品上架状态 **/
    @Properties(type = @Type(value="boolean"))
    public boolean onShelveStatus;
    /** 商品描述 **/
    @Properties(type = @Type(value="text"),analyzer = @Analyzer(value="ik_smart",use=true),
            index_options = @IndexOptions(value=IndexOption.positions))
    public String procuctDescription;
    /** 商品创建时间 **/
    @Properties(type = @Type(value="date"))
    public Date createTime;
    /** 商品更新时间 **/
    @Properties(type = @Type(value="date"))
    public Date updateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProcuctShortName() {
        return procuctShortName;
    }

    public void setProcuctShortName(String procuctShortName) {
        this.procuctShortName = procuctShortName;
    }

    public String getProcuctFullName() {
        return procuctFullName;
    }

    public void setProcuctFullName(String procuctFullName) {
        this.procuctFullName = procuctFullName;
    }

    public String getProcuctSku() {
        return procuctSku;
    }

    public void setProcuctSku(String procuctSku) {
        this.procuctSku = procuctSku;
    }

    public double getProcuctPrice() {
        return procuctPrice;
    }

    public void setProcuctPrice(double procuctPrice) {
        this.procuctPrice = procuctPrice;
    }

    public long getStockState() {
        return stockState;
    }

    public void setStockState(long stockState) {
        this.stockState = stockState;
    }

    public String getStockStateName() {
        return stockStateName;
    }

    public void setStockStateName(String stockStateName) {
        this.stockStateName = stockStateName;
    }

    public String getProcuctWeight() {
        return procuctWeight;
    }

    public void setProcuctWeight(String procuctWeight) {
        this.procuctWeight = procuctWeight;
    }

    public String getProcuctImgPath() {
        return procuctImgPath;
    }

    public void setProcuctImgPath(String procuctImgPath) {
        this.procuctImgPath = procuctImgPath;
    }

    public boolean isOnShelveStatus() {
        return onShelveStatus;
    }

    public void setOnShelveStatus(boolean onShelveStatus) {
        this.onShelveStatus = onShelveStatus;
    }

    public String getProcuctDescription() {
        return procuctDescription;
    }

    public void setProcuctDescription(String procuctDescription) {
        this.procuctDescription = procuctDescription;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "TestProduct{" +
                "id=" + id +
                ", procuctShortName='" + procuctShortName + '\'' +
                ", procuctFullName='" + procuctFullName + '\'' +
                ", procuctSku='" + procuctSku + '\'' +
                ", procuctPrice=" + procuctPrice +
                ", stockState=" + stockState +
                ", stockStateName='" + stockStateName + '\'' +
                ", procuctWeight='" + procuctWeight + '\'' +
                ", procuctImgPath='" + procuctImgPath + '\'' +
                ", onShelveStatus=" + onShelveStatus +
                ", procuctDescription='" + procuctDescription + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
