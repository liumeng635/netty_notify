package com.lty;

import com.alibaba.fastjson.JSONObject;
import com.lty.entity.primary.ScheduleGps;
import com.lty.entity.primary.ScheduleTripTime;
import com.lty.util.MD5Utils;

import java.math.BigDecimal;

public class TokenLogin {


    public static void main(String[] args) {

        System.out.println(genrateSche());
    }

    private static String token = "dabfa39ef5ad4bbd8afa205f4dcb2d15";

    public static String generate(){
        JSONObject header = new JSONObject();
        header.put("msgSn",123);
        header.put("msgId",0x102);
        header.put("msgVn",1.01);

        JSONObject appendBody = new JSONObject();
        appendBody.put("devSn","aaaaaaaaaa");
        appendBody.put("appVn","1.5");
        appendBody.put("token",token);
        appendBody.put("devType","0");

        JSONObject tlt = new JSONObject();
        tlt.put("header",header);
        tlt.put("appendBody",appendBody);


        System.out.println(tlt.toJSONString().length());

        String s = "#" + tlt.toJSONString()+ MD5Utils.encrypt16(tlt.toJSONString().length()+"");
        return s;
    }


    public static String genrateGps(){
        JSONObject header = new JSONObject();
        header.put("msgSn",123);
        header.put("msgId",0x201);
        header.put("msgVn",1.01);
        JSONObject appendBody = new JSONObject();
        appendBody.put("devSn","aaaaaaaaaa");
        appendBody.put("appVn","1.5");
        appendBody.put("token",token);
        appendBody.put("devType","0");
        JSONObject tlt = new JSONObject();
        tlt.put("header",header);
        tlt.put("appendBody",appendBody);
        ScheduleGps scheduleGps = new ScheduleGps();
        scheduleGps.setScheduleId("101010");
        scheduleGps.setRouteId(9);
        scheduleGps.setGpsTime(1528989806+"");
        scheduleGps.setBusId(10);
        scheduleGps.setCityCode(13040+"");
        scheduleGps.setDriverId(14);
        scheduleGps.setLatitude(new BigDecimal(10.1111));
        scheduleGps.setLongitude(new BigDecimal(10.1111));

        tlt.put("body",scheduleGps);
        System.out.println(tlt.toJSONString().length());

        String s = "#" + tlt.toJSONString()+ MD5Utils.encrypt16(tlt.toJSONString().length()+"");
        return s;
    }


    public static String genrateSche(){
        JSONObject header = new JSONObject();
        header.put("msgSn",123);
        header.put("msgId",0x202);
        header.put("msgVn",1.01);
        JSONObject appendBody = new JSONObject();
        appendBody.put("devSn","1528989806");
        appendBody.put("appVn","1.5");
        appendBody.put("token",token);
        appendBody.put("devType","0");
        JSONObject tlt = new JSONObject();
        tlt.put("header",header);
        tlt.put("appendBody",appendBody);
        ScheduleTripTime scheduleTripTime = new ScheduleTripTime();
//        scheduleTripTime.setScheduleDate(1528989806+"");
        scheduleTripTime.setBusId(10);
        scheduleTripTime.setRouteId(10);
        scheduleTripTime.setScheduleId("101010");
        scheduleTripTime.setStationId(10);
        scheduleTripTime.setStationName("aaaa");
        scheduleTripTime.setStationTime("1528989806");
        tlt.put("body",scheduleTripTime);
        System.out.println(tlt.toJSONString().length());

        String s = "#" + tlt.toJSONString()+ MD5Utils.encrypt16(tlt.toJSONString().length()+"");
        return s;

    }

    public static String genrate302(){
        JSONObject header = new JSONObject();
        header.put("msgSn",6022014);
        header.put("msgId",0x302);
        header.put("msgVn",1.01);
        JSONObject appendBody = new JSONObject();
        appendBody.put("devSn","1528989806");
        appendBody.put("appVn","1.5");
        appendBody.put("token",token);
        appendBody.put("devType","0");
        JSONObject tlt = new JSONObject();
        tlt.put("header",header);
        tlt.put("appendBody",appendBody);

        tlt.put("body","ni ma bi");
        System.out.println(tlt.toJSONString().length());

        String s = "#" + tlt.toJSONString()+ MD5Utils.encrypt16(tlt.toJSONString().length()+"");
        return s;

    }

}
