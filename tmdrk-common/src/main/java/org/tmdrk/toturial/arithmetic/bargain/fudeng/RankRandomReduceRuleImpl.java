package org.tmdrk.toturial.arithmetic.bargain.fudeng;

import org.tmdrk.toturial.arithmetic.bargain.PingDuoDuoReduceRule;
import java.math.BigDecimal;
import java.util.List;

/**
 * RankRandomReduceRuleImpl
 *
 * @author Jie.Zhou
 * @date 2020/9/8 13:05
 */
public class RankRandomReduceRuleImpl implements IReduceRule {
    private final int percentOfFirstNReduce;
    private final int firstNReduce;

    private final IReduceRule reduceRule = new RandomReduceRuleImpl();

    /**
     * new PingDuoDuoReduceRule(80, 10) 表示前10个人可砍掉总砍价金额的80%。
     *
     * @param percentOfFirstNReduce 前N个人可砍掉的百分比，取值范围(0, 100]
     * @param firstNReduce          前N个砍价的人
     */
    public RankRandomReduceRuleImpl(int percentOfFirstNReduce, int firstNReduce) {
        this.percentOfFirstNReduce = percentOfFirstNReduce;
        this.firstNReduce = firstNReduce;
    }


    /**
     * @param totalReduce      总共可砍价的金额
     * @param totalReduceTimes 总共可砍价的次数
     * @return 每次砍掉的金额组成的列表。
     */
    @Override
    public List<Integer> getReduceList(BigDecimal totalReduce, int totalReduceTimes, boolean average) {
        if (totalReduceTimes < firstNReduce) {
            throw new IllegalArgumentException("total reduce times is less than first n reduce times");
        }

        if (percentOfFirstNReduce > 100 || percentOfFirstNReduce <= 0) {
            throw new IllegalArgumentException("reduce percent should in (0, 100]");
        }

        BigDecimal firstNReduceTotal = BigDecimal.valueOf(totalReduce.doubleValue() * percentOfFirstNReduce / 100).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        List<Integer> ret = reduceRule.getReduceList(firstNReduceTotal, firstNReduce,average);
        BigDecimal leftReduceTotal = totalReduce.subtract(firstNReduceTotal).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        if (totalReduceTimes - firstNReduce > 0) {
            ret.addAll(reduceRule.getReduceList(leftReduceTotal, totalReduceTimes - firstNReduce,average));
        }
        return ret;
    }

    public static void main(String[] args) {
        RankRandomReduceRuleImpl rule = new RankRandomReduceRuleImpl(70, 4);
        List<Integer> list = rule.getReduceList(new BigDecimal(100), 11,true);
        Integer total = 0;
        int i = 0;
        for (Integer val : list) {
            System.out.printf("第%d人砍掉了%s\n", ++i, val);
            total = total+val;
        }
        System.out.printf("总金额=%s\n", total);
    }

}
