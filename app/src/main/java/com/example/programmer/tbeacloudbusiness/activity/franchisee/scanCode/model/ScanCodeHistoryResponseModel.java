package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * 扫码历史记录
 */

public class ScanCodeHistoryResponseModel extends BaseResponseModel {

    public  ScanCodeHistoryModel data;

    public class  ScanCodeHistoryModel{
        public List<RebateqrCode> rebateqrcodelist;
    }

    public class RebateqrCode{
        public String activitynumber;
        public String generatenumber;
        public String generatetime;
        public String id;
        public String money;

    }
}
