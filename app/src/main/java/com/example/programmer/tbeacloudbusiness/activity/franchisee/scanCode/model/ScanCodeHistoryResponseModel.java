package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * Created by DELL on 2017/7/9.
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
