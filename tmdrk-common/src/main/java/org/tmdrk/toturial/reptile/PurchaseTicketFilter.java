package org.tmdrk.toturial.reptile;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;
import org.tmdrk.toturial.common.util.SendEamilUtil;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @ClassName PurchaseTicketFilter
 * @Description 刷票程序
 * 刷直达目的地或者最终经过目的地的短程火车票，并将合适的票发邮件提醒
 * @Author zhoujie
 * @Date 2020/9/26 20:49
 * @Version 1.0
 **/
public class PurchaseTicketFilter {
    static String cookies="_uab_collina=160117018426887962014074; JSESSIONID=07B31A781F88FF35D72D8000E15818CB; RAIL_DEVICEID=XlNwgOJJhT5mZ0EwQ3rOQOw5Bobwvaxr-StHNqqyPuSh7HhI4-vS8jvJeNpCW0chJ6rphcTaD7JkCzsUwou2UiVTHWQKncBm49horB3oQ9ljbVkqID3ZsIW3R1VEdFotKbEIkLsW6pQILL6n6xZciCgPhCwk1kn1; RAIL_EXPIRATION=1601469495003; BIGipServerotn=2129133834.24610.0000; BIGipServerpassport=870842634.50215.0000; route=6f50b51faa11b987e576cdb301e545c4; _jc_save_fromStation=%u4E0A%u6D77%2CSHH; _jc_save_toStation=%u868C%u57E0%2CBBH; _jc_save_fromDate=2020-09-27; _jc_save_toDate=2020-09-27; _jc_save_wfdc_flag=dc";
    /****** 去程 ******/
    // 购票日期
    static String date = "2020-10-01";
    // 理想票开始时间
    static String startTime = "07:30";
    // 理想票结束时间
    static String endTime = "18:00";
    // 购票url
    static List<String> urls = Arrays.asList(
            //上海-蚌埠
            "https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date="+date+"&leftTicketDTO.from_station=SHH&leftTicketDTO.to_station=BBH&purpose_codes=ADULT",
            //上海-昆山
            "https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date="+date+"&leftTicketDTO.from_station=SHH&leftTicketDTO.to_station=KNH&purpose_codes=ADULT",
            //上海-苏州
            "https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date="+date+"&leftTicketDTO.from_station=SHH&leftTicketDTO.to_station=SZH&purpose_codes=ADULT",
            //上海-常州
            "https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date="+date+"&leftTicketDTO.from_station=SHH&leftTicketDTO.to_station=CZH&purpose_codes=ADULT",
            //上海-南京
            "https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date="+date+"&leftTicketDTO.from_station=SHH&leftTicketDTO.to_station=NJH&purpose_codes=ADULT"
    );
    /****** 返程 ******/
//    // 购票日期
//    static String date = "2020-10-07";
//    // 理想票开始时间
//    static String startTime = "07:30";
//    // 理想票结束时间
//    static String endTime = "17:00";
//    // 购票url
//    static List<String> urls = Arrays.asList(
//            //蚌埠-上海
//            "https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date="+date+"&leftTicketDTO.from_station=BBH&leftTicketDTO.to_station=SHH&purpose_codes=ADULT",
//            //蚌埠-昆山
//            "https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date="+date+"&leftTicketDTO.from_station=BBH&leftTicketDTO.to_station=KNH&purpose_codes=ADULT",
//            //蚌埠-苏州
//            "https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date="+date+"&leftTicketDTO.from_station=BBH&leftTicketDTO.to_station=SZH&purpose_codes=ADULT",
//            //蚌埠-常州
//            "https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date="+date+"&leftTicketDTO.from_station=BBH&leftTicketDTO.to_station=CZH&purpose_codes=ADULT",
//            //蚌埠-南京
//            "https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date="+date+"&leftTicketDTO.from_station=BBH&leftTicketDTO.to_station=NJH&purpose_codes=ADULT"
//    );

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            // 获取票信息
            List<String> tickets = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                String json = HttpClientTest.doGet(urls.get(i), null, null, cookies);
                if (StringUtils.isNotBlank(json)) {
                    tickets.add(json);
                }
                Thread.sleep(500);
            }
            // 控制台打印匹配的票信息
            printTicket(tickets);
            // 5秒一刷
            Thread.sleep(10000);
        }
    }

    private static void printTicket(List<String> tickets) {
        //        List<String> tickets = toArrayByFileReader("D://tmp//file//ticket.txt");
        StringBuilder sb = new StringBuilder();
        List<String> list = new ArrayList();
        StringBuilder perfectSb = new StringBuilder();
        List<String> perfectList = new ArrayList();
        Map<String,String[]> map = new HashMap<>();
        for(int i=0;i<tickets.size();i++){
            list.clear();
            if(i==0){
                // 目的地票信息单独处理，作为检索过滤基础信息。
                Ticket ticket = JSON.parseObject(tickets.get(i), Ticket.class);
                if(ticket == null) {
                    throw new RuntimeException("目的地票信息不能为空");
                }
                Ticket.TicketInfo ticketInfo = ticket.getData();
                ticketInfo.result.forEach(info->{
                    if(info != null){
                        String[] split = info.split("\\|");
                        if(split[3].startsWith("G")||split[3].startsWith("D")){
                            map.put(split[3],split);
                            if(!Objects.equals(split[30],"无")){
                                addList(sb, list, ticketInfo, split, map.get(split[3]));
                                if(endTime.compareToIgnoreCase(split[8]) > 0 && startTime.compareToIgnoreCase(split[8]) < 0){
                                    addList(perfectSb, perfectList, ticketInfo, split, map.get(split[3]));
                                }
                            }
                        }
                    }
                });
            }else{
                if(!(tickets.get(i).startsWith("{") || tickets.get(i).startsWith("["))){
                    System.out.println("error:"+tickets.get(i));
                    continue;
                }
                Ticket ticketDes = JSON.parseObject(tickets.get(i), Ticket.class);
                if(ticketDes != null){
                    Ticket.TicketInfo ticketInfo = ticketDes.getData();
                    ticketInfo.result.forEach(info->{
                        if(info != null){
                            String[] split = info.split("\\|");
                            if(map.containsKey(split[3])){
                                if(!Objects.equals(split[30],"无")){
                                    addList(sb, list, ticketInfo, split, map.get(split[3]));
                                    if(startTime.compareToIgnoreCase(split[8]) < 0 && endTime.compareToIgnoreCase(split[8]) > 0){
                                        addList(perfectSb, perfectList, ticketInfo, split, map.get(split[3]));
                                    }
                                }
                            }
                        }
                    });
                }
            }
            list.forEach(System.out::println);
            System.out.println("=======================================");
        }
        System.out.println("+++++++++++++++++ 理想的票 ++++++++++++++++");
        perfectList.forEach(System.out::println);
        System.out.println("----------------- 理想的票 ----------------");
        // 如果有理想的票则发送邮件通知
        if(!CollectionUtils.isEmpty(perfectList)){
            System.out.println("准备发送邮件...");
            try {
                StringBuilder sbd = new StringBuilder();
                sbd.append("车次   |").append("起始   |").append("结束  |").append("耗时  |").append("余票|").append("到目的地时间|").append("当前车次起始站").append("\n");
                perfectList.forEach(per->{
                    sbd.append(per).append("\n");
                });
                SendEamilUtil.sentSimpleMail("抢票汇报",sbd.toString(),"1308398245@qq.com");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void addList(StringBuilder sb, List<String> list, Ticket.TicketInfo ticketInfo, String[] split, String[] strings) {
        sb.append(fillingStr(split[3], 5)).append("|")
                .append(split[8]).append("|")
                .append(split[9]).append("|")
                .append(split[10]).append("|")
                .append(split[30]).append("|")
                .append("到蚌埠:" + strings[9]).append("|  ")
                .append(fillingStr(ticketInfo.map.get(split[6]), 2)).append("-")
                .append(fillingStr(ticketInfo.map.get(split[7]), 2));
        list.add(sb.toString());
        sb.delete(0, sb.length());
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
