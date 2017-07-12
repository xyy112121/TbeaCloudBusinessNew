package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * 提现排名列表返回参数
 */

public class ScanCodeRebateListResponseModel extends BaseResponseModel {

    public ScanCodeRebateList data;

    public class ScanCodeRebateList{
        public List<TakeMoneyranking> takemoneyrankinglist;
    }

    public class TakeMoneyranking{
        public  String electricianid;
        public  String personname;
        public  String personorcompany;
        public  String persontypeicon;
        public  String thumbpicture;
        public  String totlemoney;
        public  String zone;
    }

}
