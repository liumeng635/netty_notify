package com.lty.util;


import com.lty.entity.primary.ScheduleGps;

import java.util.Date;
import java.util.List;

/**
 * gps
 * @author zhouyongbo
 */
public class GpsUtil {

    public static List<ScheduleGps> subGPSTealTimeLists(List<ScheduleGps> gpsTealTimes){
        gpsTealTimes.sort((o1, o2) -> {
            String gpsTime1 = o1.getGpsTime();
            String gpsTime2 = o2.getGpsTime();
            Date format1 = DatesUtils.format(gpsTime1, DatesUtils.FORMAT_10);
            Date format2 = DatesUtils.format(gpsTime2, DatesUtils.FORMAT_10);
            return (int)(format2.getTime()-format1.getTime());
        });
        if (gpsTealTimes.size() <= 20){
            return gpsTealTimes;
        }
//        List<GPSTealTime> gpsTealTimesSub = new ArrayList<GPSTealTime>();
//        for (int i=0;i<20;i++){
//            gpsTealTimesSub.add(gpsTealTimes.get(0));
//        }
        List<ScheduleGps> gpsTealTimesSub = gpsTealTimes.subList(0,20);
        return gpsTealTimesSub;
    }
}
