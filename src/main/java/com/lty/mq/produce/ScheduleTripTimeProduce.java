package com.lty.mq.produce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * gps消息生产
 * @author zhouyongbo
 */
@Component
public class ScheduleTripTimeProduce {


    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    private final static  String TOPIC = "app_driver_trip_time";


    public void send(String value){
        if (value ==null){
            return;
        }
        kafkaTemplate.send(TOPIC,value);
    }

}
