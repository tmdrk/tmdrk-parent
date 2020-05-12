package com.tmdrk.my.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName MyProperties
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/9 20:59
 * @Version 1.0
 **/
@ConfigurationProperties(
        prefix = "tmdrk.my"
)
public class MyProperties {
    private String prefix;
    private String suffix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
