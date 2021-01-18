package org.tmdrk.toturial.http;

import com.alibaba.fastjson.JSON;
import com.gtown.cloud.utils.http.HttpUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.tmdrk.toturial.entity.OrderDetail;
import org.tmdrk.toturial.entity.User;
import org.tmdrk.toturial.thread.pool.BusinessThreadPoolExecutorFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.google.common.base.Charsets.UTF_8;

/**
 * BocfTest
 *
 * @author Jie.Zhou
 * @date 2020/11/19 9:06
 */
public class BocfTest {
    private static org.apache.log4j.Logger log = Logger.getLogger(BocfTest.class);
    public static void main(String[] args) throws IOException, InterruptedException {
        Map<String,Object> para = new HashMap<>();
        para.put("name","zhouzhou");
        para.put("age","12");
        String str = para.entrySet().stream().map(e -> String.format("%s=%s", e.getKey(), e.getValue())).collect(Collectors.joining("&"));
        System.out.println(str);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date limitDate = DateUtils.addDays(new Date(),-4);
        System.out.println("limitDate:"+simpleDateFormat.format(limitDate));
        System.out.println("limitDate:"+limitDate.getTime());
        //url是访问服务器的url地址，http:// zyfdwbnkuat.bocfullertonbank.com/pwxweb/GetMessageForMobile.do
//        String url = "http://zyfdwbnkuat.bocfullertonbank.com/pwxweb/GetMessageForMobile.do";
        String url = "http://zyfdwbnksit.bocfullertonbank.com/pwxweb/GetMessageForMobile.do";
//        String url = "https://ebank.bocfullertonbank.com/pwxweb/GetMessageForMobile.do";
        HttpUriRequest request = new HttpPost(url);

        System.out.println("user="+JSON.toJSONString(null));

        //CommDictAction.strCookie是cookie值
//        request.setHeader("Cookie", CommDictAction.strCookie);
//        request.setHeader("Cookie", "UM_distinctid=17392fe4a742c4-02c71cbf04dc48-3962420d-100200-17392fe4a755db");
//        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
//        request.addHeader("User-Agent", "CSII-MOBILE-EBANK MBL.ANDROID");
//        request.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*");
//        request.addHeader("Connection", "Keep-Alive");

        long start = System.currentTimeMillis();
        //list里存放请求参数，以BasicNameValuePair类型存放，BasicNameValuePair里存放是键值对形式。
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        BasicNameValuePair basicNameValuePair = new BasicNameValuePair("MobilePhone", "15340458923");
//        BasicNameValuePair basicNameValuePair = new BasicNameValuePair("MobilePhone", "13917466248");
        list.add(basicNameValuePair);
        ((HttpPost) request).setEntity(new UrlEncodedFormEntity(list, UTF_8));
        HttpClient hc= HttpClients.createDefault();
        HttpResponse response = hc.execute(request);
        HttpEntity entity = response.getEntity();
        String msg = EntityUtils.toString(entity,"UTF-8");
        System.out.println(msg + " "+(System.currentTimeMillis()-start));

        List<String> mobiles = Arrays.asList(
                "13619920002",
                "13011231457",
                "18228636473",
                "13125001228",
                "13225001227");

        AtomicInteger realCnt = new AtomicInteger(0);
        System.out.println("cpuCores:" + Runtime.getRuntime().availableProcessors());
        start = System.currentTimeMillis();
        ExecutorService executor = BusinessThreadPoolExecutorFactory.build(8,20);
        for(String mobile:mobiles){
            executor.execute(new BocfTest.CustomerUtil(url, mobile, realCnt));
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        long end = System.currentTimeMillis();
        log.info("多线程查询富登接口完成 duration:"+(end - start) + " realCnt:"+realCnt);

    }


    static class CustomerUtil implements Runnable{
        private String url;
        private String mobile;
        private AtomicInteger realCnt;

        public CustomerUtil(String url, String mobile, AtomicInteger realCnt){
            this.url = url;
            this.mobile = mobile;
            this.realCnt = realCnt;
        }

        @Override
        public void run() {
            checkCustomer(url, mobile, realCnt);
        }
        private void checkCustomer(String url, String mobile, AtomicInteger realCnt){
            Map<String,Object> para = new HashMap<>();
            para.put("MobilePhone",mobile);
            com.gtown.cloud.utils.http.HttpResponse resp = null;
            try {
                resp = HttpUtils.post(url).form(para).call();
                realCnt.getAndIncrement();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (resp.status() == 200) {
                CustomerAccountInfo customerAccountInfo = JSON.parseObject(resp.string(), CustomerAccountInfo.class);
                System.out.println("查询成功 mobile="+mobile+" "+resp.string());
            }else{
                System.out.println("查询异常 mobile="+mobile);
            }
        }

        @Override
        public String toString() {
            return "CustomerUtil{" +
                    "url='" + url + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", realCnt=" + realCnt +
                    '}';
        }
    }
}


