package com.lty.cache.redis;


import com.lty.cache.DataCache;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author  zhouyongbo 2017/10/27
 * 缓存使用接口
 */
public interface IAbsCache {

    /**
     * 保存数据 data
     * @param dataCache
     */
    void save(DataCache dataCache);

    /**
     * 保存数据到指定缓存集合中
     * @param haskKey
     * @param dataCache
     */
    void saveHashValue(String haskKey, DataCache dataCache);


    /**
     * 修改key
     * @param oldKey
     * @param newKey
     */
    void updateKey(String oldKey, String newKey);


    /**
     * 根据KEY获取值
     * @param key
     * @return
     */
    Object get(String key) ;

    /**
     * 根据key来进行模糊查询
     * @param key
     * @return
     */
    Set<String> like(String key);


    /**
     * 删除key
     * @param key
     */
    void del(String key);

	Map<Object, Object> hgetall(String key);

    List<Object> getList(String key, int index, int end);

    /**
     * 
     * @Title: addSetObj   
     * @Description: 添加set集合
     * @author: yuwenfeng     
     * @date:   2018年1月30日 下午4:57:54     
     * @param: @param key
     * @param: @param objs
     * @param: @return      
     * @return: Long      
     * @throws
     */
    Long addSetObj(String key, Object objs);

    /**
     * 获取指定hash中的指定key的value
     * @param hashKey
     * @param valueKey
     * @return
     */
    Object hashGetKey(String hashKey, String valueKey);



}
