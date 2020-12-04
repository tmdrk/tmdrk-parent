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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.google.common.base.Charsets.UTF_8;

/**
 * BocfTest
 *
 * @author Jie.Zhou
 * @date 2020/11/19 9:06
 */
public class BocfTest {
    private static org.apache.log4j.Logger log = Logger.getLogger(BocfTest.class);
    public static void main(String[] args) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date limitDate = DateUtils.addDays(new Date(),-4);
        System.out.println("limitDate:"+simpleDateFormat.format(limitDate));
        System.out.println("limitDate:"+limitDate.getTime());
        //url是访问服务器的url地址，http:// zyfdwbnkuat.bocfullertonbank.com/pwxweb/GetMessageForMobile.do
//        String url = "http://zyfdwbnkuat.bocfullertonbank.com/pwxweb/GetMessageForMobile.do";
//        String url = "http://zyfdwbnksit.bocfullertonbank.com/pwxweb/GetMessageForMobile.do";
        String url = "https://ebank.bocfullertonbank.com/pwxweb/GetMessageForMobile.do";
        HttpUriRequest request = new HttpPost(url);

        System.out.println("user="+JSON.toJSONString(null));

        //CommDictAction.strCookie是cookie值
//        request.setHeader("Cookie", CommDictAction.strCookie);
//        request.setHeader("Cookie", "UM_distinctid=17392fe4a742c4-02c71cbf04dc48-3962420d-100200-17392fe4a755db");
//        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
//        request.addHeader("User-Agent", "CSII-MOBILE-EBANK MBL.ANDROID");
//        request.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*");
//        request.addHeader("Connection", "Keep-Alive");

        //list里存放请求参数，以BasicNameValuePair类型存放，BasicNameValuePair里存放是键值对形式。
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        BasicNameValuePair basicNameValuePair = new BasicNameValuePair("MobilePhone", "13011231457");
//        BasicNameValuePair basicNameValuePair = new BasicNameValuePair("MobilePhone", "13917466248");
        list.add(basicNameValuePair);
        ((HttpPost) request).setEntity(new UrlEncodedFormEntity(list, UTF_8));
        HttpClient hc= HttpClients.createDefault();
        HttpResponse response = hc.execute(request);
        HttpEntity entity = response.getEntity();
        String msg = EntityUtils.toString(entity,"UTF-8");
        System.out.println(msg);

        Map<String,Object> para = new HashMap<>();
//        para.put("MobilePhone","13619920002");
//        para.put("MobilePhone","18516157635");
        para.put("MobilePhone","13011231457");
//        para.put("MobilePhone","18301785058");
        com.gtown.cloud.utils.http.HttpResponse resp = HttpUtils.post(url).form(para).call();
        if (resp.status() == 200) {
            System.out.println(resp.string());
//            if (JsonUtils.asInt(body.get("code")) == 200) {
//                log.info("微信立减金发放成功:{}", body);
//            } else {
//                log.info("微信立减金发放失败:body={}", body);
//            }
        }else{
//            log.info("微信立减金发放失败,status={}", resp.status());
        }
        String str1 = "{\"_RejCode\":\"10012\",\"_RejMessage\":\"未查询到客户信息！\"}";
        String str2 = "{\"_RejCode\":\"10012\",\"_RejMessage\":\"未查询到客户信息！\"}";
    }
}
