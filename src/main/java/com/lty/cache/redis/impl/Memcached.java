package com.lty.cache.redis.impl;

import com.lty.cache.DataCache;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class Memcached extends AbsCache {

    @Override
    void doSave(DataCache dataCache) {

    }

    @Override
    String doGet(String key) {
        return null;
    }

    @Override
    Set<String> doLike(String key) {
        return null;
    }


    @Override
    public void saveHashValue(String haskKey, DataCache dataCache) {

    }

    @Override
    public void updateKey(String oldKey, String newKey) {

    }

    @Override
    public Object get(String key) {
        return null;
    }

    @Override
    public void del(String key) {
        return;
    }

	@Override
	public Map<Object, Object> hgetall(String key) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public List<Object> getList(String key, int index, int end) {
        return null;
    }

    @Override
    public Long addSetObj(String key, Object objs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object hashGetKey(String hashKey, String valueKey) {
        return null;
    }
}
