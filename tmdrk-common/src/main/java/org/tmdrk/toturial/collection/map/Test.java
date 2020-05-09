package org.tmdrk.toturial.collection.map;

/**
 * @ClassName Test
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/3/26 12:02
 * @Version 1.0
 **/
public class Test implements Cloneable {
    int sum = 11;

    public Test(int sum) {
        this.sum = sum;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "Test{" +
                "sum=" + sum +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
