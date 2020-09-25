package org.tmdrk.toturial.spring.app;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Objects;

/**
 * MainConfigOfEs
 * es搜索配置类
 * @author Jie.Zhou
 * @date 2020/9/25 10:38
 */
@Slf4j
@Configuration
@ComponentScan(value={"org.tmdrk.toturial.spring.es.service"})
public class MainConfigOfEs {

    //使用冒号隔开ip和端口
    private String[] address = {"es-cn-v0h1m0am700174ayf.public.elasticsearch.aliyuncs.com:9200"};
    private String userName = "elastic";
    private String password = "Gtown123$%^";

    private static final int ADDRESS_LENGTH = 2;
    private static final String HTTP_SCHEME = "http";
    final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    @Bean
    public RestHighLevelClient restHighLevelClient(){
        HttpHost[] hosts = Arrays.stream(address)
                .map(this::makeHttpHost)
                .filter(Objects::nonNull)
                .toArray(HttpHost[]::new);
        log.debug("hosts:{}", Arrays.toString(hosts));
        RestClientBuilder restClientBuilder = RestClient.builder(hosts);

        //设置连接超时
        restClientBuilder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder builder) {
                return builder.setConnectTimeout(5000).setSocketTimeout(60000);
            }
        });

        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));
        restClientBuilder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
        });

        return new RestHighLevelClient(restClientBuilder);
    }

    /**
     * 处理请求地址
     * @param s
     * @return HttpHost
     */
    private HttpHost makeHttpHost(String s) {
        assert StrUtil.isNotBlank(s);
        String[] address = s.split(":");
        if (address.length == ADDRESS_LENGTH) {
            String ip = address[0];
            int port = Integer.parseInt(address[1]);
            return new HttpHost(ip, port, HTTP_SCHEME);
        } else {
            return null;
        }
    }
}
