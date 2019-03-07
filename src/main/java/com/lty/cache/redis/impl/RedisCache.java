package com.lty.cache.redis.impl;

import com.lty.cache.DataCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisCache extends AbsCache {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

//    @Autowired
//    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//        redisTemplate.setKeySerializer(new StringRedisSerializer() );
//        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(Object.class));
//    }

    @Override
    void doSave(DataCache dataCache) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        if (dataCache.getTimer()<=0){
            valueOperations.set(dataCache.getKey(),dataCache.getData());
        }else {
            valueOperations.set(dataCache.getKey(),dataCache.getData(),dataCache.getTimer(), TimeUnit.SECONDS);
        }
    }

    @Override
    public void saveHashValue(String hashKey, DataCache dataCache) {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(hashKey,dataCache.getKey(),dataCache.getData());
    }

    @Override
    Object doGet(String key) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        Object o = valueOperations.get(key);
        if (o == null ){
            return null;
        }
        return o;
    }

    @Override
     Set<String> doLike(String key) {
        Set<String> keys = redisTemplate.keys(key);
        return keys;
    }



    @Override
    public void updateKey(String oldKey, String newKey) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        Object o = valueOperations.get(oldKey);
        redisTemplate.delete(oldKey);
        valueOperations.set(newKey,o);
    }

    @Override
    public Object get(String key) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
         return valueOperations.get(key);
    }

    @Override
    public void del(String key) {
        redisTemplate.delete(key);
    }
    
    @Override
    public Map<Object,Object> hgetall(String key){
    	Map<Object,Object> map = redisTemplate.opsForHash().entries(key);
    	return map;    	
    }

    @Override
    public List<Object> getList(String key, int index, int end) {
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
        return listOperations.range(key, index, end);
    }

    @Override
    public Long addSetObj(String key, Object objs) {
        SetOperations<String, Object> stringObjectSetOperations = redisTemplate.opsForSet();
        Boolean member = stringObjectSetOperations.isMember(key, objs);
        if (member){
            return 0L;
        }
        return stringObjectSetOperations.add(key, objs);
    }

    @Override
    public Object hashGetKey(String hashKey, String valueKey) {
       return redisTemplate.opsForHash().entries(hashKey).get(valueKey);
    }

}
