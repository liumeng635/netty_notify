package com.lty.show;

import com.lty.entity.primary.BaseDriver;
import com.lty.entity.primary.BaseDriverUser;

/**
 * 司机信息show
 * @author zhouyongbo
 */
public class BaseDriverShow {

    private BaseDriver baseDriver;
    private BaseDriverUser baseDriverUser;

    public BaseDriverShow() {
    }

    public BaseDriverShow(BaseDriver baseDriver, BaseDriverUser baseDriverUser) {
        this.baseDriver = baseDriver;
        this.baseDriverUser = baseDriverUser;
    }

    public BaseDriver getBaseDriver() {
        return baseDriver;
    }

    public void setBaseDriver(BaseDriver baseDriver) {
        this.baseDriver = baseDriver;
    }

    public BaseDriverUser getBaseDriverUser() {
        return baseDriverUser;
    }

    public void setBaseDriverUser(BaseDriverUser baseDriverUser) {
        this.baseDriverUser = baseDriverUser;
    }
}
