package com.tmdrk.chat.common.entity.es;

import com.tmdrk.chat.common.entity.es.baseAnnotation.BooleanAnno;
import com.tmdrk.chat.common.entity.es.baseAnnotation.ClassAnno;
import com.tmdrk.chat.common.entity.es.baseAnnotation.StringAnno;
import com.tmdrk.chat.common.entity.es.fieldAnnotation.*;
import com.tmdrk.chat.common.utils.ElasticsearchUtil;

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
@EsIndex(settings=@Settings(numberOfShards=2,numberOfReplicas = 1),needMapping = true,aliases = {"tp31","tp32"})
public class TestProductNew {
    public static void main(String[] args) throws Exception {
        TestProductNew testProductNew = new TestProductNew();
        Class clazz = testProductNew.getClass();
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

            String methodJson = ElasticsearchUtil.dealWithProperties(fields);

            System.out.println(methodJson);
        }
    }

    /** 商品id **/
    @PropertiesNew(type = @StringAnno(value="long",use = true))
    public long id;
    /** 商品简称 **/
    @PropertiesNew(type = @StringAnno(value="keyword",use = true))
    public String procuctShortName;
    /** 商品全称 **/
    @PropertiesNew(type = @StringAnno(value="text"),
            analyzer = @StringAnno(value="ik_max_word",use=true),
            norms = @BooleanAnno(value=true,use=true),
            search_analyzer = @StringAnno(value="ik_smart",use=true),
            fields = @ClassAnno(value=Default.class,use=true),
            index_options = @IndexOptions(value=IndexOption.positions))
    public String procuctFullName;
    /** 商品sku **/
    @PropertiesNew(type = @StringAnno(value="keyword",use = true))
    public String procuctSku;
    /** 商品价格 **/
    @PropertiesNew(type = @StringAnno(value="double",use = true))
    public double procuctPrice;
    /** 商品库存状态 **/
    @PropertiesNew(type = @StringAnno(value="long",use = true))
    public long stockState;
    /** 商品库存状态名称 **/
    @PropertiesNew(type = @StringAnno(value="keyword",use = true))
    public String stockStateName;
    /** 商品重量 **/
    @PropertiesNew(type = @StringAnno(value="keyword",use = true))
    public String procuctWeight;
    /** 商品图片地址 **/
    @PropertiesNew(type = @StringAnno(value="keyword",use = true))
    public String procuctImgPath;
    /** 商品上架状态 **/
    @PropertiesNew(type = @StringAnno(value="boolean",use = true))
    public boolean onShelveStatus;
    /** 商品描述 **/
    @PropertiesNew(type = @StringAnno(value="text",use = true),
            analyzer = @StringAnno(value="ik_max_word",use=true),
            norms = @BooleanAnno(value=true,use=true),
            index_options = @IndexOptions(value=IndexOption.positions),
            search_analyzer = @StringAnno(value="ik_smart",use=true))
    public String procuctDescription;
    /** 商品创建时间 **/
    @PropertiesNew(type = @StringAnno(value="date",use = true))
    public Date createTime;
    /** 商品更新时间 **/
    @PropertiesNew(type = @StringAnno(value="date",use = true))
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
