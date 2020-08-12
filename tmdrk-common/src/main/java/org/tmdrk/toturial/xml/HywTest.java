package org.tmdrk.toturial.xml;

import com.alibaba.fastjson.JSON;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.*;

/**
 * TODO
 *
 * @author Jie.Zhou
 * @date 2020/7/31 8:52
 */
public class HywTest {
    public static void main(String[] args) {
        String body = "<p>毛锴锴(先生) 185****0708 <br>石排湾郊野公园石排湾郊野公园<br>-<br>鱼香肉丝  x2<br>招牌羊排  x1<br>好吃好吃  x1<br></p>";
        System.out.println(parseToJsonString(body));
        Map<String,Object> map = JSON.parseObject(parseToJsonString(body), Map.class);
        map.get("");
    }
    private static String parseToJsonString(String body){
        Map<String,Object> jsonMap = new HashMap<>();
        body = body.trim().replaceAll(" +"," ").replace("<p>", "").replace("</p>", "");
        System.out.println(body);
        String[] split = body.split("<br>-<br>");
        if(split.length != 2){
            throw new RuntimeException("数据格式异常");
        }
        String[] msg = split[0].split("<br>");
        if(msg.length != 2){
            throw new RuntimeException("数据格式异常");
        }
        String[] nameAndMobile = msg[0].split(" ");
        jsonMap.put("realName",nameAndMobile[0]);
        if(nameAndMobile.length>=2){
            jsonMap.put("mobile",nameAndMobile[1]);
        }
        jsonMap.put("address",msg[1]);
        String[] foods = split[1].split("<br>");
        System.out.println(Arrays.asList(foods));
        List<Map<String,String>> list = new ArrayList<>();
        for (int i=0;i<foods.length;i++) {
            String[] food = foods[i].split(" ");
            if(food.length==2){
                Map<String,String> map = new HashMap<>();
                map.put("foodName",food[0]);
                map.put("count",food[1]);
                list.add(map);
            }
        }
        jsonMap.put("foodsInfo",list);
        ObjectMapper mapper = new ObjectMapper();
        return JSON.toJSONString(jsonMap);
    }
}
