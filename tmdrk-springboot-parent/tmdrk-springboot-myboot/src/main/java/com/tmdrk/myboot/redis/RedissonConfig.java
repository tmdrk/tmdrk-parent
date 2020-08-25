package com.tmdrk.myboot.redis;

import org.springframework.context.annotation.Configuration;

/**
 * @ClassName RedissonConfig
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/8/25 0:16
 * @Version 1.0
 **/
@Configuration
public class RedissonConfig {
//    @Autowired
//    private RedisProperties redisProperties;
//
//    @Bean
//    public RedissonClient redissonClient(){
//        Config config = new Config();
//        String redisUrl = String.format("redis://%s:%s",redisProperties.getHost()+"",redisProperties.getPort()+"");
//        config.useSingleServer().setAddress(redisUrl).setPassword(redisProperties.getPassword());
//        config.useSingleServer().setDatabase(3);
//        return Redisson.create(config);
//    }
}
