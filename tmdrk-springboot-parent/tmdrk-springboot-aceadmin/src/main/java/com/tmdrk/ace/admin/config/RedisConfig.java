package com.tmdrk.ace.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

/**
 * @ClassName RedisConfig
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/8/29 23:10
 * @Version 1.0
 **/
@Configuration
public class RedisConfig {
    @Autowired
    private RedisConnectionFactory connectionFactory;
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//
//        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
//        template.setConnectionFactory(redisConnectionFactory);
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(jackson2JsonRedisSerializer);
////        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
////        new JsonJacksonCodec();
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.setHashValueSerializer(jackson2JsonRedisSerializer);
////        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
//        template.afterPropertiesSet();
//        return template;
//    }
//    @Bean
//    JedisConnectionFactory jedisConnectionFactory() {
//        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
//        jedisConFactory.setHostName("localhost");
//        jedisConFactory.setPort(6379);
//        return jedisConFactory;
//    }

//    @Bean
//    public RedisTemplate<Serializable, ?> redisTemplate() {
//        RedisTemplate<Serializable, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(connectionFactory);
//        // 设置 value 的转化格式和 key 的转化格式
//        redisTemplate.setValueSerializer(new GenericToStringSerializer(Object.class));
//        redisTemplate.setHashValueSerializer(new GenericToStringSerializer(Object.class));
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        return redisTemplate;
//    }

//    @Bean
//    public RedisTemplate<Serializable, ?> redisTemplate() {
//        RedisTemplate<Serializable, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(connectionFactory);
//        // 设置 value 的转化格式和 key 的转化格式
//        redisTemplate.setValueSerializer(new GenericToStringSerializer(Object.class));
//        redisTemplate.setHashValueSerializer(new GenericToStringSerializer(Object.class));
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        return redisTemplate;
//    }

    @Bean
    public RedisTemplate<String, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        GenericJackson2JsonRedisSerializerEx jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializerEx();
        // 设置值（value）的序列化采用FastJsonRedisSerializer。
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        // 设置键（key）的序列化采用StringRedisSerializer。
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
