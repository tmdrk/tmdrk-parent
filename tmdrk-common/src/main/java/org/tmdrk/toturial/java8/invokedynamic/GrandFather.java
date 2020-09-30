package org.tmdrk.toturial.java8.invokedynamic;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

/**
 * GrandFather
 *
 * @author Jie.Zhou
 * @date 2020/9/28 15:13
 */
public class GrandFather {
    void thinking() {
        System.out.println("i am grandfather");
    }
    public static void main(String[] args) {
        (new Son()).thinking();
    }
}

class Father extends GrandFather {
    void thinking() {
        System.out.println("i am father");
    }
}
class Son extends Father {
    void thinking() {
        // 8.4.5　实战：掌控方法分派规则
        // 请读者在这里填入适当的代码（不能修改其他地方的代码）
        // 实现调用祖父类的thinking()方法，打印"i am grandfather
        // ********** 然而作者的说法并没有成功，反射还是只会打印父类方法 *********
        try {
            MethodType mt = MethodType.methodType(void.class);
            Field lookupImpl = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
            lookupImpl.setAccessible(true);
            MethodHandle mh = ((MethodHandles.Lookup) lookupImpl.get(null)).findSpecial(GrandFather.class,"thinking",mt,getClass());
            mh.invoke(this);
        } catch (Throwable e) {

        }
    }
}

