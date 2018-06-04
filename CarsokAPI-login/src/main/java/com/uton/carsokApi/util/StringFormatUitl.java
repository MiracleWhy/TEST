package com.uton.carsokApi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by WANGYJ on 2017/11/8.
 * TODO:根据不同的类型追加下面的处理，主要是需要以枚举形式展示的部分
 */
public class StringFormatUitl {
    public static String formatStringByType(String orgStr, String type, String format,String suffix) {
        String ret = null;
;
        if (type != null){
            switch (type){
                case "String":
                    ret = orgStr;
                    break;
                case "Date":
                    SimpleDateFormat oldsdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    if (format != null){
                        sdf = new SimpleDateFormat(format);
                        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
                    }
                    Date tmpdate = null;
                    try {
                        tmpdate = oldsdf.parse(orgStr);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    ret = sdf.format(tmpdate);
                    break;
                default:
                    ret = orgStr;
                    break;
            }
        }else {
            ret = orgStr;
        }

        //如果有后缀的情况下，追加后缀
        if (suffix != null){
            ret += suffix;
        }
        return ret;
    }
}
