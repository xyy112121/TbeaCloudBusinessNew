package com.example.programmer.tbeacloudbusiness.activity.franchisee.scan.model;

import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.ScanCodeInfoResponseModel;
import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

/**
 * Created by programmer on 2017/7/21.
 */

public class ScanCodeIndoResponseModel extends BaseResponseModel {
    public ScanCodeIndoModel data;

    public class ScanCodeIndoModel {
        public TakeMoneyInfo takemoneyinfo;
    }


    public class TakeMoneyInfo {
        public String companyname;
        public String money;
        public String payeeid;
        public String personname;
        public String personorcompany;
        public String persontypeicon;
        public String qrcode;
        public String takemoneyid;
        public String takemoneystatus;
        public String takemoneystatusid;
        public String thumbpicture;
        public String validexpiredtime;
    }
}
