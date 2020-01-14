package org.tmdrk.toturial.spring.app;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * @ClassName MyTypeFilter
 * @Description 自定义ComponentScan.Filter的type属性
 * @Author zhoujie
 * @Date 2020/1/10 23:56
 * @Version 1.0
 **/
public class MyTypeFilter implements TypeFilter {
    /**
     * @Author zhoujie
     * @Description
     * @Date 23:57 2020/1/10
     * @Param [
     * metadataReader 读取到当前正在扫描的类的信息,
     * metadataReaderFactory 可以读取到其他任何类的信息
     * ]
     * @return boolean
     **/
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        //获取注解信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        //获取当前正在扫描的类的类信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        //获取当前类资源（类的路径）
        Resource resource = metadataReader.getResource();

        System.out.println("className-->"+classMetadata.getClassName());

        if(classMetadata.getClassName().contains("er")||classMetadata.getClassName().contains("dao")){
            return true;
        }
        return false;
    }
}
