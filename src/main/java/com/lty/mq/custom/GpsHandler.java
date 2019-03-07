package com.lty.mq.custom;

import com.alibaba.fastjson.JSON;
import com.lty.entity.primary.ScheduleGps;
import com.lty.service.IScheduleGpsSerivce;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * gps处理
 * @author zhouyongbo
 */
@Component
public class GpsHandler {

    @Autowired
    IScheduleGpsSerivce iScheduleGpsSerivce;


    @KafkaListener(topics = {"app_driver_gps"})
    public void listen(ConsumerRecord<String, String> record) {
        String value = record.value();
        if (value!=null){
            try{
                iScheduleGpsSerivce.save(JSON.parseObject(value, ScheduleGps.class));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



}
