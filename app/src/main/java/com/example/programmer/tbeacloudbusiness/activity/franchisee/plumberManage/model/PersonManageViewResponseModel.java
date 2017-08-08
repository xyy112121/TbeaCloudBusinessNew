package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

/**
 * Created by DELL on 2017/8/4.
 */

public class PersonManageViewResponseModel extends BaseResponseModel {
    public PersonManageView data;

    public class PersonManageView {
        public RebateScanInfo rebatescaninfo;
        public OrderingServiceInfo orderingserviceinfo;
        public LoginInfo logininfo;
        public ElectricianMeetingAttendInfo electricianmeetingattendinfo;
        public ElectricianBaseInfo electricianbaseinfo;
        public CommodityOrderInfo commodityorderinfo;

    }

    public class CommodityOrderInfo {
        public String totlemoneyforoneyear;
        public String totlemoneyforthreemonth;
    }

    public class ElectricianBaseInfo {
        public String address;
        public String age;
        public String attentnumber;
        public String fansnumber;
        public String homepicture;
        public String personname;
        public String thumbpicture;
        public String userid;
        public String whetheridentify;
        public String persontypeicon;
        public int starlevel;
        public String workyears;
    }

    public class ElectricianMeetingAttendInfo {
        public String attendtimesforoneyear;
        public String attendtimesforthreemonth;
    }

    public class LoginInfo {
        public String logintimes;
        public String totlelogintime;
        public String lastloginaddr;
    }

    public class OrderingServiceInfo {
        public String timesforoneyear;
        public String timesforthreemonth;
    }

    public class RebateScanInfo {
        public String takemoneyforoneyear;
        public String takemoneyforthreemonth;
    }

}
