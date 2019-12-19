package com.tmdrk.chat.common.utils.reptile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tmdrk.chat.common.entity.es.TestProduct;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.*;

/**
 * @ClassName JDProductDetial
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/17 16:16
 * @Version 1.0
 **/
public class JDProductDetial {
    private static final String HTTPS = "https:";
    private static final int pageSize = 55;
    private static final int pageTotal = 20;
    private static int page = 1;
    private static double total = 0;
    private static double fail = 0;

    public static void main(String[] args) throws Exception {
//        String[] str = new String[]{"100007926790","100009349506","4861276","100007958752","3168337","1560207","100003434175","884718","1234967","100008348542","100008490518","100004770281","56063329311","7224900","100000654489"};
//        for(String s:str){
//            productDetail(s);
//        }

//        productDetail("https://item.jd.com/100009349506.html#comment");

//        String[] strs = new String[]{"日常","手机","电脑","空气净化器","衣服","美食","汽车"};
////        String[] strs = new String[]{"日常"};
//        for(String str:strs){
//            productList(str);
//        }
//        productList("日常");
//        System.out.println("总次数:"+total+" 失败数:"+fail+ "失败率："+100*fail/total+"%");
        List<TestProduct> productList = getProductList();
        for (TestProduct testProduct:productList){
            System.out.println(testProduct.toString());
        }
    }

    /**
     * @Author zhoujie
     * @Description //重置参数
     * @Date 20:40 2019/12/19
     * @Param []
     * @return void
     **/
    public static void resetParamater(){
        page = 1;total = 0;fail = 0;
    }

    /**
     * @Author zhoujie
     * @Description //获取产品列表
     * @Date 20:40 2019/12/19
     * @Param []
     * @return java.util.List<com.tmdrk.chat.common.entity.es.TestProduct>
     **/
    public static List<TestProduct> getProductList(){
        resetParamater();
        List<TestProduct> list = new ArrayList<TestProduct>();
        String[] strs = new String[]{"日常","手机","电脑","空气净化器","衣服","美食","汽车"};
//        String[] strs = new String[]{"日常","手机"};
        for(String str:strs){
            list.addAll(productList(str));
        }
        productList("日常");
        System.out.println("总次数:"+total+" 失败数:"+fail+ "失败率："+100*fail/total+"%");
        return list;
    }
    public static List<TestProduct> getProductList2(){
        List<TestProduct> list = new ArrayList<TestProduct>();
        try {
            TestProduct testProduct = productDetail("https://item.jd.com/100009349506.html#comment");
            list.add(testProduct);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<TestProduct> productList(String str){
        List<TestProduct> list = new ArrayList<>();
        for(int i=0;i<pageTotal;i++){
            String queryList = "https://search.jd.com/Search?keyword="+str+"&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&page="+page+"&s="+(((page/2)+1)*pageSize+1)+"&click=0";
            String html = HttpClientTest.doGet(queryList,HttpClientTest.ISO_8859_1);
            if (html != null && !"".equals(html)) {
                Document doc = Jsoup.parse(html);
                Elements select = doc.select("ul.gl-warp");
                for (Element element:select){
                    for (Element element_li:element.children()) {
                        for (Element element_div: element_li.child(0).children()) {
                            if(element_div.className().equals("p-commit")){
                                String detailUrl = null;
                                try {
                                    detailUrl = HTTPS+element_div.child(0).child(0).attr("href");
                                    TestProduct testProduct = productDetail(detailUrl);
                                    if(testProduct!=null){
                                        list.add(testProduct);
                                    }
                                    total++;
                                } catch (Exception e) {
                                    fail++;
                                    e.printStackTrace();
                                    System.out.println("detailUrl:"+detailUrl+" 调用失败！");
                                }
                            }
                        }
                    }
                }
            }
            page = page +2;
        }
        return list;
    }


    public static TestProduct productDetail(String detailUrl) throws Exception {
        if(!detailUrl.contains("item.jd.com")){
            return null;
        }
        TestProduct testProduct = new TestProduct();

        String y = detailUrl.split("com")[1];
        String[] tm = y.split("html");
        String tmp = tm[0];
        String id = tmp.replace("/","").replace(".","");
        String html = HttpClientTest.doGet(detailUrl);
//        System.out.println("html:"+html);
        if (html != null && !"".equals(html)) {
            String area = "1_72_2799_0";
            String url = "https://c0.3.cn/stock?skuId=%s&cat=%s&venderId=%s&area=%s";

            Document doc = Jsoup.parse(html);
            Elements elements = doc.getElementsByTag("script").eq(0);
            /*取得JS变量数组*/
            String data = elements.first().data();
            ScriptEngineManager sem = new ScriptEngineManager();
            ScriptEngine engine = sem.getEngineByName("javascript");
            engine.eval(data);
            ScriptObjectMirror pageConfig = (ScriptObjectMirror) engine.get("pageConfig");
            ScriptObjectMirror product = (ScriptObjectMirror) pageConfig.to(Map.class).get("product");
            Long skuid = Long.parseLong(product.get("skuid").toString());
            ScriptObjectMirror cat = (ScriptObjectMirror) product.get("cat");
            List<Long> longs = Arrays.asList(cat.to(Long[].class));
            String cats = "";
            for(Long l:longs){
                cats = cats+l+",";
            }
            cats = cats.substring(0,cats.length()-1);

            Integer venderId = (Integer)(product.get("venderId"));
            String fullName = (String)(product.get("name"));

            Elements ul = doc.select("ul.parameter2");

            Element img = doc.getElementById("spec-img");
            String procuct_img_path = HTTPS+img.attr("data-origin");

            String procuctDescription = "";
            String shortName = "";
            int index = 0;
            for (Element element:ul.first().children()) {
                if(index==0){
                    shortName = element.text();
                }
                index++;
                procuctDescription += element.text() + "|";
            }


            String priceHtml = HttpClientTest.doGet(String.format(url, skuid,cats ,venderId ,area));

//            System.out.println("priceHtml:"+priceHtml);
            JSONObject jsonObject = JSONObject.parseObject(priceHtml);
            Map map = JSON.parseObject(jsonObject.get("stock").toString());
            String weightValue = (String) map.get("weightValue");
            int stockState = (Integer) map.get("StockState");
            String stockStateName = (String) map.get("StockStateName");
            Map jdPrice = JSON.parseObject(map.get("jdPrice").toString(), Map.class);
            Double price = Double.parseDouble(jdPrice.get("p").toString());

//            String com = "PUT test_product/_doc/%s\n" +
//                    "{\n" +
//                    "\t\"id\":%d,\n" +
//                    "\t\"procuct_short_name\":\"%s\",\n" +
//                    "\t\"procuct_full_name\":\"%s\",\n" +
//                    "\t\"procuct_sku\":\"%s\",\n" +
//                    "\t\"procuct_price\":%f,\n" +
//                    "\t\"stock_state\":%d,\n" +
//                    "\t\"stock_state_name\":\"%s\",\n" +
//                    "\t\"procuct_weight\":\"%s\",\n" +
//                    "\t\"procuct_img_path\":\"%s\",\n" +
//                    "\t\"on_shelve_status\":%b,\n" +
//                    "\t\"procuct_description\":\"%s\"\n" +
////                    "\t\"create_time\":%t,\n" +
////                    "\t\"update_time\":%t\n" +
//                    "}";
//            String format = String.format(com, id, Long.parseLong(id), shortName, fullName, skuid, price, stockState, stockStateName, weightValue, procuct_img_path, stockState > 0 ? true : false, procuctDescription);
//            System.out.println(format);


            testProduct.setId(Long.parseLong(id));
            testProduct.setProcuctShortName(shortName);
            testProduct.setProcuctFullName(fullName);
            testProduct.setProcuctSku(skuid.toString());
            testProduct.setProcuctPrice(price);
            testProduct.setStockState(stockState);
            testProduct.setStockStateName(stockStateName);
            testProduct.setProcuctWeight(weightValue);
            testProduct.setProcuctImgPath(procuct_img_path);
            testProduct.setOnShelveStatus(stockState > 0 ? true : false);
            testProduct.setProcuctDescription(procuctDescription);
            testProduct.setCreateTime(new Date());
            testProduct.setUpdateTime(new Date());
        }
        return testProduct;
    }

}