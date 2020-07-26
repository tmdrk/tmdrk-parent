package com.tmdrk.chat.common.entity.es;

import com.tmdrk.chat.common.entity.es.fieldAnnotation.*;
import com.tmdrk.chat.common.utils.ElasticsearchUtil;
import com.tmdrk.chat.common.utils.StringUtil;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * @ClassName TestProduct
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/18 17:05
 * @Version 1.0
 **/
@EsIndex(settings=@Settings(numberOfShards=2,numberOfReplicas = 1,analysis="{\n" +
        "\t\"char_filter\": {\n" +
        "\t\t\"&_to_and\": {\n" +
        "\t\t\t\"type\": \"mapping\",\n" +
        "\t\t\t\"mappings\": [\" & => and \"]\n" +
        "\t\t},\n" +
        "\t\t\"minus_to_underline\": {\n" +
        "          \"type\": \"pattern_replace\",\n" +
        "          \"pattern\": \"(\\\\d+)-(?=\\\\d)\",\n" +
        "          \"replacement\": \"$1_\"\n" +
        "        }\n" +
        "\t},\n" +
        "\t\"filter\": {\n" +
        "\t\t\"my_stopwords\": {  \n" +
        "\t\t\t\"type\": \"stop\",\n" +
        "\t\t\t\"stopwords\": [\"the\",\"a\"]\n" +
        "\t\t}\n" +
        "\t},\n" +
        "\t\"analyzer\": {\n" +
        "\t\t\"my_analyzer\": {\n" +
        "\t\t\t\"type\": \"custom\",\n" +
        "\t\t    \"char_filter\": [\"htmp_strip\",\"&_to_and\",\"minus_to_underline\"],\n" +
        "\t\t\t\"tokenizer\": \"ik_max_word\",\n" +
        "\t\t    \"filter\": [\"lowercase\",\"my_stopwords\"]\n" +
        "\t\t}\n" +
        "\t}\n" +
        "}"),
        needMapping = true,
        aliases = {"tp31","tp32"})
public class TestProduct {
    public static void main(String[] args) throws Exception {
        StringBuilder esIndex = new StringBuilder();
        TestProduct testProduct = new TestProduct();
        Class clazz = testProduct.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (clazz.isAnnotationPresent(EsIndex.class)) {
            EsIndex indexAnno = (EsIndex)clazz.getAnnotation(EsIndex.class);
            Settings settings = indexAnno.settings();
            int numberOfShards = settings.numberOfShards();
            int numberOfReplicas = settings.numberOfReplicas();
            String analysis = settings.analysis();
//            System.color.println(indexAnno);
//            System.color.println(settings);
//            System.color.println(numberOfShards);
//            System.color.println(numberOfReplicas);
//            System.color.println(analysis);

            String[] aliases = indexAnno.aliases();

            esIndex.append("{\"settings\":{\"index\" :{\"number_of_shards\" :").append(numberOfShards);
            esIndex.append(",").append("\"number_of_replicas\" :").append(numberOfReplicas).append("},");
            if(!StringUtil.isEmpty(analysis)){
                esIndex.append("\"analysis\": {").append(analysis).append("}");
            }else{
                esIndex.delete(esIndex.length()-1,esIndex.length());
            }
            esIndex.append("},");

            if(aliases.length>0) {
                esIndex.append("\"aliases\" : {");
                for (String aliase:aliases){
                    esIndex.append("\""+aliase+"\" : {},");
                }
                esIndex.delete(esIndex.length()-1,esIndex.length());
                esIndex.append("},");
            }

            String methodJson = null;
            if(!indexAnno.needMapping()){
                System.out.println("mapping no need");
            }else{
                methodJson = ElasticsearchUtil.dealWithMappings(fields);
                esIndex.append(methodJson);
            }
            if(methodJson==null){
                esIndex.delete(esIndex.length()-1,esIndex.length());
            }
            System.out.println(esIndex.toString());


            analysis="{\n" +
                    "\t\"char_filter\": {\n" +
                    "\t\t\"&_to_and\": {\n" +
                    "\t\t\t\"type\": \"mapping\",\n" +
                    "\t\t\t\"mappings\": [\" & => and \"]\n" +
                    "\t\t},\n" +
                    "\t\t\"minus_to_underline\": {\n" +
                    "          \"type\": \"pattern_replace\",\n" +
                    "          \"pattern\": \"(\\\\d+)-(?=\\\\d)\",\n" +
                    "          \"replacement\": \"$1_\"\n" +
                    "        }\n" +
                    "\t},\n" +
                    "\t\"filter\": {\n" +
                    "\t\t\"my_stopwords\": {  \n" +
                    "\t\t\t\"type\": \"stop\",\n" +
                    "\t\t\t\"stopwords\": [\"the\",\"a\"]\n" +
                    "\t\t}\n" +
                    "\t},\n" +
                    "\t\"analyzer\": {\n" +
                    "\t\t\"my_analyzer\": {\n" +
                    "\t\t\t\"type\": \"custom\",\n" +
                    "\t\t    \"char_filter\": [\"htmp_strip\",\"&_to_and\",\"minus_to_underline\"],\n" +
                    "\t\t\t\"tokenizer\": \"ik_max_word\",\n" +
                    "\t\t    \"filter\": [\"lowercase\",\"my_stopwords\"]\n" +
                    "\t\t}\n" +
                    "\t}\n" +
                    "}";
            System.out.println(analysis);
        }
    }

    /**
     * @Author zhoujie
     * @Description //处理所有声明Properties注解的字段
     * @Date 13:04 2019/12/19
     * @Param [declaredFields]
     * @return java.java.lang.String
     **/
//    public static String dealWithProperties(Field[] declaredFields) throws Exception {
//        StringBuilder propertiesJson = new StringBuilder();
//        for(Field field:declaredFields){
//            propertiesJson.append("\""+field.getName()+"\":{");
//            if (field.isAnnotationPresent(Properties.class)) {
//                Properties properties = field.getAnnotation(Properties.class);
//                Method[] declaredMethods = properties.getClass().getDeclaredMethods();
//                String pro = dealWith(properties, declaredMethods);
//                propertiesJson.append(pro);
//            }
//            propertiesJson.append("},");
//        }
//        if(propertiesJson.length()>0){
//            propertiesJson.delete(propertiesJson.length() - 1,propertiesJson.length());
//        }
//        return propertiesJson.toString();
//    }

    /**
     * @Author zhoujie
     * @Description //单独处理声明了注解属性值
     * @Date 13:04 2019/12/19
     * @Param [properties, declaredMethods]
     * @return java.java.lang.String
     **/
//    public static String dealWith(Object properties,Method[] declaredMethods) throws Exception {
//        StringBuilder fieldJson = new StringBuilder();
//        //循环处理属性值所声明的注解内容
//        for (Method method:declaredMethods){
//            String methodName = method.getName();
//            if(!(methodName.equals("equals")||methodName.equals("hashCode")||
//                    methodName.equals("toString")||methodName.equals("annotationType"))){
//                Object obj = method.invoke(properties);
//                Method[] methods = obj.getClass().getDeclaredMethods();
//                boolean use = false; //注解是否生效
//                Object value = null; //注解vlaue
//                Class cla = null; //注解vlaue明确为class
//                //循环获取注解内use，和value值
//                for (Method md:methods){
//                    if(md.getName().equals("use")){
//                        use = (Boolean) md.invoke(obj);
//                        if(!use){
//                            break;//如果注解不生效则直接中止本字段其他操作；
//                        }
//                    } else if(md.getName().equals("value")){
//                        if(methodName.equals("fields")){
//                            cla = (Class) md.invoke(obj);
//                        }else{
//                            value = md.invoke(obj);
//                        }
//                    }else{
//                    }
//                }
//                //单独处理fields类型，因为Fields注解value返回值为Class
//                if(methodName.equals("fields")){
//                    if(use){
//                        fieldJson.append("\""+methodName+"\":{");
//                        Object object = cla.newInstance();
//                        Field[] declaredFields1 = object.getClass().getDeclaredFields();
//                        //递归dealWithProperties
//                        fieldJson.append(dealWithProperties(declaredFields1));
//                        fieldJson.append("},");
//                    }
//                }else{
//                    if(use){
//                        fieldJson.append("\""+methodName +"\":\"" + value +"\",");
//                    }
//                }
//            }
//        }
//        if(fieldJson.length()>0){
//            fieldJson.delete(fieldJson.length() - 1,fieldJson.length());
//        }
//        return fieldJson.toString();
//    }
    /** 商品id **/
    @Properties(type = @Type(value="long"))
    public long id;
    /** 商品简称 **/
    @Properties()
    public String procuctShortName;
    /** 商品全称 **/
    @Properties(type = @Type(value="text"),
            analyzer = @Analyzer(value="ik_max_word",use=true),
            norms = @Norms(value=true,use=true),
            search_analyzer = @SearchAnalyzer(value="ik_smart",use=true),
            fields = @Fields(value=Default.class,use=true),
            index_options = @IndexOptions(value=IndexOption.positions))
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
    @Properties(type = @Type(value="text"),
            analyzer = @Analyzer(value="ik_max_word",use=true),
            norms = @Norms(value=true,use=true),
            index_options = @IndexOptions(value=IndexOption.positions,use=true),
            search_analyzer = @SearchAnalyzer(value="ik_smart",use=true))
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
