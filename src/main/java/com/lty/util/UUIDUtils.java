package com.lty.util;

import java.util.UUID;

/**
 * UUID 工具类
 * @author zhouyongbo
 */
public class UUIDUtils {


    public static String getUUId(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
