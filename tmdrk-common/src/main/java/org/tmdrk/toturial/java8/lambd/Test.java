package org.tmdrk.toturial.java8.lambd;

/**
 * @ClassName Test
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/16 1:24
 * @Version 1.0
 **/
public class Test {
    public static void main(String[] args) {
        Test test = new Test();
        int a=9,b=56;
        MyWork myWork = new MyWork();
        test.test1(a,b,myWork);

        MyFactory myFactory = myWork.map.get("key1");
        System.out.println(myFactory.add());
        System.out.println(myFactory.add());
        System.out.println(myFactory.add());
    }
    public void test(int a,int b,MyWork myWork){
        myWork.work("key1",()->doWork(a,b));
    }

    public void test1(int a,int b,MyWork myWork){
        myWork.work("key1", new MyFactory() {
            @Override
            public int add() {
                return doWork(a,b);
            }
        });
    }

    public int doWork(int a,int b){
        return a*b;
    }
}
