package com.lty.mq.custom;

import com.alibaba.fastjson.JSON;
import com.lty.entity.primary.ScheduleGps;
import com.lty.entity.primary.ScheduleTripTime;
import com.lty.service.IScheduleGpsSerivce;
import com.lty.service.IScheduledTripTimeService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * gps处理
 * @author zhouyongbo
 */
@Component
public class ScheduleTripTimeHandler {

    @Autowired
    IScheduledTripTimeService iScheduledTripTimeService;

    @KafkaListener(topics = {"app_driver_trip_time"})
    public void listen(ConsumerRecord<String, String> record) {
        String value = record.value();
        if (value!=null){
            try{
                iScheduledTripTimeService.saveTime(JSON.parseObject(value, ScheduleTripTime.class));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



}
