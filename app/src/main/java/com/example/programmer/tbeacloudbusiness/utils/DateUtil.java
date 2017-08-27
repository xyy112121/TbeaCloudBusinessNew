package com.example.programmer.tbeacloudbusiness.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    private static SimpleDateFormat sf=new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
    private static SimpleDateFormat  dsf=new SimpleDateFormat("MM-dd",Locale.getDefault());
    private static SimpleDateFormat  tf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());
    private static SimpleDateFormat  tf2=new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());


    public static String format(Date date){
        return sf.format(date);
    }

    public static String formatTime(Date date){
        return dsf.format(date);
    }

    public static String formatTime2(Date date){
        return tf.format(date);
    }

    public static String formatTime3(Date date){
        return tf2.format(date);
    }

    public static String formatTimeStr(String timeStr){
        try{
            long time=Long.parseLong(timeStr)*1000;
            return formatTime(new Date(time));
        }catch(NumberFormatException nfe){
            Log.e("DateUtil",nfe.getMessage(),nfe);
        }
        return null;
    }

    public static String formatTimeStr2(String timeStr){
        try{
            long time=Long.parseLong(timeStr)*1000;
            return formatTime2(new Date(time));
        }catch(NumberFormatException nfe){
            Log.e("DateUtil",nfe.getMessage(),nfe);
        }
        return null;
    }
}

