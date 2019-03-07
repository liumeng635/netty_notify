package com.lty.util;


/**
 *
 * 缓存key
 * @author zhouyongbo
 */
public class CacheKeyUtils {

    public final static String DRIVER_TOKEN = "DRIVER_CUSTOM_TOKEN";



    public static String getTokenKey(String uuid){
        return DRIVER_TOKEN+":"+uuid;
    }



    public static String getGpsKey(Integer routeId, String sid){
        return "customization"+"_"+routeId+"_"+sid;
    }
}
