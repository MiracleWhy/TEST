package com.uton.carsokApi.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *《运营报表》日期参数处理统一方法
 * Created by SEELE on 2018/1/15.
 */
public class DateParamUtil {

    public static Map<String,String> dateParamHandler(String dateStr,int type){
        Map<String,String> map=new HashMap<>();
        switch (type) {
            case 1: {//日
                map.put("startDate", dateStr);
                map.put("endDate", dateStr);
                return map;
            }
            case 2: {//周   2017-11,1
                String ymd = dateStr.split(",")[0] + "-01";//年月
                Integer week = Integer.valueOf(dateStr.split(",")[1]);
                Calendar cal = Calendar.getInstance();
                cal.setTime(DateUtil.StringToDate(ymd));
                int i = 1;
                //DAY_OF_WEEK获取当前时间是一个星期的第几天，星期日是第一天  星期一是第二天，以此类推
                //Calendar.MONDAY判断是不是星期1
                while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                    cal.set(Calendar.DAY_OF_MONTH, i++);//设置这个月的星期1 为几号
                }
                Date firstMondayDate = cal.getTime();//取得日期和时间
                String firstMonday = new SimpleDateFormat("YYYY-MM-dd").format(firstMondayDate);//格式化日期
                map.put("startDate", DateUtil.addDay(firstMonday, (week - 1) * 7));
                map.put("endDate", DateUtil.addDay(firstMonday, (week * 7) - 1));
                return map;
            }
            case 3: {//月
                String endDate = DateUtil.addDay(DateUtil.addMonth(dateStr, 1) + "-01", -1);
                map.put("startDate", dateStr + "-01");
                map.put("endDate", endDate);
                return map;
            }
            case 4: {//年
                map.put("startDate", dateStr + "-01-01");
                map.put("endDate", dateStr + "-12-31");
                return map;
            }
        }
        return map;
    }
    public static Map<String,String> lastDateParamHandler(String startDate,String endDate,int type){
        Map<String,String> map=new HashMap<>();
        switch (type){
            case 1:{//日
                map.put("startDate", DateUtil.addDay(startDate,-1));
                map.put("endDate", DateUtil.addDay(endDate,-1));
                return map;
            }
            case 2:{//周
                map.put("startDate", DateUtil.addDay(startDate,-7));
                map.put("endDate", DateUtil.addDay(endDate,-7));
                return map;
            }
            case 3:{//月
                map.put("startDate", DateUtil.addMonth(startDate,-1));
                map.put("endDate", DateUtil.addMonth(endDate,-1));
                return map;
            }
            case 4:{//年
                map.put("startDate", DateUtil.addYear(startDate,-1));
                map.put("endDate", DateUtil.addYear(endDate,-1));
                return map;
            }
        }
        return map;
    }
}
