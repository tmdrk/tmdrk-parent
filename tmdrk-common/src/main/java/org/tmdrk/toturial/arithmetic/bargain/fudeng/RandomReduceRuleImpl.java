package org.tmdrk.toturial.arithmetic.bargain.fudeng;

import org.tmdrk.toturial.arithmetic.bargain.SfReduceRule;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * RandomReduceRule
 * 随机砍价 最低价：总砍价金额 / (砍价次数 * 2)
 * @author Jie.Zhou
 * @date 2020/9/8 11:43
 */
public class RandomReduceRuleImpl implements IReduceRule{
    @Override
    public List<Integer> getReduceList(BigDecimal totalReduce, int totalReduceTimes, boolean average) {
        if (totalReduceTimes <= 0) {
            throw new IllegalArgumentException("总金额不能小于等于零");
        }
        if(average){
            return getAverageReduceList(totalReduce,totalReduceTimes);
        }else{
            // 最低砍价金额默认为： 总砍价金额 / (砍价次数 * 2)
            BigDecimal minReduce = totalReduce.divide(BigDecimal.valueOf(totalReduceTimes).multiply(BigDecimal.valueOf(2)), BigDecimal.ROUND_HALF_EVEN);
            return getRandomReduceList(totalReduce, totalReduceTimes, minReduce);
        }
    }

    private List<Integer> getAverageReduceList(BigDecimal totalReduce, int totalReduceTimes) {
        List<Integer> ret = new ArrayList<>();
        BigDecimal avg = totalReduce.divide(new BigDecimal(totalReduceTimes),0, RoundingMode.HALF_EVEN);
        BigDecimal t = BigDecimal.valueOf(0d);
        for(int i = 0; i < totalReduceTimes - 1; i++){
            t = t.add(avg);
            ret.add(avg.intValue());
        }
        ret.add(totalReduce.subtract(t).setScale(0, RoundingMode.HALF_EVEN).intValue());
        return ret;
    }

    public List<Integer> getRandomReduceList(BigDecimal totalReduce, int totalReduceTimes, BigDecimal minReduce) {
        List<Integer> ret = new ArrayList<>();
        Random rand = new Random();
        double[] vs = new double[totalReduceTimes];
        double total = 0d;
        for (int i = 0; i < totalReduceTimes; i++) {
            double v = rand.nextDouble() * totalReduce.doubleValue();
            vs[i] = v;
            total += v;
        }

        double minReduceVal = minReduce.doubleValue();
        BigDecimal t = BigDecimal.valueOf(0d);
        for (int i = 0; i < totalReduceTimes - 1; i++) {
            double vss = vs[i] / total * (totalReduce.doubleValue() - minReduceVal * totalReduceTimes) + minReduceVal;
            BigDecimal reduce = BigDecimal.valueOf(vss).setScale(0, RoundingMode.HALF_EVEN);
            t = t.add(reduce);
            ret.add(reduce.intValue());
        }
        ret.add(totalReduce.subtract(t).setScale(0, RoundingMode.HALF_EVEN).intValue());
        return ret;
    }

    public static void main(String[] args) {
        for(int j=0;j<2;j++){
            int t = new Random().nextInt(20) + 1;
            int i = 0;
//        BigDecimal tt = BigDecimal.valueOf(new Random().nextDouble() * 10000 + 1).setScale(2, RoundingMode.HALF_UP);
            int amt = new Random().nextInt(10000)+1;
            BigDecimal tt = new BigDecimal(amt);
            List<Integer> reduceList = new RandomReduceRuleImpl().getReduceList(tt, t,true);
            int total = 0;
            for (Integer d : reduceList) {
                total = total+d;
                System.out.printf("第%d个人砍掉了%s\n", ++i, d);
            }
            System.out.println("砍价总金额=" + tt + ",合计总金额=" + total+ ",砍价总次数="+t+",实际次数="+reduceList.size());
            if(amt-total!=0||t!=reduceList.size()){
                System.out.println("砍价失败");
            }
        }
    }
}
