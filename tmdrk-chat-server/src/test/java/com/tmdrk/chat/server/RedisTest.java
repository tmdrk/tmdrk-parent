package com.tmdrk.chat.server;

import com.tmdrk.chat.common.entity.User;
import com.tmdrk.chat.common.utils.JedisPoolUtil;
import com.tmdrk.chat.common.utils.JedisUtil;
import com.tmdrk.chat.common.utils.RedisUtil;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.BitOP;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisUtil redisUtil;

    private void init(StackTraceElement stackTraceElement){
        String method = stackTraceElement.getMethodName();
        System.out.println(method+"测试开始");
    }


    private void print(List<Object> list){
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        for (Object obj:list){
            System.out.println(obj.toString());
        }
    }

    @Test
    public void simpleTest() {
        init(Thread.currentThread().getStackTrace()[1]);
        redisUtil.set("my:name",111);
        redisUtil.incr("my:name",3);
        System.out.println(redisUtil.setnx("my:age",18));
        System.out.println(redisUtil.setnx("my:age",17));
        System.out.println(redisUtil.get("my:age"));
        System.out.println(redisUtil.setnx("my:weight",180,10));
        System.out.println(redisUtil.setnx("my:weight",170,10));
        System.out.println(redisUtil.get("my:weight"));
        System.out.println("test:name="+redisUtil.get("my:name"));


    }

    @Test
    public void listTest() {
        init(Thread.currentThread().getStackTrace()[1]);
        String test1 = "my:list:test1";
        redisUtil.del(test1);
        List<Object> users = new ArrayList<>();
        for(int i=0;i<20;i++){
            User user = new User();
            user.setId(i);
            user.setName("name_"+i);
            users.add(user);
        }
        System.out.println(redisUtil.lSet(test1,users));

        System.out.println(redisUtil.lGetListSize(test1));
        List<Object> objects = redisUtil.lGet(test1, 0, 5);
        objects.forEach(user -> System.out.println("objects.size:"+objects.size()+" contents:"+user.toString()));
    }

    @Test
    public void bitMapTest() {
        init(Thread.currentThread().getStackTrace()[1]);
        String key1 = "my:bit:test1";
        String key2 = "my:bit:test2";
        String key3 = "my:bit:test3";
        JedisUtil.del(key1);
        JedisUtil.del(key2);
        JedisUtil.del(key3);
        JedisUtil.setBit(key1,1,true);
        JedisUtil.setBit(key1,4,true);
        JedisUtil.setBit(key1,8,true);
        for(int i=0;i<=10;i++){
            System.out.println(key1+":"+i+"="+JedisUtil.getBit(key1,i));
        }
        JedisUtil.setBit(key2,2,true);
        JedisUtil.setBit(key2,5,true);
        JedisUtil.setBit(key2,8,true);
        for(int i=0;i<=10;i++){
            System.out.println(key2+":"+i+"="+JedisUtil.getBit(key2,i));
        }
        System.out.println(JedisUtil.bitCount(key1));
        System.out.println(JedisUtil.bitCount(key2));
        System.out.println(JedisUtil.bitpos(key1,true));
        System.out.println(JedisUtil.bitpos(key1,false));

        System.out.println(JedisUtil.bitOp(BitOP.AND,key3,new String[]{key1,key2}));
        for(int i=0;i<=10;i++){
            System.out.println(key3+":"+i+"="+JedisUtil.getBit(key3,i));
        }
        System.out.println(JedisUtil.bitCount(key3));
    }

    @Test
    public void transaction() {
        init(Thread.currentThread().getStackTrace()[1]);
        Jedis jedis = JedisPoolUtil.getJedis();

        String key1 = "my:tran:test1";
        String key2 = "my:tran:test2";
        JedisUtil.set(key1,"10");
        JedisUtil.set(key2,"20");
        Transaction tran = jedis.multi();
        tran.incr(key1);
        tran.incr(key2);
        List<Object> list1 = tran.exec();
        print(list1);

        Transaction tran2 = jedis.multi();
        tran2.incrBy(key1,1);
        tran2.incrBy(key2,2);
        String discard = tran2.discard();
        System.out.println("discard:"+discard);

        jedis.watch(key1);
        Transaction tran3 = jedis.multi();
        tran3.incrBy(key1,5);
        tran3.incrBy(key2,5);
        List<Object> list3 = tran3.exec();
        if(CollectionUtils.isEmpty(list3)){
            System.out.println("本次事物执行失败");
        }
        print(list3);

        System.out.println("key1:"+redisUtil.get(key1));
        System.out.println("key2:"+redisUtil.get(key2));
        jedis.close();
    }

    /**
     * @Author zhoujie
     * @Description
     *      lua脚本对redis集群的操作，要求传入的key都在同一个槽，否则报错，解决办法在key前加“{分槽内容}”
     *      keySlot算法中，如果key包含{}，就会使用第一个{}内部的字符串作为hash key，
     *      这样就可以保证拥有同样{}内部字符串的key就会拥有相同slot。
     * @Date 1:28 2019/12/14
     * @Param []
     * @return void
     **/
    @Test
    public void eval() {
        Jedis jedis = JedisPoolUtil.getJedis();
        try {
            String lua = "local num = redis.call('incr', KEYS[1])\n" +
                    "if tonumber(num) == 1 then\n" +
                    "\tredis.call('expire', KEYS[1], ARGV[1])\n" +
                    "\treturn 1\n" +
                    "elseif tonumber(num) > tonumber(ARGV[2]) then\n" +
                    "\treturn 0\n" +
                    "else \n" +
                    "\treturn 1\n" +
                    "end\n";
            Object result = jedis.evalsha(jedis.scriptLoad(lua), Arrays.asList("localhost"), Arrays.asList("20", "2"));
            System.out.println(JedisUtil.get("localhost"));
//            Object result2 = jedis.eval(lua, Arrays.asList("localhost"), Arrays.asList("20", "2"));
//            System.color.println(JedisUtil.get("localhost"));
            System.out.println("result:"+result);
//            System.color.println("result2:"+result2);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis != null){
                try {
                    jedis.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void publish() {
        Jedis jedis = JedisPoolUtil.getJedis();
        System.out.println(jedis.publish("my_channel", "你好呀"));
        System.out.println(jedis.publish("you_channel", "我很好"));
    }

    @Test
    public void hyperLogLog() {
        String key1 = "mysql";
        String key2 = "oracle";
        String key3 = "postgresql";
        String key4 = "sqlserver";
        String key5 = "database";
        String key6 = "database1";
        long result = JedisUtil.pfadd(key1, "1", "1", "3", "4", "5");
        System.out.println("result:"+result);
        System.out.println(JedisUtil.pfcount(key5));

//        int total = 10000;
//        int succ = 0;
//        int toa = 0;
//        for(int i=0;i<total;i++){
//            long result1 = JedisUtil.pfadd(key4,"test"+i);
//            toa++;
//            if(result1==1){
//                succ++;
//            }else {
//                System.color.println(result1+"|"+i);
//            }
//        }
//        System.color.println("总操作:"+toa+" 实际存入:"+succ+" hyperLogLog计数:"+JedisUtil.pfcount(key4));

        System.out.println(JedisUtil.pfmerge(key6,key4,key3));
        System.out.println(JedisUtil.pfcount(key6));
    }
}
