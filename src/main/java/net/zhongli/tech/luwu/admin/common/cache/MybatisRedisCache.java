package net.zhongli.tech.luwu.admin.common.cache;


import lombok.extern.slf4j.Slf4j;
import net.zhongli.tech.luwu.admin.common.constants.RedisKeyConstant;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * mybatis 使用二级缓存
 *
 *
 * @author lk
 * @create 2020-12-14 11:21 上午
 **/
@Slf4j
public class MybatisRedisCache implements Cache {

    private static RedisTemplate<String, Object> redisTemplate;

    // redis 过期时间
    private static final long EXPIRE_TIME_IN_MINUTES = 30;

    private final String id;

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public MybatisRedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }

    protected String getKey(Object key) {
        String newKey = String.format(RedisKeyConstant.MYBATIS_REDIS_CACHE, getId(), key.toString());
        return newKey;
    }

    protected String clearKeys() {
        String newKey = String.format(RedisKeyConstant.MYBATIS_REDIS_CACHE, getId(), "*");
        return newKey;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        try {
            ValueOperations opsForValue = redisTemplate.opsForValue();
            opsForValue.set(getKey(key), value, EXPIRE_TIME_IN_MINUTES, TimeUnit.MINUTES);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public Object getObject(Object key) {
        ValueOperations opsForValue = redisTemplate.opsForValue();
        return opsForValue.get(getKey(key));

    }

    @Override
    public Object removeObject(Object key) {
        redisTemplate.delete(getKey(key));
        return null;
    }

    @Override
    public void clear() {
        Set<String> keySet = redisTemplate.keys(clearKeys());
        if (null != keySet && !keySet.isEmpty()) {
            for (String key : keySet) {
                redisTemplate.delete(key);
            }
        }
    }

    @Override
    public int getSize() {
        int result = 0;
        Set<String> keySet = redisTemplate.keys(clearKeys());
        if (null != keySet && !keySet.isEmpty()) {
            result = keySet.size();
        }
        return result;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }

    public static void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        MybatisRedisCache.redisTemplate = redisTemplate;
    }

}
