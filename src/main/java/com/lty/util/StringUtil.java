package com.lty.util;


/**
 * 字符串操作工具类
 * @author zhouyongbo
 */
public class StringUtil {

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isBank(String str){
        if (str == null || "".equals(str.trim())){
            return false;
        }
        return true;
    }
}
