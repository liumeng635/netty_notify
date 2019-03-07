package com.lty.dao.primary;


import com.lty.dao.IBaseDao;
import com.lty.entity.primary.BaseDriverUser;

import java.util.Map;

/**
 *
 * 司机用户表
 * @author zhouyongbo
 */
public interface BaseDriverUserDao extends IBaseDao<BaseDriverUser,Integer> {


    BaseDriverUser findUserNameAndPassword(Map<String, Object> params);
}
