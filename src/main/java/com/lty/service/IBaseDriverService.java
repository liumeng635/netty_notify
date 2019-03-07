package com.lty.service;


import com.lty.entity.primary.BaseDriver;

/**
 *
 * 司机信息表
 * @author zhouyongbo
 */
public interface IBaseDriverService {

    BaseDriver findOne(Integer id);
}
