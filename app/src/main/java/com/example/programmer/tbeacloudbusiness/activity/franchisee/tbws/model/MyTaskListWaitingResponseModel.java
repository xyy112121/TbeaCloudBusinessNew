package com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * 获得我发布的任务列表-­‐待处理
 */

public class MyTaskListWaitingResponseModel extends BaseResponseModel {


    public DataBean data;

    public static class DataBean {
        public List<ElectricalchecklistBean> electricalchecklist;

        public static class ElectricalchecklistBean {
            public String electricalcheckid;
            public String subscribecode;
            public String checkstatusid;
            public String checkstatus;
            public String customeruserid;
            public String customername;
            public String subscribetime;
        }
    }
}
