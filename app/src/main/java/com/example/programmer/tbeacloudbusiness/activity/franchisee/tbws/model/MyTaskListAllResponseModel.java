package com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 获得我发布的任务列表-­‐全部
 */

public class MyTaskListAllResponseModel extends BaseResponseModel {


    public DataBean data;


    public class DataBean {
        public List<ElectricalchecklistBean> electricalchecklist;

        public class ElectricalchecklistBean {
            public String electricalcheckid;
            public String subscribecode;
            public String checkstatusid;
            public String checkstatus;
            public String subscribetime;
        }
    }
}
