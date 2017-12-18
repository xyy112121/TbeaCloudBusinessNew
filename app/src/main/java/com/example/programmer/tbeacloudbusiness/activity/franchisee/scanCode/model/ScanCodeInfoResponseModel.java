package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

/**
 *扫描详情
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
        public String activityusercityzone;
        public String actuvityuserpicture;
        public String personjobtitle;
        public String qrcodeactivityid;
    }

    public class RebateqrCode {
        public String commodityspecificationandmodel;
        public String confirmstatus;
        public String generateid;
        public String generatetime;
        public String commoditycategory;
        public String money;
        public String rebatecode;
    }
}
