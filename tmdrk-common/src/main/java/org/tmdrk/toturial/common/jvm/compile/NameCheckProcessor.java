package org.tmdrk.toturial.common.jvm.compile;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import java.util.Set;

/**
 * NameCheckProcessor
 * 对javac编译器新增功能
 * java程序命名进行检查
 * @author Jie.Zhou
 * @date 2020/9/29 13:40
 */
//使用*表示支持所有的Annotations
//  可以用"*"表示支持所有Annotations
@SupportedAnnotationTypes("*")
//  只支持 JDK 1.8 的 Java代码
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class NameCheckProcessor extends AbstractProcessor {
    private NameChecker nameChecker;

    /**
     *  初始化名称检查
     * @param processingEnv
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        nameChecker = new NameChecker(processingEnv);
    }

    /**
     *  对输入的语法树的各个节点进行名称检查
     * @param annotations
     * @param roundEnv
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver()) {
            for (Element element : roundEnv.getRootElements()){
                nameChecker.checkNames(element);
            }
        }
        return false;
    }
}
