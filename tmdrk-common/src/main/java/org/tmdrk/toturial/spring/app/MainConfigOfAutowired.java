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
import org.elasticsearch.client.Node;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.tmdrk.toturial.spring.dao.IndexDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName MainConfigOfAutowired
 * @Description 自动装配
 * 1 @Autowired
 *      1) 默认按照类型去找相应的组件
 *      2) 如果找到多个相同类型的组件，再将属性的名称作为组件的id去容器中查找
 *      3) @Qualifier("indexDao1") //多个bean时，指定某个名称
 *      4) 自动装配默认一定要将属性赋好值，没有就报错,@Autowired(required=false)则不会报错，但属性可能为null
 *      5) @Primary 默认指定首选的bean，也可以继续使用@Qualifier指定bean
 * 2 @Resource
 *      JSR250规范
 *      默认装配组件名称bean,@Resource(name="indexDao1")指定名称
 * 2 @Inject
 *      JSR330规范
 *      与@Autowired一致，没有required=false功能
 * @Author zhoujie
 * @Date 2020/1/13 1:35
 * @Version 1.0
 **/
@Slf4j
@Configuration
@ComponentScan(value={"org.tmdrk.toturial.spring.service","org.tmdrk.toturial.spring.dao","org.tmdrk.toturial.spring.listenner","org.tmdrk.toturial.spring.es.service"})
public class MainConfigOfAutowired {

    @Bean(name="indexDao1")
    @Primary
//    @Bean
    public IndexDao indexDao(){
        IndexDao indexDao = new IndexDao();
        indexDao.setLable("1");
        return indexDao;
    }

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
