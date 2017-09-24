package com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.model;



import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * 获得我发布的任务列表-­‐待评价
 */

public class MyTaskListHaveEvaluationResponseModel extends BaseResponseModel {

    public DataBean data;

    public static class DataBean {
        public List<TasklistBean> tasklist;

        public static class TasklistBean {
            public String taskid;
            public String taskcode;
            public String actualfee;
            public String taskstatusid;
            public String taskstatus;
            public String finishtime;
        }
    }
}
