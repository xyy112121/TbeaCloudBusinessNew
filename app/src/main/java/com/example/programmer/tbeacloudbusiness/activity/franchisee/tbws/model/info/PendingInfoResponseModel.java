package com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.model.info;

/**
 * Created by programmer on 2017/9/25.
 */

public class PendingInfoResponseModel {


    public ElectricalcheckinfoBean electricalcheckinfoX;

    public OrderinfoBean orderinfoX;

    public static class OrderinfoBean {
        public String orderid;
        public String ordertime;
        public String ordercompanyid;
        public String ordercompany;

    }

    public class ElectricalcheckinfoBean {
        public String electricalcheckid;//检测Id
        public String vouchercode;//凭证号
        public String subscribecode;//预约编号
        public String subscribeuserid;
        public String subscribeuser;//预约用户
        public String customername;//业主信息
        public String customermobile;//联系电话
        public String customeraddress;//地址
        public String subscribetime;//预约时间
        public String electricalcheckstatusid;
        public String electricalcheckstatus;
        public String subscribenote;//预约说明
    }


}
