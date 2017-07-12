package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * 已激活列表返回参数
 */

public class ScanCodeStateListResponseModel extends BaseResponseModel {
    public ScanCodeStateModel data;

    public class  ScanCodeStateModel{
        public List<RebateqrCodeActivity> rebateqrcodeactivitylist;
    }

    public class RebateqrCodeActivity{
        public  String activityday;
        public  String activitytime;
        public  String actuvityuser;
        public  String qrcodeactivityid;
        public  String rebatecode;
    }
}
