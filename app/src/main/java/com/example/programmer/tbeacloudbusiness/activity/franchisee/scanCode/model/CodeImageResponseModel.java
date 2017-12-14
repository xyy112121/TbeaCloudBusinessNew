package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model;

/**
 * Created by DELL on 2017/12/14.
 */

public class CodeImageResponseModel {

    public QrcodepictureinfoBean qrcodepictureinfo;

    public static class QrcodepictureinfoBean {
        public String qrcodepicture;
        public String qrcode;
        public String rebatemoney;
        public String activitystatus;
    }
}
