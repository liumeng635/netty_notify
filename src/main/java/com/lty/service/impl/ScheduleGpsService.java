package com.lty.service.impl;

import com.alibaba.fastjson.JSON;
import com.lty.cache.DataCache;
import com.lty.cache.redis.ICacheService;
import com.lty.dao.primary.ScheduleGpsDao;
import com.lty.entity.primary.ScheduleGps;
import com.lty.service.IScheduleGpsSerivce;
import com.lty.util.CacheKeyUtils;
import com.lty.util.DatesUtils;
import com.lty.util.GpsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author zhouyongbo
 */
@Service
public class ScheduleGpsService implements IScheduleGpsSerivce {

    @Autowired
    private ScheduleGpsDao scheduleGpsDao;


    @Autowired
    private ICacheService iCacheService;


    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleGpsService.class);
    /**
     * 将秒变动为   入库的格式
     * @param scheduleGps
     */
    @Override
    @Transactional
    public void save(ScheduleGps scheduleGps) {
        Long gpstime = Long.valueOf(scheduleGps.getGpsTime());
        scheduleGps.setGpsTime(DatesUtils.format(new Date(gpstime*1000),DatesUtils.FORMAT_10));
        scheduleGps.setCreateTime(DatesUtils.format(new Date(),DatesUtils.FORMAT_10));
        scheduleGpsDao.save(scheduleGps);
        //todo 将此gps入库至 redis
        String gpsKey = CacheKeyUtils.getGpsKey(scheduleGps.getRouteId(),
                scheduleGps.getScheduleId());
        Object data = iCacheService.get(gpsKey);
        if (data == null){
            List<ScheduleGps> gpsList = new ArrayList<ScheduleGps>();
            gpsList.add(scheduleGps);
            String sJson = JSON.toJSONString(gpsList);
            iCacheService.save(new DataCache(gpsKey, sJson));
            LOGGER.info("gps:   key:"+gpsKey+" json:"+sJson);
        }else {
            List<ScheduleGps> gpsList = JSON.parseArray(data.toString(), ScheduleGps.class);
            gpsList.add(scheduleGps);
            String sJson = JSON.toJSONString(
                    GpsUtil.subGPSTealTimeLists(gpsList));
            iCacheService.save(new DataCache(gpsKey, sJson));
            LOGGER.info("gps:   key:"+gpsKey+" json:"+sJson);
        }

    }
}
