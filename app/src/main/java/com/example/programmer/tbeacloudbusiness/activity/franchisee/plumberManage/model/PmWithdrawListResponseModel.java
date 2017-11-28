package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * Created by programmer on 2017/8/10.
 */

public class PmWithdrawListResponseModel extends BaseResponseModel {
    public DataBean data;


    public  class DataBean {
        public List<TakeMoneyBean> takemoneylist ;
        public TakeMoneyTotleInfo takemoneytotleinfo ;

        public  class TakeMoneyBean {
            public String takemoneyid;
            public String time;
            public String qrcode;
            public String zone;
            public String money;
        }


        public class TakeMoneyTotleInfo{
            public String totlemoney;
        }
    }
}
