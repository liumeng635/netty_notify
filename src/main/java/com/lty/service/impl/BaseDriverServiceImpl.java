package com.lty.service.impl;

import com.lty.dao.primary.BaseDriverDao;
import com.lty.entity.primary.BaseDriver;
import com.lty.service.IBaseDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 司机信息业务处理
 * @author zhouyongbo
 */
@Service
public class BaseDriverServiceImpl implements IBaseDriverService {

    @Autowired
    private BaseDriverDao baseDriverDao;

    @Override
    public BaseDriver findOne(Integer id) {
        return baseDriverDao.findOne(id);
    }
}
