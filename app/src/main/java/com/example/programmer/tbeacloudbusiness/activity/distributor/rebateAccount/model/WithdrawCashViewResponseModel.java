package com.example.programmer.tbeacloudbusiness.activity.distributor.rebateAccount.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

/**
 * Created by programmer on 2017/7/20.
 */

public class WithdrawCashViewResponseModel extends BaseResponseModel {
    public WithdrawCashViewModel data;

    public class WithdrawCashViewModel {
        public DistributorInfo distributorinfo;
        public TakemoneyInfo takemoneyinfo;
    }

    public class DistributorInfo {
        public String addr;
        public String mobilenumber;
        public String name;
    }

    public class TakemoneyInfo {
        public String id;
        public String money;
        public String note;
        public String qrcodepicture;
        public String status;
        public String takemoneycode;
        public String validexpiredtime;
    }

}
