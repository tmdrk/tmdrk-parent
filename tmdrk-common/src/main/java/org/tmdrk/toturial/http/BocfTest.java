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
import org.tmdrk.toturial.thread.pool.BusinessThreadPoolExecutorFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        Date limitDate = DateUtils.addDays(new Date(),-4);
//        System.out.println("limitDate:"+simpleDateFormat.format(limitDate));
//        System.out.println("limitDate:"+limitDate.getTime());
//        //url是访问服务器的url地址，http:// zyfdwbnkuat.bocfullertonbank.com/pwxweb/GetMessageForMobile.do
////        String url = "http://zyfdwbnkuat.bocfullertonbank.com/pwxweb/GetMessageForMobile.do";
//        String url = "http://zyfdwbnksit.bocfullertonbank.com/pwxweb/GetMessageForMobile.do";
////        String url = "https://ebank.bocfullertonbank.com/pwxweb/GetMessageForMobile.do";
//        HttpUriRequest request = new HttpPost(url);
//
//        System.out.println("user="+JSON.toJSONString(null));
//
//        //CommDictAction.strCookie是cookie值
////        request.setHeader("Cookie", CommDictAction.strCookie);
////        request.setHeader("Cookie", "UM_distinctid=17392fe4a742c4-02c71cbf04dc48-3962420d-100200-17392fe4a755db");
////        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
////        request.addHeader("User-Agent", "CSII-MOBILE-EBANK MBL.ANDROID");
////        request.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*");
////        request.addHeader("Connection", "Keep-Alive");
//
//        long start = System.currentTimeMillis();
//        //list里存放请求参数，以BasicNameValuePair类型存放，BasicNameValuePair里存放是键值对形式。
//        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
//        BasicNameValuePair basicNameValuePair = new BasicNameValuePair("MobilePhone", "15340458923");
////        BasicNameValuePair basicNameValuePair = new BasicNameValuePair("MobilePhone", "13917466248");
//        list.add(basicNameValuePair);
//        ((HttpPost) request).setEntity(new UrlEncodedFormEntity(list, UTF_8));
//        HttpClient hc= HttpClients.createDefault();
//        HttpResponse response = hc.execute(request);
//        HttpEntity entity = response.getEntity();
//        String msg = EntityUtils.toString(entity,"UTF-8");
//        System.out.println(msg + " "+(System.currentTimeMillis()-start));
//
////        List<String> mobiles = Arrays.asList("18123198332",
////                "13953011016",
////                "15502332885",
////                "13830381391",
////                "15310520238",
////                "17714195257",
////                "15228666205",
////                "17883199819",
////                "18223754545",
////                "15006440081",
////                "18982897618",
////                "18681782902",
////                "15088457335",
////                "18982606365",
////                "13707263906",
////                "18275226607",
////                "15563786065",
////                "18228636473",
////                "19942363763",
////                "13096240103",
////                "13320303677",
////                "13989133181",
////                "15106720521",
////                "17764860032",
////                "13996522035",
////                "13986372082",
////                "19181678886",
////                "15151700467",
////                "15334038856",
////                "15871056878",
////                "13865876550",
////                "13855858160",
////                "13855858160",
////                "15150029677",
////                "15269089698",
////                "16605586988",
////                "15053003628",
////                "18008672667",
////                "17601057376",
////                "13353846266",
////                "18729626079",
////                "18191160703",
////                "13872948572",
////                "13309169775",
////                "18009165791",
////                "15589780699",
////                "13891623901",
////                "13541873898",
////                "13541873898",
////                "15594805161",
////                "13316911955",
////                "13474310521",
////                "18523051430",
////                "13882693923",
////                "15607255272",
////                "18523765712",
////                "18824482376",
////                "15179652489",
////                "18632645679",
////                "13368162345",
////                "13926797476",
////                "18798658807",
////                "13668474667",
////                "18672155866",
////                "18607279923",
////                "18171420810",
////                "15872136112",
////                "15591579979",
////                "15591579979",
////                "13607050617",
////                "18607093236",
////                "18089616663",
////                "18166468462",
////                "15827265541",
////                "18580151425",
////                "13882811982",
////                "19992771082",
////                "13765971012",
////                "13982567530",
////                "13883555406",
////                "18786918425",
////                "18362056979",
////                "18275226607",
////                "13469916991",
////                "15151699375",
////                "16675553505",
////                "13986992552",
////                "13972906959",
////                "13971856089",
////                "13484457512",
////                "17383075762",
////                "15091611667",
////                "18727185081",
////                "18627209665",
////                "13597544488",
////                "18797304865",
////                "15810194082",
////                "18771657253",
////                "13053775656",
////                "18766832611",
////                "18693319925",
////                "18602785193",
////                "13954500122",
////                "13337702079",
////                "13738874887",
////                "13617955910",
////                "13993356489",
////                "15374565168",
////                "18972070961",
////                "13476322458",
////                "18766832611",
////                "15072228600",
////                "18782505388",
////                "16650037422",
////                "13982553719",
////                "17601057376",
////                "18627209665",
////                "13451162091",
////                "15939470825",
////                "18682665467",
////                "13688357583",
////                "18728091625",
////                "13868362029",
////                "15215039617",
////                "13883555406",
////                "18696686456",
////                "18623557187",
////                "15923062146",
////                "18696686456",
////                "15215039617",
////                "15923062146",
////                "13648433443",
////                "13368405052",
////                "18802235159",
////                "15727007206",
////                "15727007206",
////                "18761835177",
////                "15810194082",
////                "15071661021",
////                "15387196330",
////                "13882582955",
////                "15353720796",
////                "13982541355",
////                "18190163109",
////                "15106720521",
////                "13451169207",
////                "15039911608",
////                "13451169207",
////                "13797976615",
////                "13597943416",
////                "15153598879",
////                "13707263115",
////                "18623688585",
////                "18615298377",
////                "13882533009",
////                "15045821234",
////                "13694554422",
////                "15244313487",
////                "18398152431",
////                "13280162377",
////                "13673871811",
////                "15714877537",
////                "18181820005",
////                "13705119957",
////                "13361324088",
////                "15926749992",
////                "18839585366",
////                "13368010989",
////                "13673871811",
////                "15193026348",
////                "13855863135",
////                "15151700467",
////                "13855863135",
////                "15896768196",
////                "18507212541",
////                "15953962511",
////                "18953280303",
////                "18019888691",
////                "18055020967",
////                "15634517010",
////                "18507212541",
////                "13954500122",
////                "18055020967",
////                "15182559583",
////                "13361324088",
////                "15562369994",
////                "18226665711",
////                "18226802871",
////                "18226802871",
////                "13968371392",
////                "18226665711",
////                "13246184333",
////                "13677643433",
////                "18660086803",
////                "18672155866",
////                "15556538046",
////                "15556538046",
////                "15908408962",
////                "15572330382",
////                "13419383117",
////                "15244914523",
////                "15572668133",
////                "13451162091",
////                "18354747721",
////                "13883959702",
////                "13595560037",
////                "13597943416",
////                "13866291789",
////                "13797976615",
////                "15969791133",
////                "15244525606",
////                "18857154119",
////                "18715500631",
////                "18682665467",
////                "18226355860",
////                "18972070961",
////                "15181907766",
////                "15327708106",
////                "15972375202",
////                "18329155436",
////                "13398220980",
////                "17839195446",
////                "18716502676",
////                "18439488288",
////                "13437210745",
////                "17339765755",
////                "15982522008",
////                "18671306008",
////                "18147731995",
////                "15244922377",
////                "15238155347",
////                "18361440076",
////                "15956870393",
////                "15083852585",
////                "15039911608");
//
//        List<String> mobiles = Arrays.asList(
//                "13125001228",
//                "13225001227");
//
////        List<String> mobiles = Arrays.asList(
////                "15173309870",
////                "18301785058");
//
//        AtomicInteger realCnt = new AtomicInteger(0);
//        System.out.println("cpuCores:" + Runtime.getRuntime().availableProcessors());
//        start = System.currentTimeMillis();
//        ExecutorService executor = BusinessThreadPoolExecutorFactory.build(8,20);
//        for(String mobile:mobiles){
//            executor.execute(new BocfTest.CustomerUtil(url, mobile, realCnt));
//        }
//        executor.shutdown();
//        executor.awaitTermination(10, TimeUnit.SECONDS);
//        long end = System.currentTimeMillis();
//        log.info("多线程查询富登接口完成 duration:"+(end - start) + " realCnt:"+realCnt);

//        Map<String,Object> para = new HashMap<>();
//        para.put("MobilePhone","13619920002");
//        para.put("MobilePhone","18516157635");
//        para.put("MobilePhone","13011231457");
//        para.put("MobilePhone","18301785058");
//        para.put("MobilePhone","17764956327");
//        para.put("MobilePhone","18228636473");
//        para.put("MobilePhone","18361440076");
//        para.put("MobilePhone","15039911608");
//        para.put("MobilePhone","18223754545");
//        para.put("MobilePhone","17883199819");
//        para.put("MobilePhone","15228666205");
//        para.put("MobilePhone","17714195257");
//
//
//        com.gtown.cloud.utils.http.HttpResponse resp = HttpUtils.post(url).form(para).call();
//        if (resp.status() == 200) {
//            System.out.println(resp.string());
////            if (JsonUtils.asInt(body.get("code")) == 200) {
////                log.info("微信立减金发放成功:{}", body);
////            } else {
////                log.info("微信立减金发放失败:body={}", body);
////            }
//        }else{
////            log.info("微信立减金发放失败,status={}", resp.status());
//        }
//        String str1 = "{\"_RejCode\":\"10012\",\"_RejMessage\":\"未查询到客户信息！\"}";
//        String str2 = "{\"_RejCode\":\"10012\",\"_RejMessage\":\"未查询到客户信息！\"}";


        String url = "http://192.168.1.180:8880/ib/jd/cancelOrder/";
        Map<String,Object> req = new HashMap<>();
        req.put("orderNo","1234");
        try {
            com.gtown.cloud.utils.http.HttpResponse resp = HttpUtils.post(url).form(req).call();
            if (resp.status() == 200) {
                System.out.println("查询成功 "+resp.string());
            }else{
                System.out.println("查询异常 "+resp.status());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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


