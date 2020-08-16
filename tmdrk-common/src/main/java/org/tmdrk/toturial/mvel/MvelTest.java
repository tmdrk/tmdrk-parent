package org.tmdrk.toturial.mvel;

import com.google.common.collect.Maps;
import org.mvel2.MVEL;

import java.util.Map;

public class MvelTest {
    public static void main(String[] args) {

        String expression = "a == null && b == 3";
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("a", null);
        paramMap.put("b", "3");
        Object object = MVEL.eval(expression, paramMap);
        System.out.println(object);


        System.out.println(Integer.parseInt("101111"));
        System.out.println(Integer.parseInt("101111",2));
    }
}
