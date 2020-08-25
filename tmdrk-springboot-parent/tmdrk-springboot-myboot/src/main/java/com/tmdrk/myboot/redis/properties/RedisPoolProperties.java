package com.tmdrk.myboot.redis.properties;

import lombok.Data;
import lombok.ToString;

/**
 * @ClassName RedisPoolProperties
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/8/25 0:54
 * @Version 1.0
 **/

@Data
@ToString
public class RedisPoolProperties {
    private int maxIdle;

    private int minIdle;

    private int maxActive;

    private int maxWait;

    private int connTimeout;

    private int soTimeout;

    /**
     * 池大小
     */
    private  int size;
}
