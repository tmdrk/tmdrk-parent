package org.tmdrk.toturial.arithmetic.bargain.fudeng;

import java.math.BigDecimal;
import java.util.List;

public interface IReduceRule {

    /**
     * 获取砍价列表。
     *
     * @param totalReduce      总共可砍价的金额
     * @param totalReduceTimes 总共可砍价的次数
     * @param average          是否平均分配
     * @return 砍价列表
     */
    List<Integer> getReduceList(BigDecimal totalReduce, int totalReduceTimes, boolean average);
}
