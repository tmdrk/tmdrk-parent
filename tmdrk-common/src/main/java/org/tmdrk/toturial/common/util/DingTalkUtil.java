package org.tmdrk.toturial.common.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * DingTalkUtil
 *
 * @author Jie.Zhou
 * @date 2021/1/11 11:06
 */
@Slf4j
public class DingTalkUtil {
    public static final String dingApiUrl = "https://oapi.dingtalk.com/robot/send?access_token=9aecc0b52ba728b91f5284868760a65dda6f05f02cc083c842ddb0fe1f06c85a";
    public static void main(String[] args) {
        Map<String, String> content = new HashMap<>();
        for(int i=1;i<3000;i++){
            content.put("PB010437000"+i,"自动下架成功：SUCCESS");
        }
        content.put("PB0104360000","自动下架失败：商品有正在进行中的活动，请关闭相关活动再下架商品,请关闭相关活动再下架商品,请关闭相关活动再下架商品,请关闭相关活动再下架商品");
        String p = content.entrySet().stream().map((e) -> String.format("%s %s", e.getKey(), e.getValue())).collect(Collectors.joining("\n"));
        Map<String,Object> contentMap = new HashMap();
        contentMap.put("content", "refrain 我是钉钉小可爱\n"+p);

        HashMap param = new HashMap();
        param.put("msgtype", "text");
        param.put("text", contentMap);

        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(dingApiUrl);
        httpPost.addHeader("Content-Type", "application/json; charset=utf-8");
        String resultParam = JSONObject.toJSONString(param);
        StringEntity se = new StringEntity(resultParam, "utf-8");
        httpPost.setEntity(se);

        try {
            HttpResponse response = httpClient.execute(httpPost);
            log.info("返回值是:{}", response);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(response.getEntity(), "utf-8");
                log.info("错误信息是:{}", result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
