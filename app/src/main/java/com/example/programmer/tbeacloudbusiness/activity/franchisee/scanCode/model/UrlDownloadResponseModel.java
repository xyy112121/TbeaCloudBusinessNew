package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

/**
 * 下载链接
 */

public class UrlDownloadResponseModel extends BaseResponseModel {
    public Generateinfo generateinfo;

    public static class Generateinfo {
        public String companyname;
        public String commodityname;
        public String commodityspecificationandmodel;
        public String money;
        public int count;
        public String totlemoney;
        public String createtime;
        public String excelfileurl;
    }
}
