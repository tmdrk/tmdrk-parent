package org.tmdrk.toturial.classFrame.dispatch.statics;

/**
 * Test
 * 静态分派测试
 * @author Jie.Zhou
 * @date 2020/10/20 16:04
 */
public class Test {
    // 类定义
    static abstract class Human {
    }

    // 继承自抽象类Human
    static class Man extends Human {
    }

    static class Woman extends Human {
    }

    // 可供重载的方法
    public void sayHello(Human guy) {
        System.out.println("hello,guy!");
    }

    public void sayHello(Man guy) {
        System.out.println("hello gentleman!");
    }

    public void sayHello(Woman guy) {
        System.out.println("hello lady!");
    }

    // 测试代码
    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        Test test = new Test();

        test.sayHello(man);
        test.sayHello(woman);
    }
}
