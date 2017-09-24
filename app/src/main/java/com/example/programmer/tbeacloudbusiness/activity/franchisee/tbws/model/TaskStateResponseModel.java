package com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.example.programmer.tbeacloudbusiness.model.Condition;

import java.util.List;

/**
 * Created by DELL on 2017/9/18.
 */

public class TaskStateResponseModel extends BaseResponseModel {
    public DataBean data;

    public class DataBean {
        public List<Condition> taskstatuslist;
    }
}
