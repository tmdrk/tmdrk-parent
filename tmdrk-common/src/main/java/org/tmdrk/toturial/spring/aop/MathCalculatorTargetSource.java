package org.tmdrk.toturial.spring.aop;

import org.springframework.aop.TargetSource;

/**
 * @ClassName MathCalculatorTargetSource
 * @Description bean实例化时会调用getTarget实例化对象
 * @Author zhoujie
 * @Date 2020/4/27 14:55
 * @Version 1.0
 **/
public class MathCalculatorTargetSource implements TargetSource {
    private MathCalculator mathCalculator;

    public MathCalculatorTargetSource(){
        this.mathCalculator = new MathCalculator();
    }

    @Override
    public Class<?> getTargetClass() {
        return MathCalculator.class;
    }

    @Override
    public boolean isStatic() {
        return false;
    }

    @Override
    public Object getTarget() throws Exception {
        System.out.println("MathCalculatorTargetSource调用getTarget方法");
        return this.mathCalculator;
    }

    @Override
    public void releaseTarget(Object target) throws Exception {
        System.out.println("MathCalculatorTargetSource releaseTarget");
    }
}
