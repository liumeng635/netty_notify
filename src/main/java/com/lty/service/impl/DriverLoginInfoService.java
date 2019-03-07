package com.lty.service.impl;


import com.lty.dao.primary.DriverLoginInfoDao;
import com.lty.entity.primary.DriverLoginInfo;
import com.lty.service.IDriverLoginInfoService;
import com.lty.show.BaseDriverShow;
import com.lty.socket.packet.appDriver.base.AppBasePacket;
import com.lty.socket.packet.appDriver.base.AppendBody;
import com.lty.util.DatesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *
 * @author zhouyongbo
 */
@Service
public class DriverLoginInfoService implements IDriverLoginInfoService {

    @Autowired
    DriverLoginInfoDao driverLoginInfoDao;

    @Override
    public int save(DriverLoginInfo driverLoginInfo) {
        driverLoginInfoDao.save(driverLoginInfo);
        return 0;
    }

    @Override
    public void saveDriverInfo(BaseDriverShow baseDriverShow,
                               AppendBody appendBody, int type) {
        try{
            DriverLoginInfo driverLoginInfo = new DriverLoginInfo();
            driverLoginInfo.setCreateTime(DatesUtils.format(new Date(),DatesUtils.FORMAT_10));
            driverLoginInfo.setDriverId(baseDriverShow.getBaseDriver().getId());
            driverLoginInfo.setFacilityNo(appendBody.getDevSn());
            driverLoginInfo.setFacilityType(appendBody.getDevType());
            driverLoginInfo.setIsLogin(type);
            driverLoginInfo.setProtocolVersion(appendBody.getProtocolVersion()+"");
            driverLoginInfo.setAppVersion(appendBody.getAppVn()+"");
            save(driverLoginInfo);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
