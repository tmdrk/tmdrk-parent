package com.tmdrk.chat.common.utils.reptile;//package org.tmdrk.toturial.reptile;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.util.EntityUtils;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * @ClassName FootballStat
// * @Description 根据赔率让球过滤
// * @Author zhoujie
// * @Date 2019/7/2 1:10
// * @Version 1.0
// **/
//public class FootballStat {
//    public static void main(String[] args) {
//        doGetTotalUrl();
//    }
//
//    public static void doGetTotalUrl() {
//        String html = doGet("https://live.dszuqiu.com/ajax/score/data?mt="+DateUtil.getTime());
//        System.out.println(html);
//        JSONObject jsonObject = JSONObject.parseObject(html);
//        List<HashMap> list = JSON.parseArray(jsonObject.get("rs").toString(), HashMap.class);
//        System.out.println("list.size():"+list.size());
//        for(HashMap map:list){
//            Map host = JSONObject.toJavaObject((JSONObject)map.get("host"), Map.class);
//            Map guest = JSONObject.toJavaObject((JSONObject)map.get("guest"), Map.class);
//            Map h_ld = JSONObject.toJavaObject((JSONObject)map.get("h_ld"), Map.class);
//            Map f_ld = JSONObject.toJavaObject((JSONObject)map.get("f_ld"), Map.class);
//            System.out.print("id="+map.get("id").toString());
//            if(host==null){
//                System.out.println();
//                continue;
//            }
//            System.out.println("  host="+showStr(host.get("stn"))+" guest="+showStr(guest.get("stn")));
//            if(h_ld!=null){
//                System.out.println("  半场 主队="+showStr(h_ld.get("hrfsp"))+" 让球="+showStr(h_ld.get("hrf"))+" 客队="+showStr(h_ld.get("grfsp"))+" 高于="+showStr(h_ld.get("hdxsp"))+" 大小球="+showStr(h_ld.get("hdx"))+" 低于="+showStr(h_ld.get("gdxsp")));
//            }
//            System.out.println("  全场 主队="+showStr(f_ld.get("hrfsp"))+" 让球="+showStr(f_ld.get("hrf"))+" 客队="+showStr(f_ld.get("grfsp"))+" 高于="+showStr(f_ld.get("hdxsp"))+" 大小球="+showStr(f_ld.get("hdx"))+" 低于="+showStr(f_ld.get("gdxsp")));
//        }
//    }
//
//    public static String doGet(String url){
//        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//        HttpGet httpGet = new HttpGet(url);
//        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36");
//        CloseableHttpResponse response = null;
//        try {
//            System.out.println("请求开始...");
//            response = httpClient.execute(httpGet);
//            System.out.println("请求结束...");
//            System.out.println("响应状态为:" + response.getStatusLine());
//            HttpEntity responseEntity = response.getEntity();
//            System.out.println("响应内容长度为:" + responseEntity.getContentLength());
//            String html = EntityUtils.toString(responseEntity);//获得html源代码
//            html = unicodeDecode(html);
//            html = new String(html.getBytes("ISO-8859-1"),"utf-8");
//            return html;
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally{
//            try{
//                if(httpClient != null) {
//                    httpClient.close();
//                }
//                if(response != null) {
//                    response.close();
//                }
//            } catch(IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//        return null;
//    }
//
//
//    public static String unicodeEncode(String string) {
//        char[] utfBytes = string.toCharArray();
//        String unicodeBytes = "";
//        for (int i = 0; i < utfBytes.length; i++) {
//            String hexB = Integer.toHexString(utfBytes[i]);
//            if (hexB.length() <= 2) {
//                hexB = "00" + hexB;
//            }
//            unicodeBytes = unicodeBytes + "\\u" + hexB;
//        }
//        return unicodeBytes;
//    }
//
//    public static String unicodeDecode(String string) {
//        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
//        Matcher matcher = pattern.matcher(string);
//        char ch;
//        while (matcher.find()) {
//            ch = (char) Integer.parseInt(matcher.group(2), 16);
//            string = string.replace(matcher.group(1), ch + "");
//        }
//        return string;
//    }
//    public static String showStr(Object obj){
//        return obj==null?"":obj.toString();
//    }
//
//}
