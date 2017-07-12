package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

/**
 *生成二维码
 */

public class ScanCodeCreateResponseModel extends BaseResponseModel {
    public ScanCodeCreateModel data;

    public class ScanCodeCreateModel {
        public ScanCodeCreate generateinfo;

    }

    public class ScanCodeCreate {
        public String id;
        public String totlenumber;

    }
}
