package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model;

import java.util.List;

/**
 * Created by programmer on 2017/9/29.
 */

public class ScanCodeHistoryInfoResponseModel {

    public RebateqrcodegenerateinfoBean rebateqrcodegenerateinfo;
    public List<QrcodelistBean> qrcodelist;

    public  class RebateqrcodegenerateinfoBean {
        public String id;
        public int money;
        public int number;
        public String time;
    }

    public  class QrcodelistBean {
        public String rebatecode;
        public String activitystatusid;
        public String activitystatus;
    }

}
