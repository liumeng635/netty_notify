package com.lty.cache.redis.impl;

import com.lty.cache.DataCache;
import com.lty.cache.redis.IAbsCache;
import com.lty.cache.redis.ICacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * zhouyongbo 2017/10/27
 */
@Service
public class CacheService implements ICacheService {


    IAbsCache iAbsCache;

    @Autowired
    public void setiAbsCache(IAbsCache iAbsCache) {
        this.iAbsCache = iAbsCache;
    }
    @Override
    public void save(DataCache dataCache) {
        iAbsCache.save(dataCache);
    }

    @Override
    public Object get(String key) {
            return iAbsCache.get(key);
    }

    @Override
    public Set<String> like(String key) {
        return iAbsCache.like(key);
    }

    @Override
    public void updateKey(String oldKey, String newKey) {
        iAbsCache.updateKey(oldKey,newKey);
    }
    
	@Override
	public Map<Object, Object> hgetall(String key) {		
		return iAbsCache.hgetall(key);
	}

    @Override
    public Object hashGetKey(String hashKey, String valueKey) {
        return iAbsCache.hashGetKey(hashKey,valueKey);
    }

    @Override
    public List<Object> getList(String key, int index, int end) {
        return iAbsCache.getList(key,index,end);
    }

    @Override
    public void delete(String key) {
        iAbsCache.del(key);
    }

    @Override
    public Long addSetObj(String key, Object obj) {
        return iAbsCache.addSetObj(key, obj);
    }

    @Override
    public void saveHashValue(String haskKey, DataCache dataCache) {
        iAbsCache.saveHashValue(haskKey,dataCache);
    }


}
