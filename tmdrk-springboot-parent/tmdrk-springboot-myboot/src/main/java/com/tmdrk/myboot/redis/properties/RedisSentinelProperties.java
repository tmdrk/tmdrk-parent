package com.tmdrk.myboot.redis.properties;

import lombok.Data;
import lombok.ToString;

/**
 * @ClassName RedisSentinelProperties
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/8/25 0:56
 * @Version 1.0
 **/

@Data
@ToString
public class RedisSentinelProperties {
    /**
     * 哨兵master 名称
     */
    private String master;

    /**
     * 哨兵节点
     */
    private String nodes;

    /**
     * 哨兵配置
     */
    private boolean masterOnlyWrite;

    /**
     *
     */
    private int failMax;

}
