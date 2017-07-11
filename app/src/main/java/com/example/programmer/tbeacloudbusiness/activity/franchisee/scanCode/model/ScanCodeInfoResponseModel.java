package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

/**
 * Created by programmer on 2017/7/10.
 */

public class ScanCodeInfoResponseModel extends BaseResponseModel {
    public ScanCodeInfo data;

    public class ScanCodeInfo {
        public RebateqrCodeActivity rebateqrcodeactivityinfo;
        public RebateqrCode rebateqrcodeinfo;
    }


    public class RebateqrCodeActivity {
        public String activityplace;
        public String activitytime;
        public String actuvityuserid;
        public String actuvityusername;
        public String actuvityuserpicture;
        public String personjobtitle;
        public String qrcodeactivityid;
    }

    public class RebateqrCode {
        public String commodityspecificationandmodel;
        public String confirmstatus;
        public String generateid;
        public String generatetime;
        public String money;
        public String rebatecode;
    }
}
