package com.example.programmer.tbeacloudbusiness.activity.check.tbws.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * 获得我发布的任务列表-­‐已上传
 */

public class MyTaskListHaveFinishedResponseModel extends BaseResponseModel {


    public DataBean data;


    public static class DataBean {
        public List<ElectricalchecklistBean> electricalchecklist;

        public static class ElectricalchecklistBean {
            public String electricalcheckid;
            public String subscribecode;
            public String uploadtime;
        }
    }
}
