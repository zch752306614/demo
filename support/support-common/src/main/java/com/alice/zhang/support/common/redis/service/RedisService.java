package com.alice.zhang.support.common.redis.service;

import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Description redis工具
 * @Author ZhangChenhuang
 * @ClassName RedisService
 * @DateTime 2022/10/20 0020 15:42
 */
@Service
public class RedisService {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 字符串操作：添加到队列
     * @param key
     * @param value
     */
    public Long pushToList(String key, String value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 新增或修改
     * @param key
     * @param value
     */
    public void putValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 字符串操作：新增或修改
     * @param key
     * @param value
     * @param time  有限时间，单位为秒
     */
    public void putValue(String key, String value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 字符串操作：取值
     * @param key
     */
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置有效时间
     * @param key
     * @param time
     */
    public void expire(String key, long time) {
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 删除key
     * @param key
     */
    public void remove(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 批量移除
     * @param keys
     */
    public void removeByKeys(String keys) {
        Set keysSet = redisTemplate.keys(keys);
        Assert.notNull(keysSet);
        redisTemplate.delete(keysSet);
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashGet
     * @param key  键 不能为null
     * @param item 项 不能为null
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash表中的值
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return double
     */
    public double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return double
     */
    public double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    /**
     * reids主键自增
     * @param key 主键值
     * @return long 类型主键
     */
    public Long incrId(final String key) {
        return (Long) redisTemplate.execute((RedisCallback<Long>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] keys = serializer.serialize("sequence:id_" + key);
            Assert.notNull(keys);
            // 主键35开头
            return Long.valueOf(connection.incr(keys).toString());
        });
    }

    /**
     * reids主键自增-不含35前缀
     * @param key 主键值
     * @return long 类型主键
     */
    public Long incrIdWithoutPre(String key) {
        return (Long) redisTemplate.execute((RedisCallback<Long>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] keys = serializer.serialize("sequence:id_" + key);
            Assert.notNull(keys);
            return Long.valueOf(connection.incr(keys).toString());
        });
    }

    /**
     * 若不存在则插入
     * @param key
     * @param value
     * @param seconds
     * @return 插入成功返回true
     */
    public boolean setnxAndExpire(final String key, String value, long seconds) {
        Boolean result = redisTemplate.opsForValue().setIfAbsent(key, value, seconds, TimeUnit.SECONDS);
        return null != result && result;
    }

}
