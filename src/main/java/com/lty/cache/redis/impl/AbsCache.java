package com.lty.cache.redis.impl;


import com.lty.cache.DataCache;
import com.lty.cache.redis.IAbsCache;
import java.util.Set;

/**
 * zhouyongbo 2017/10/27
 * 缓存抽象类
 */
public abstract class AbsCache implements IAbsCache {

    //--------------保存-----------
    abstract  void doSave(DataCache dataCache);

    @Override
    public void save(DataCache dataCache) {
        doSave(dataCache);
    }


    //--------------获取--------------
    abstract Object doGet(String key);

    @Override
    public Object get(String key) {
        return doGet(key);
    }


    //--------------模糊查询-------------
    abstract Set<String>  doLike(String key);
    @Override
    public Set<String> like(String key) {
        return doLike(key);
    }
}
