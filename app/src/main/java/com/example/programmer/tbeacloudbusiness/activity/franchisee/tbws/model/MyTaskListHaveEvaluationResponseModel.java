package com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.model;



import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 获得我发布的任务列表-­‐已完工
 */

public class MyTaskListHaveEvaluationResponseModel extends BaseResponseModel {


    public DataBean data;


    public static class DataBean {
        public List<ElectricalchecklistBean> electricalchecklist;

        public static class ElectricalchecklistBean {
            public String electricalcheckid;
            public String subscribecode;
            public String electricianid;
            public String electricianname;
            public String finishtime;
        }
    }
}
