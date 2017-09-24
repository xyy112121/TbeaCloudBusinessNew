package com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * 获得我发布的任务列表-­‐已接单
 */

public class MyTaskListHaveOrderResponseModel extends BaseResponseModel {

    public DataBean data;

    public class DataBean {
        public List<TasklistBean> tasklist;

        public class TasklistBean {
            public String taskid;
            public String taskcode;
            public String bidstatusid;
            public String bidstatus;
            public String taskstatusid;
            public String taskstatus;
            public String publishtime;
        }
    }
}
