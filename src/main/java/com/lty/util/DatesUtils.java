package com.lty.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhouyongbo 2017/11/1
 * 时间工具
 */
public class DatesUtils {
    public final static String FORMAT_10 = "yyyyMMddHHmmss"; //精确到秒
    public final static String FORMAT_13 = "yyyy-MM-dd HH:mm:ss"; //精确到秒
    public final static String FORMAT_8 = "yyyy-MM-dd"; // 年月日
    public final static String FORMAT_17 = "yyyy-MM-dd HH:mm:ss.SSS"; // 精确到毫秒
    public final static String FORMAT_ES_18 = "yyyy-MM-dd HH:mm:ss.FFFZ"; // 精确到毫秒
//    public final static String FORMAT_17_ =



    public static String format(Date date,String format){
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }



    public static Date format(String date,String format){
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date formatUTCES(String date,String format){
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return new Date(formatter.parse(date).getTime());
//           return new Date(formatter.parse(date).getTime()+8*3600*1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


}
