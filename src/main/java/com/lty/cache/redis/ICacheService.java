package com.lty.cache.redis;


import com.lty.cache.DataCache;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 缓存接口  使用这层将缓存进行隔离使用  方便维护与扩展
 * @author zhouyongbo 2017/12/29
 */
public interface ICacheService {

    /**
     * 保存数据 data
     * @param dataCache
     */
    void save(DataCache dataCache);


    /**
     * 根据Key获取值
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 根据key来进行模糊查询
     * @param key
     * @return
     */
    Set<String> like(String key);

    /**
     * 修改key
     * @param oldKey
     * @param newKey
     */
    void updateKey(String oldKey, String newKey);
    /**
     * 获取指定的key 中所有的hash值
     * */
    Map<Object,Object> hgetall(String key);

    /**
     * 获取指定hash中的指定key的value
     * @param hashKey
     * @param valueKey
     * @return
     */
    Object hashGetKey(String hashKey, String valueKey);


    /**
     * 根据指定key 获取LIST集合
     * @param key
     * @param index
     *@param end @return
     */
    List<Object> getList(String key, int index, int end);

    /**
     * 根据key删除
     * @param oldkey
     */
    void delete(String oldkey);

    Long addSetObj(String key, Object obj);


    /**
     * 保存数据到指定缓存集合中
     * @param haskKey
     * @param dataCache
     */
    void saveHashValue(String haskKey, DataCache dataCache);
}
