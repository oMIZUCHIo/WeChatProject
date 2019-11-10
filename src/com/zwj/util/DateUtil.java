package com.zwj.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    //获取当前时间当前时间,yyyy/MM/dd HH:mm格式
    public static String getNowTime(){

        return simpleDateFormat.format(new Date());
    }

    //获取当前系统的毫秒数
    public static String getCurrentTimeMillis(){

        return System.currentTimeMillis() + "";
    }

}
