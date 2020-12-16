package org.tmdrk.interview.hyw;

import cn.hutool.core.util.RandomUtil;
import org.tmdrk.toturial.entity.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ProbabilityTest
 *
 * @author Jie.Zhou
 * @date 2020/12/8 9:03
 */
public class ProbabilityTest {
    public static void main(String[] args) {
        List<User> list = new ArrayList();
        User user1 = new User(1L,"张三","12343234566",null);
        user1.setProbability(new BigDecimal("0.2"));
        User user2 = new User(2L,"历史","12343234566",null);
        user2.setProbability(new BigDecimal("0.3"));
        User user3 = new User(3L,"王二毛","12343234566",null);
        user3.setProbability(new BigDecimal("0.5"));
        list.add(user1); list.add(user2); list.add(user3);
        Map<Long,Integer> map = new HashMap();
        map.put(1L,0);map.put(2L,0);map.put(3L,0);
        for(int i=0;i<10000;i++){
            BigDecimal random = RandomUtil.randomBigDecimal();
            BigDecimal decimal = new BigDecimal(0);
            for (User user : list) {
                decimal = decimal.add(user.getProbability());
                if (random.compareTo(decimal) < 0) {
                    map.put(user.getUserId(),map.get(user.getUserId())+1);
                    break;
                }
            }
        }
        System.out.println("user1中奖率："+map.get(1L)+"%");
        System.out.println("user2中奖率："+map.get(2L)+"%");
        System.out.println("user3中奖率："+map.get(3L)+"%");
    }
}
