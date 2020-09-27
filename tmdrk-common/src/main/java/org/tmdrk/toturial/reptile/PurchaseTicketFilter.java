package org.tmdrk.toturial.reptile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static org.tmdrk.toturial.reptile.HttpClientTest.ISO_8859_1;
import static org.tmdrk.toturial.reptile.HttpClientTest.UTF_8;

/**
 * @ClassName PurchaseTicketFilter
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/9/26 20:49
 * @Version 1.0
 **/
public class PurchaseTicketFilter {
    public static void main(String[] args) {
        List<String> tickets = toArrayByFileReader("D://Files//ticket.txt");
        StringBuilder sb = new StringBuilder();
        Map<String,String[]> map = new HashMap<>();
        for(int i=0;i<tickets.size();i++){
            if(i==0){
                Ticket ticket = JSON.parseObject(tickets.get(i), Ticket.class);
                if(ticket != null){
                    Ticket.TicketInfo ticketInfo = ticket.getData();
                    ticketInfo.result.forEach(info->{
                        if(info != null){
                            String[] split = info.split("\\|");
//                    System.out.println(split[3]+":"+split.length+"|"+split[30]+"|"+split[31]+"|"+split[32]);
                            if(split[3].startsWith("G")||split[3].startsWith("D")){
                                map.put(split[3],split);
                            }
                        }
                    });
                }
            }else{
                List<String> list = new ArrayList();
                Ticket ticketDes = JSON.parseObject(tickets.get(i), Ticket.class);
                if(ticketDes != null){
                    Ticket.TicketInfo ticketInfo = ticketDes.getData();
                    ticketInfo.result.forEach(info->{
                        if(info != null){
                            String[] split = info.split("\\|");
                            if(map.containsKey(split[3])){
                                if(!Objects.equals(split[30],"无")){
                                    sb.append(fillingStr(split[3],5)).append("|")
                                            .append(split[8]).append("|")
                                            .append(split[9]).append("|")
                                            .append(split[10]).append("|")
                                            .append(split[30]).append("|")
                                            .append("到蚌埠:"+map.get(split[3])[9]).append("|  ")
                                            .append(fillingStr(ticketInfo.map.get(split[6]),2)).append("-")
                                            .append(fillingStr(ticketInfo.map.get(split[7]),2));
                                    list.add(sb.toString());
                                    sb.delete(0,sb.length());
                                }
                            }
                        }
                    });
                }
                list.forEach(System.out::println);
            }
            System.out.println("=======================================");
        }

    }

    public static List<String> toArrayByFileReader(String name) {
        // 使用ArrayList来存储每行读取到的字符串
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            FileReader fr = new FileReader(name);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null && !str.equals("")) {
                arrayList.add(str);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static String fillingStr(String name,int length) {
        return fillingStr(name,length," ");
    }

    public static String fillingStr(String name,int length,String filling) {
        if(name.length() < length){
            name = StringUtils.rightPad(name,length,filling);
        }
        return name;
    }
}

@Data
class Ticket{

    private int httpstatus;
    private TicketInfo data;

    @Data
    class TicketInfo{
        List<String> result;
        Map<String,String> map;
    }
}
