package com.fanbo.sharingitime.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * Created by fanbo on 2017/6/29.
 */

public class DateUtil {

    /**
     * 获得时间标签，当天日期+零点到方法调用时的毫秒数
     *
     * @return yyyyMMdd_mm
     */
    public static String getDateTag() {
        String dateTag = "";
        try {
            //获得当前时间的日期以及毫秒数
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String todayDate = simpleDateFormat.format(date);
            //获取当天凌晨的毫秒数
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String todayZero = todayDate + " 00:00:00";
            Date dateZero = simpleDateFormat.parse(todayZero);
            String difftime = String.valueOf(date.getTime()-dateZero.getTime());
            dateTag = todayDate+difftime;
        } catch (ParseException e) {
            ExceptionUtil.handleException(e);
        }
        return dateTag;
    }

}
