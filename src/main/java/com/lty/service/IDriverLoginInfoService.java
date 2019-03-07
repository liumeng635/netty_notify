package com.lty.service;

import com.lty.entity.primary.DriverLoginInfo;
import com.lty.show.BaseDriverShow;
import com.lty.socket.packet.appDriver.base.AppendBody;

/**
 * 司机登录信息业务处理
 * @author zhouyongbo
 */
public interface IDriverLoginInfoService {

    /**
     *
     * @param driverLoginInfo
     * @return
     */
    int save(DriverLoginInfo driverLoginInfo);


    /**
     *
     * @param baseDriverShow
     * @param appendBody
     * @param type 0：登录  1：退出
     */
    void saveDriverInfo(BaseDriverShow baseDriverShow, AppendBody appendBody, int type);
}
