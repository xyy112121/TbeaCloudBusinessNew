package com.example.programmer.tbeacloudbusiness.activity.distributor.rebateAccount.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * Created by programmer on 2017/7/20.
 */

public class RebateAccountListResponseModel extends BaseResponseModel {
    public RebateAccountListModel data;

    public class RebateAccountListModel {
        public MyMoneyInfo mymoneyinfo;
        public List<NottakeMoney> nottakemoneylist;

    }

    public class MyMoneyInfo {
        public String currentmoney;
    }

    public class NottakeMoney {
        public String id;
        public String money;
        public String takemoneycode;
        public String validexpiredtime;
    }
}
