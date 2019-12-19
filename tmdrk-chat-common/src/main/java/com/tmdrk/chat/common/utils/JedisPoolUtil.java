package com.tmdrk.chat.common.utils;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * @ClassName JedisPoolUtil
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/7/12 16:39
 * @Version 1.0
 **/
public class JedisPoolUtil {
    private static final String PROPERTIES_PATH = "config/redis.properties";
    private static JedisPool jedisPool;

    static {
        if (jedisPool == null) {
            try {
                init();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化Jedis连接池
     *
     * @throws IOException
     */
    private static void init() throws IOException {
        URL resource = JedisPoolUtil.class.getClassLoader().getResource(PROPERTIES_PATH);
        if (resource == null) {
            throw new FileNotFoundException("没有找到指定redis的配置文件:" + PROPERTIES_PATH);
        }
        //加载配置文件
        InputStream input = new FileInputStream(resource.getFile());
        Properties p = new Properties();
        p.load(input);
        //开始配置JedisPool
        String host = (p.getProperty("redis.host").trim() == null || "".equals(p.getProperty("redis.host").trim())) ? "localhost" : p.getProperty("redis.host");
        int port = (p.getProperty("redis.port").trim() == null || "".equals(p.getProperty("redis.port").trim())) ? 6379 : Integer.parseInt(p.getProperty("redis.port"));
        String password = trim(p.getProperty("redis.password"));
        int poolTimeOut = (trim(p.getProperty("redis.timeout")) == null || "".equals(trim(p.getProperty("redis.timeout")))) ? 2000 : Integer.parseInt(trim(p.getProperty("redis.timeout")));
        //判断使用默认的配置方式还是采用自定义配置方式,
        boolean isSetDefault = (trim(p.getProperty("defaultSetting")) == null || "".equals(trim(p.getProperty("defaultSetting")))) ? true : Boolean.parseBoolean(p.getProperty("defaultSetting"));
        if (isSetDefault) {
            if(password==null||password.equals("")){
                jedisPool = new JedisPool(new GenericObjectPoolConfig(), host, port, poolTimeOut);
            }else{
                jedisPool = new JedisPool(new GenericObjectPoolConfig(), host, port, poolTimeOut, password);
            }
        } else {
            JedisPoolConfig config = new JedisPoolConfig();
            String blockWhenExhausted = trim(p.getProperty("redis.blockWhenExhausted"));
            if (blockWhenExhausted != null) {
                config.setBlockWhenExhausted(Boolean.parseBoolean(blockWhenExhausted));
            }
            String evictionPolicyClassName = trim(p.getProperty("redis.evictionPolicyClassName"));
            if (evictionPolicyClassName != null) {
                config.setEvictionPolicyClassName(evictionPolicyClassName);
            }
            String jmxEnabled = trim(p.getProperty("redis.jmxEnabled"));
            if (jmxEnabled != null) {
                config.setJmxEnabled(Boolean.parseBoolean(jmxEnabled));
            }
            String lifo = trim(p.getProperty("redis.lifo"));
            if (lifo != null) {
                config.setLifo(Boolean.parseBoolean(lifo));
            }
            String maxIdle = p.getProperty("redis.maxIdle");
            if (maxIdle != null) {
                config.setMaxIdle(Integer.parseInt(maxIdle));
            }
            String maxTotal = p.getProperty("redis.maxTotal");
            if (maxTotal != null) {
                config.setMaxTotal(Integer.parseInt(maxTotal));
            }
            String maxWaitMillis = p.getProperty("redis.maxWaitMillis");
            if (maxWaitMillis != null) {
                config.setMaxWaitMillis(Long.parseLong(maxWaitMillis));
            }
            String minEvictableIdleTimeMillis = p.getProperty("redis.minEvictableIdleTimeMillis");
            if (minEvictableIdleTimeMillis != null) {
                config.setMinEvictableIdleTimeMillis(Long.parseLong(minEvictableIdleTimeMillis));
            }
            String minIdle = p.getProperty("redis.minIdle");
            if (minIdle != null) {
                config.setMinIdle(Integer.parseInt(minIdle));
            }
            String numTestsPerEvictionRun = p.getProperty("redis.numTestsPerEvictionRun");
            if (numTestsPerEvictionRun != null) {
                config.setNumTestsPerEvictionRun(Integer.parseInt(numTestsPerEvictionRun));
            }
            String softMinEvictableIdleTimeMillis = p.getProperty("redis.softMinEvictableIdleTimeMillis");
            if (softMinEvictableIdleTimeMillis != null) {
                config.setSoftMinEvictableIdleTimeMillis(Long.parseLong(softMinEvictableIdleTimeMillis));
            }
            String testOnBorrow = p.getProperty("redis.testOnBorrow");
            if (testOnBorrow != null) {
                config.setTestOnBorrow(Boolean.parseBoolean(testOnBorrow));
            }
            String testWhileIdle = p.getProperty("redis.testWhileIdle");
            if (testWhileIdle != null) {
                config.setTestWhileIdle(Boolean.parseBoolean(testWhileIdle));
            }
            String timeBetweenEvictionRunsMillis = p.getProperty("redis.timeBetweenEvictionRunsMillis");
            if (timeBetweenEvictionRunsMillis != null) {
                config.setTimeBetweenEvictionRunsMillis(Long.parseLong(timeBetweenEvictionRunsMillis));
            }
            if(password==null||password.equals("")) {
                jedisPool = new JedisPool(config, host, port, poolTimeOut);
            }else{
                jedisPool = new JedisPool(config, host, port, poolTimeOut, password);
            }
        }

    }

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

    public static void closeJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    public static String trim(String obj){
        return obj==null?obj:obj.trim();
    }
}
