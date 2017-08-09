package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 水电工管理签到历史
 */

public class PmSignHistoryResponseModel extends BaseResponseModel {
    public DataBean data;

    public static class DataBean {
        public ElectricianinfoBean electricianinfo;
        public List<AttendmeetinglistBean> attendmeetinglist;

        public static class ElectricianinfoBean {
            public String userid;
            public String thumbpicture;
            public String personname;
            public String cityzone;
            public String zone;
            public String companyname;
            public String persontypeicon;
            public String personorcompany;
            public String totleattendtimes;
        }

        public static class AttendmeetinglistBean {
            public String attendid;
            public String meetingcode;
            public String zone;
            public String attendtime;
        }
    }
}
