package org.tmdrk.toturial.arithmetic.bargain.BOCFCB;

import java.math.BigDecimal;
import java.util.List;

/**
 * 仿拼多多砍价规则。
 */
public class BOCFCBBargainRule implements BargainRule {

    private final int percentOfFirstNReduce;
    private final int firstNReduce;

    private final RandomBargainRule sfReduceRule = new RandomBargainRule();

    /**
     * new PingDuoDuoReduceRule(80, 10) 表示前10个人可砍掉总砍价金额的80%。
     *
     * @param percentOfFirstNReduce 前N个人可砍掉的百分比，取值范围(0, 100]
     * @param firstNReduce          前N个砍价的人
     */
    public BOCFCBBargainRule(int percentOfFirstNReduce, int firstNReduce) {
        this.percentOfFirstNReduce = percentOfFirstNReduce;
        this.firstNReduce = firstNReduce;
    }


    /**
     * @param totalReduce      总共可砍价的金额
     * @param totalReduceTimes 总共可砍价的次数
     * @return 每次砍掉的金额组成的列表。
     */
    @Override
    public List<Integer> getReduceList(int totalReduce, int totalReduceTimes) {
        if (totalReduceTimes < firstNReduce) {
            throw new IllegalArgumentException("total reduce times is less than first n reduce times");
        }

        if (percentOfFirstNReduce > 100 || percentOfFirstNReduce <= 0) {
            throw new IllegalArgumentException("reduce percent should in (0, 100]");
        }

        int firstNReduceTotal = totalReduce * percentOfFirstNReduce / 100;
        List<Integer> ret = sfReduceRule.getReduceList(firstNReduceTotal, firstNReduce);
        int leftReduceTotal = totalReduce-firstNReduceTotal;
        if (totalReduceTimes - firstNReduce > 0) {
            ret.addAll(sfReduceRule.getReduceList(leftReduceTotal, totalReduceTimes - firstNReduce));
        }
        return ret;
    }

    public static void main(String[] args) {
        BOCFCBBargainRule rule = new BOCFCBBargainRule(60, 5);
        List<Integer> list = rule.getReduceList(100, 10);
        int total = 0;
        int i = 0;
        for (Integer val : list) {
            System.out.printf("第%d人砍掉了%s\n", ++i, val);
            total = total+val;
        }
        System.out.printf("总金额=%s\n", total);
    }
}
