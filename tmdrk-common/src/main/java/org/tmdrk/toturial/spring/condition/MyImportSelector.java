package org.tmdrk.toturial.spring.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @ClassName MyImportSelector
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/11 11:07
 * @Version 1.0
 **/
public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"org.tmdrk.toturial.spring.service.color.Red","org.tmdrk.toturial.spring.service.color.Blue"};
    }
}
