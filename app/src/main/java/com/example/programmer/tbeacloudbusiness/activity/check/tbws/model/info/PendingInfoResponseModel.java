package com.example.programmer.tbeacloudbusiness.activity.check.tbws.model.info;

/**
 * Created by programmer on 2017/9/25.
 */

public class PendingInfoResponseModel {


    public ElectricalcheckinfoBean electricalcheckinfo;
    public OrderinfoBean orderinfo;
    public AssigninfoBean assigninfo;

    public  class AssigninfoBean {
        public String assignid;
        public String assigntime;
        public String electricianid;
        public String thumbpicture;
        public String name;
        public String info;
    }

    public class OrderinfoBean {
        public String orderid;
        public String ordertime;//购买时间
        public String ordercompanyid;
        public String ordercompany;//购买商家

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
        public String assigntime ;//派单时间
        public String electricalcheckstatusid;
        public String electricalcheckstatus;
        public String subscribenote;//预约说明
    }


}
