package com.tmdrk.myboot.redis.service;


//import com.gtown.cloud.commons.enums.Code;
import com.tmdrk.myboot.redis.BusinessException;
import com.tmdrk.myboot.redis.Result;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.ReturnType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Redis incr service.
 */
public class RedisIncrServiceImpl implements IRedisIncrService {

    private RedisConnectionFactory redisConnectionFactory;

    /**
     * Instantiates a new Redis incr service.
     *
     * @param redisConnectionFactory the redis connection factory
     */
    public RedisIncrServiceImpl(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    private String hsetScript = "local res = {0}\n" +
            "local rollBackIndex = 0\n" +
            "local itemCount = ARGV[1]\n" +
            "for i = 1 , itemCount , 1 do\n" +
            "  local id = tonumber(ARGV[1 + i])\n" +
            "  local newTotal = tonumber(ARGV[1 + itemCount + i])\n" +
            "  local totalKey = string.format(\"%d-total\", id)\n" +
            "  local usedKey = string.format(\"%d-used\", id)\n" +
            "  local used = redis.call('HGET', KEYS[1], usedKey) or 0\n" +
            "  if newTotal >= tonumber(used) then\n" +
            "    local nowTotal = redis.call('HGET', KEYS[1], totalKey)\n" +
            "    local addNum = newTotal - tonumber(nowTotal)\n" +
            "    res[1 + i] = addNum\n" +
            "    if addNum ~= 0 then\n" +
            "      redis.call('HINCRBY', KEYS[1], id, addNum)\n" +
            "      redis.call('HINCRBY', KEYS[1], totalKey, addNum)\n" +
            "    end\n" +
            "  end\n" +
            "  if newTotal < tonumber(used) then\n" +
            "    rollBackIndex = i - 1\n" +
            "    res[1] = -1 - rollBackIndex\n" +
            "    break\n" +
            "  end\n" +
            "end\n" +
            "if rollBackIndex > 0 then\n" +
            "  for i = 1 , rollBackIndex , 1 do\n" +
            "    local id = tonumber(ARGV[1 + i])\n" +
            "    local totalKey = string.format(\"%d-total\", id)\n" +
            "    local usedKey = string.format(\"%d-used\", id)\n" +
            "    local rollBackNum = 0 - res[1 + i]\n" +
            "    if rollBackNum ~= 0 then\n" +
            "      redis.call('HINCRBY', KEYS[1], id, rollBackNum)\n" +
            "      redis.call('HINCRBY', KEYS[1], totalKey, rollBackNum)\n" +
            "    end\n" +
            "  end\n" +
            "end\n" +
            "return res";

    private String hscript = "local res = {0}\n" +
            "local breakIndex = 0\n" +
            "local argvLength = ARGV[1]\n" +
            "local expire = tonumber(ARGV[2])\n" +
            "for i = 1 , argvLength , 1 do\n" +
            "    res[1+i] = redis.call('HINCRBY', KEYS[1], ARGV[2 + i], ARGV[2 + argvLength + i])\n" +
            "    if res[1+i] < 0 then\n" +
            "        breakIndex = i\n" +
            "        break\n" +
            "    end\n" +
            "end\n" +
            "if breakIndex > 0 then\n" +
            "    res[1] = 0 - breakIndex\n" +
            "    for i = 1 , breakIndex , 1 do\n" +
            "        local addNum = 0 - tonumber(ARGV[2 + argvLength + i])\n" +
            "        redis.call('HINCRBY', KEYS[1], ARGV[2 + i], addNum)\n" +
            "    end\n" +
            "end\n" +
            "if res[1] == 0 then\n" +
            "    if expire < 0 then\n" +
            "        redis.call('PERSIST', KEYS[1])\n" +
            "    elseif expire > 0 then\n" +
            "        redis.call('EXPIRE', KEYS[1], expire)\n" +
            "    end\n" +
            "end\n" +
            "return res";

    private String script = "local res = {0}\n" +
            "local breakIndex = 0\n" +
            "local argvLength = table.getn(ARGV) / 2\n" +
            "for i = 1 , argvLength , 1 do\n" +
            "    res[1+i] = redis.call('INCRBY', KEYS[i], ARGV[i])\n" +
            "    if res[1+i] < 0 then\n" +
            "        breakIndex = i\n" +
            "        break\n" +
            "    end\n" +
            "end\n" +
            "if breakIndex > 0 then\n" +
            "    res[1] = 0 - breakIndex\n" +
            "    for i = 1 , breakIndex , 1 do\n" +
            "        redis.call('DECRBY', KEYS[i], ARGV[i])\n" +
            "    end\n" +
            "end\n" +
            "if res[1] == 0 then\n" +
            "    for i = 1 , argvLength , 1 do\n" +
            "        local expire = tonumber(ARGV[argvLength + i])\n" +
            "        if expire < 0 then\n" +
            "            redis.call('PERSIST', KEYS[i])\n" +
            "        elseif expire > 0 then\n" +
            "            redis.call('EXPIRE', KEYS[i], expire)\n" +
            "        end\n" +
            "    end\n" +
            "end\n" +
            "return res";


    @Override
    public Result<Map<String, Long>> hStockSet(String key, Map<String, Long> keyArgs) {
        RedisConnection connection = redisConnectionFactory.getConnection();
        ArrayList<String> list = new ArrayList<>(keyArgs.keySet());
        int size = list.size() * 2;
        byte[][] keys = new byte[2 + size][];
        keys[0] = key.getBytes();
        keys[1] = String.valueOf(list.size()).getBytes();
        for (int i = 0; i < size; i++) {
            keys[2 + i] = list.get(i).getBytes();
            keys[2 + size + i] = keyArgs.get(list.get(i)).toString().getBytes();
        }
        List<Long> eval = connection.eval(hsetScript.getBytes(), ReturnType.fromJavaType(List.class), 1, keys);
        connection.close();
        assert eval != null;
        Long result = eval.get(0);
        if (result < 0) {
            throw new BusinessException(1, "总库存更新出错，新设置数量应该大于已使用数量");
        }
        for (int i = 0; i < size; i++) {
            keyArgs.put(list.get(i), eval.get(i + 1));
        }
        return Result.success(keyArgs);
    }

    private List<Long> hincr(String hash, Integer expire, String[] hashKey, String[] incr) {
        RedisConnection connection = redisConnectionFactory.getConnection();
        byte[][] keyArgs = new byte[3 + hashKey.length + incr.length][];
        // key
        keyArgs[0] = hash.getBytes();
        // hash key长度
        keyArgs[1] = String.valueOf(hashKey.length).getBytes();
        // 过期时间
        keyArgs[2] = String.valueOf(expire).getBytes();
        for (int i = 0; i < hashKey.length; i++) {
            keyArgs[3 + i] = hashKey[i].getBytes();
            keyArgs[3 + hashKey.length + i] = incr[i].getBytes();
        }
        List<Long> eval = connection.eval(hscript.getBytes(), ReturnType.fromJavaType(List.class), 1, keyArgs);
        connection.close();
        return eval;
    }

    /**
     * Hash key incr
     * 批量HashKey增量操作，保证操作后数量>=0
     * 如出现<0情况本次操作失败已操作Key回滚
     * 正数为增加负数为扣减
     *
     * @param key     the key
     * @param keyArgs the params
     * @return the result
     */
    @Override
    public Result<Map<String, Long>> hincr(String key, Integer expire, Map<String, Long> keyArgs) {
        String[] hashKey = new String[keyArgs.size()];
        String[] incr = new String[keyArgs.size()];
        int i = 0;
        for (Map.Entry<String, Long> entry : keyArgs.entrySet()) {
            hashKey[i] = entry.getKey();
            incr[i] = String.valueOf(entry.getValue());
            i++;
        }
        List<Long> hincr = hincr(key, expire, hashKey, incr);
        Long aLong = hincr.get(0);
        if (aLong < 0) {
            // 操作失败
            i = -1 - aLong.intValue();
            return Result.failure(5000, hashKey[i] + " 数量不足");
        }
        Map<String, Long> map = new HashMap<>();
        for (int j = 0; j < hashKey.length; j++) {
            map.put(hashKey[j], hincr.get(1 + j));
        }
        return Result.success(map);
    }

    @Override
    public Result<Map<String, Long>> hincr(String key, Map<String, Long> keyArgs) {
        return hincr(key, 0, keyArgs);
    }

    /**
     * Incr result.
     * 批量Key增量操作，保证操作后数量>=0
     * 如出现<0情况本次操作失败已操作Key回滚
     * 正数为增加负数为扣减
     * ***注意：批量操作的Key必须在同一节点
     *
     * @return the result
     */
    @Override
    public Result<Map<String, Long>> incr(String[] hashKey, Long[] incr, Integer[] expire) {
        RedisConnection connection = redisConnectionFactory.getConnection();
        byte[][] keyArgs = new byte[hashKey.length + incr.length + expire.length][];
        for (int i = 0; i < hashKey.length; i++) {
            keyArgs[i] = hashKey[i].getBytes();
            keyArgs[hashKey.length + i] = String.valueOf(incr[i]).getBytes();
            keyArgs[hashKey.length + incr.length + i] = String.valueOf(expire[i]).getBytes();
        }
        List<Long> eval = connection.eval(script.getBytes(), ReturnType.fromJavaType(List.class), hashKey.length, keyArgs);
        connection.close();
        Long aLong = eval.get(0);
        if (aLong < 0) {
            // 操作失败
            int i = -1 - aLong.intValue();
            return Result.failure(5000, hashKey[i] + " 数量不足");
        }
        Map<String, Long> map = new HashMap<>();
        for (int j = 0; j < hashKey.length; j++) {
            map.put(hashKey[j], eval.get(1 + j));
        }
        return Result.success(map);
    }

    @Override
    public Result<Map<String, Long>> incr(Map<String, Long> keyArgs) {
        String[] hashKey = new String[keyArgs.size()];
        Long[] incr = new Long[keyArgs.size()];
        Integer[] expire = new Integer[keyArgs.size()];
        int i = 0;
        for (Map.Entry<String, Long> entry : keyArgs.entrySet()) {
            hashKey[i] = entry.getKey();
            incr[i] = entry.getValue();
            expire[i] = 0;
            i++;
        }
        return incr(hashKey, incr, expire);
    }

    @Override
    public Result<Map<String, Long>> getIncr(String[] keys) {
        Map<String, Long> map = new HashMap<>(keys.length);
        RedisConnection connection = redisConnectionFactory.getConnection();
        for (int i = 0; i < keys.length; i++) {
            byte[] bytes = connection.get(keys[i].getBytes());
            if (bytes == null) {
                map.put(keys[i], 0L);
            } else {
                map.put(keys[i], Long.parseLong(new String(bytes)));
            }
        }
        connection.close();
        return Result.success(map);
    }

    @Override
    public Result<Map<String, Long>> getHincr(String key, String[] hashKeys) {
        Map<String, Long> map = new HashMap<>(hashKeys.length);
        RedisConnection connection = redisConnectionFactory.getConnection();
        for (int i = 0; i < hashKeys.length; i++) {
            byte[] bytes = connection.hashCommands().hGet(key.getBytes(), hashKeys[i].getBytes());
            if (bytes == null) {
                map.put(hashKeys[i], 0L);
            } else {
                map.put(hashKeys[i], Long.parseLong(new String(bytes)));
            }
        }
        connection.close();
        return Result.success(map);
    }
}