package org.tmdrk.toturial.xml;

import com.alibaba.fastjson.JSON;
import org.codehaus.jackson.map.ObjectMapper;

import javax.print.attribute.HashPrintJobAttributeSet;
import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author Jie.Zhou
 * @date 2020/7/31 8:52
 */
public class HywTest2 {
    public static void main(String[] args) {
        String body = "<p>毛锴锴(先生) 185****0708 <br>石排湾郊野公园石排湾郊野公园<br>-<br>鱼香肉丝  x2<br>招牌羊排  x1<br>好吃好吃  x1<br></p>";
        System.out.println("json:"+parseToJsonString(body));
        EleOrderInfo eleOrderInfo = JSON.parseObject(parseToJsonString(body), EleOrderInfo.class);
        System.out.println("eleOrderInfo:"+eleOrderInfo.toString());

        List<EleOrderInfo.FoodsInfo> foodsInfo = eleOrderInfo.getFoodsInfo();
        int quantity;
        quantity = foodsInfo.stream().mapToInt(EleOrderInfo.FoodsInfo::getCount).sum();
        int total1 = foodsInfo.stream().map(EleOrderInfo.FoodsInfo::getCount).reduce(Integer::sum).get();
        String foodsName;
        foodsName = foodsInfo.stream().map(EleOrderInfo.FoodsInfo::getFoodName).reduce((a,b) ->a +","+ b).get();
        System.out.println(foodsName);
        System.out.println(quantity);
        String s = foodsInfo.toString();
        System.out.println(s);
        /* foodsInfo.stream().collect(Collectors.toMap(EleOrderInfo.FoodsInfo::getFoodName,)); */
        Map<String,Integer> foodMap = new HashMap();
        for (EleOrderInfo.FoodsInfo food : foodsInfo) {
            if (foodMap.containsKey(food.getFoodName())) {
                foodMap.put(food.getFoodName(), foodMap.get(food.getFoodName()) + 1);
            } else {
                foodMap.put(food.getFoodName(), 1);
            }
        }
        Iterator<Map.Entry<String, Integer>> iterator = foodMap.entrySet().iterator();
//        StringBuilder sb = new StringBuilder();
//        while(iterator.hasNext()){
//            Map.Entry<String, Integer> next = iterator.next();
//            sb.append(next.getKey()).append(" x").append(next.getValue());
//        }

        StringBuilder sb = new StringBuilder();
        foodsInfo.stream().forEach(fs -> {sb.append(fs.getFoodName()).append(" x").append(fs.getCount()).append(",");});
        sb.deleteCharAt(sb.length()-1);
        System.out.println(sb.toString());
    }
    private static String parseToJsonString(String body){
        body = body.trim().replaceAll(" +"," ").replace("<p>", "").replace("</p>", "");
        String[] split = body.split("<br>-<br>");
        if(split.length != 2){
            throw new RuntimeException("数据格式异常");
        }
        String[] msg = split[0].split("<br>");
        if(msg.length != 2){
            throw new RuntimeException("数据格式异常");
        }
        EleOrderInfo eleOrderInfo = new EleOrderInfo();
        String[] nameAndMobile = msg[0].split(" ");
        eleOrderInfo.setRealName(nameAndMobile[0]);
        if(nameAndMobile.length>=2){
            eleOrderInfo.setMobile(nameAndMobile[1]);
        }
        eleOrderInfo.setAddress(msg[1]);
        String[] foods = split[1].split("<br>");
        System.out.println(Arrays.asList(foods));
        List<EleOrderInfo.FoodsInfo> list = new ArrayList<>();
        for (int i=0;i<foods.length;i++) {
            String[] food = foods[i].split(" ");
            if(food.length==2){
                EleOrderInfo.FoodsInfo foodsInfo = eleOrderInfo.new FoodsInfo();
                foodsInfo.setFoodName(food[0]);
                String count = food[1].replace("x", "");
                if("".equals(count)){
                    throw new RuntimeException("数据解析异常");
                }
                foodsInfo.setCount(Integer.parseInt(count));
                list.add(foodsInfo);
            }
        }
        eleOrderInfo.setFoodsInfo(list);
        return JSON.toJSONString(eleOrderInfo);
    }
}
