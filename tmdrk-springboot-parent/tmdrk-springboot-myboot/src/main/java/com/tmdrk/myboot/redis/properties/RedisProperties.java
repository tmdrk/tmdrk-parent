package com.tmdrk.myboot.redis.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName RedisProperties
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/8/25 0:54
 * @Version 1.0
 **/
@ConfigurationProperties(prefix = "spring.redis", ignoreUnknownFields = false)
@Data
@ToString
public class RedisProperties {
    private int database;

    /**
     * 等待节点回复命令的时间。该时间从命令发送成功时开始计时
     */
    private int timeout;

    private String password;

    private String mode;

    /**
     * 池配置
     */
    private RedisPoolProperties pool;

    /**
     * 单机信息配置
     */
    private RedisSingleProperties single;

    /**
     * 集群 信息配置
     */
    private RedisClusterProperties cluster;

    /**
     * 哨兵配置
     */
    private RedisSentinelProperties sentinel;

}
