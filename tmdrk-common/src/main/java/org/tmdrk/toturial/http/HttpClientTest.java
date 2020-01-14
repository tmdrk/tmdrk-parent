package org.tmdrk.toturial.http;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.tmdrk.toturial.common.util.DateUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.jsoup.nodes.Document.OutputSettings.Syntax.html;

public class HttpClientTest {
    public static void main(String[] args) {
        HttpClientTest.doGetTotalUrl();
    }

    public static String doGetTestOne(String url) {
        String html = doGet(url);
//          System.color.println("响应内容为:" + html);
        String line = "";
        if (html!=null&&!"".equals(html)) {
            Document doc = Jsoup.parse(html);
            Elements select = doc.select("div.bottom_info");
            int outCount = 0;
            for (Element element:select) {
                outCount++;
                int count = 0;
                for (Element ele:element.child(0).children()) {
                    count++;
                    if(count==2){
                        if(outCount<=2){
                            line += "近10场战绩 ";
                        }else{
                            line += "近10场主场战绩 ";
                        }
                    }
                    if(count==3){
                        line += " ";
                    }
                    line += ele.text();
                }
                line += "\r\n";
                if(outCount==4){
                    line += "---------------------------------------------------------------"+"\r\n";
                }
            }
            System.out.println(line);
        }
        return line;
    }

    public static void doGetTotalUrl() {
        String html = doGet("http://trade.500.com/jczq/");
//        System.color.println("响应内容为:" + html);
        StringBuffer buffer = new StringBuffer();
        if (html!=null&&!"".equals(html)) {
            Document doc = Jsoup.parse(html);
            Elements select = doc.select("td.td-data");
            for (Element element:select) {
                String url = element.child(0).attr("href");
                System.out.println("url="+url);
                buffer.append(doGetTestOne(url));
            }
        }
        String now = DateUtil.dateFormathhmmss(new Date());
        File file = new File("D:/File/statistic_"+now+".txt");
        FileWriter fw = null;
        BufferedWriter bw = null;
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fw = new FileWriter(file,false);
            bw = new BufferedWriter(fw);
            bw.write(buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        }
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String doGet(String url){
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36");
        CloseableHttpResponse response = null;
        try {
            System.out.println("请求开始...");
            response = httpClient.execute(httpGet);
            System.out.println("请求结束...");
            System.out.println("响应状态为:" + response.getStatusLine());
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应内容长度为:" + responseEntity.getContentLength());
            String html = EntityUtils.toString(responseEntity);//获得html源代码
            html = new String(html.getBytes("ISO-8859-1"),"gb2312");
            return html;
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(httpClient != null) {
                    httpClient.close();
                }
                if(response != null) {
                    response.close();
                }
            } catch(IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }
}
