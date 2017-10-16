package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.example.programmer.tbeacloudbusiness.model.Condition;

import java.util.List;

/**
 * Created by programmer on 2017/10/16.
 */

public class CommdityCreateListResponseModel extends BaseResponseModel {

    public DataBean data;


    public class DataBean {
        public List<Condition> tbeacommoditycaterogylist;
    }

}
