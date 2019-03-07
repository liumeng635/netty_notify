package com.lty.dao;



import java.util.List;
import java.util.Map;

/**
 * 描述: IBaseDao
 * author: yanpenglei
 * Date: 2017/9/8 19:50
 */
public interface IBaseDao<T,K>{

    /**
     * 查询统计 根据对象条件统计
     * @return
     */
    public Integer count(Map map);

    /**
     * 自定义查询
     * @param key
     * @return
     */
    public T findOne(K key);


    /**
     * 插入一条记录
     *
     * @param entity
     * @return @
     */
    public int save(T entity);

    /**
     * 批量插入数据
     *
     * @param list
     * @return @
     */
    public int save(List<T> list);

    /**
     * 更新对象(此方法只根据主键ID进行更新)
     * @param entity
     * @return @
     */
    public int update(T entity);

    /**
     * 删除 (根据主键删除)
     * @param key
     * @return @
     */
    public int delete(K key);

    /**
     * 查询一个列表， 不分页,不排序
     * @param entity 实体对象
     * @return
     */
    public List<T> selectList(T entity);


}
