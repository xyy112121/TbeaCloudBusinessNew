package com.example.programmer.tbeacloudbusiness.activity.distributor.scanCode.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * 扫码返利首页
 */

public class DBScanCodeMainResponseModel extends BaseResponseModel {


    public DataBean data;


    public class DataBean {
        public PayandincomestaticinfoBean payandincomestaticinfo;
        public List<TakeMoneyRanking> takemoneyrankinglist;
    }

    public class PayandincomestaticinfoBean {
        public String totlepayed;
        public String totleincome;
    }

    public class TakeMoneyRanking {
        public String electricianid;
        public String payeeid;
        public String personorcompany;
        public int sequence;
        public String thumbpicture;
        public String personname;
        public String personjobtitle;
        public String totlemoney;
        public String rankingorder;
    }
}
