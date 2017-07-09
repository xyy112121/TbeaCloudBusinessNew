package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

/**
 * Created by DELL on 2017/7/9.
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
