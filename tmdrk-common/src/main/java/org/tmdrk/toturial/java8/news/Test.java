package org.tmdrk.toturial.java8.news;

import cn.hutool.core.util.IdUtil;

import java.util.Optional;

/**
 * @ClassName Test
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/4/10 15:08
 * @Version 1.0
 **/
public class Test implements InterfaceTest{
    public static void main(String[] args) {
        Test test = new Test();
        System.out.println(test.getName("zhoujie"));
        test = null;
        Optional<Test> optional = Optional.ofNullable(test);
        optional.ifPresent(t->t.getName("hei"));
        System.out.println(optional.isPresent());
        for(int i=0;i<10;i++){
            System.out.println(IdUtil.objectId());
        }
    }
}
