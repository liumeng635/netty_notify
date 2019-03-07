package com.lty.service.impl;


import com.lty.dao.primary.ScheduleTripTimeDao;
import com.lty.entity.primary.ScheduleTripTime;
import com.lty.service.IScheduledTripTimeService;
import com.lty.util.DatesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *
 * @author zhouyongbo
 */
@Service
public class ScheduledTripTimeService implements IScheduledTripTimeService {

    @Autowired
    ScheduleTripTimeDao scheduleTripTimeDao;

    @Override
    public void saveTime(ScheduleTripTime scheduleTripTime) {
        scheduleTripTime.setCreateTime(DatesUtils.format(new Date(),DatesUtils.FORMAT_10));
        scheduleTripTime.setStationTime(DatesUtils.format(new Date(Long.parseLong
                (scheduleTripTime.getStationTime())*1000),DatesUtils.FORMAT_10));
        scheduleTripTimeDao.save(scheduleTripTime);
    }
}
