package com.lty.cache.redis.config;


import com.lty.cache.redis.IAbsCache;
import com.lty.cache.redis.ICacheService;
import com.lty.cache.redis.impl.CacheService;
import com.lty.cache.redis.impl.RedisCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {


    /**
     *  默认为redis 单节点 缓存
     */
    @Bean
    public IAbsCache iAbsCache(RedisCache redisCache){
        return redisCache;
    }


//    public ICacheService iCacheService(CacheService cacheService){
//        CacheService.cacheService = cacheService;
//        return cacheService;
//    }

}
