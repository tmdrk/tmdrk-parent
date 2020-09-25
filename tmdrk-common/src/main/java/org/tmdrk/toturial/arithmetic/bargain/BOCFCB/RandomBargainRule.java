package org.tmdrk.toturial.arithmetic.bargain.BOCFCB;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 砍价的几个因素：
 * 1. 可砍价的人数(N)
 * 2. 可砍掉的总金额(T)
 * 3. 每次砍价的最低砍价金额(M)
 * 4. 总砍价次数(K)
 * <p>
 * 本砍价规则将'每次砍价的最低砍价金额'默认为 := T / (K * 2)。
 */
public class RandomBargainRule implements BargainRule {

    @Override
    public List<Integer> getReduceList(int totalReduce, int totalReduceTimes) {
        if (totalReduceTimes <= 0) {
            throw new IllegalArgumentException("total reduce times should gte 1");
        }

        // 最低砍价金额默认为： 总砍价金额 / (砍价次数 * 2)
        int minReduce = totalReduce/(totalReduceTimes*2);
        return getReduceList(totalReduce, totalReduceTimes, minReduce);
    }

    public List<Integer> getReduceList(int totalReduce, int totalReduceTimes, int minReduce) {
        List<Integer> ret = new ArrayList<>();
        Random rand = new Random();
        Double[] vs = new Double[totalReduceTimes];
        int total = 0;
        for (int i = 0; i < totalReduceTimes; i++) {
            Double v = rand.nextDouble() * totalReduce;
            vs[i] = v;
            total += v;
        }

        int minReduceVal = minReduce;
        int t = 0;
        for (int i = 0; i < totalReduceTimes - 1; i++) {
            Double vss = vs[i] / total * (totalReduce - minReduceVal * totalReduceTimes) + minReduceVal;
            int reduce = vss.intValue();
            t = t+reduce;
            ret.add(reduce);
        }
        ret.add(totalReduce);
        return ret;
    }


    public static void main(String[] args) {
//        int t = new Random().nextInt(20) + 1;
        int t = 4 + 1;
        int i = 0;
//        BigDecimal tt = BigDecimal.valueOf(new Random().nextDouble() * 10000 + 1).setScale(2, RoundingMode.HALF_UP);
        int tt = new Random().nextInt(100)+1;
        List<Integer> reduceList = new RandomBargainRule().getReduceList(tt, t);
        int total = 0;
        for (Integer d : reduceList) {
            total = total+d;
            System.out.printf("第%d个人砍掉了%s\n", ++i, d);
        }
        System.out.println("砍价总金额=" + tt + "，合计总金额=" + total);
    }
}
