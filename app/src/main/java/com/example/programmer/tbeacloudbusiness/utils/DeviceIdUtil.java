package com.example.programmer.tbeacloudbusiness.utils;

import android.telephony.TelephonyManager;

import com.example.programmer.tbeacloudbusiness.activity.MyApplication;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by programmer on 2017/7/6.
 */

public class DeviceIdUtil {
    public static String getDeviceId(){
        TelephonyManager TelephonyMgr = (TelephonyManager) MyApplication.instance.getSystemService(TELEPHONY_SERVICE);
        String szImei = TelephonyMgr.getDeviceId();
        return  szImei;
    }

}
