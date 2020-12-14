package net.zhongli.tech.luwu.admin.common.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 注入 redis 实例
 * @author lk
 * @create 2020/12/14 11:27 上午
 **/
@Component
public class RedisCacheTransfer {

    @Resource
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        MybatisRedisCache.setRedisTemplate(redisTemplate);
    }
}
