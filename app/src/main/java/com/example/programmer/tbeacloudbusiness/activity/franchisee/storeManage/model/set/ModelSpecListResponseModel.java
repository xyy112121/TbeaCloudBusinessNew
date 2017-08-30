package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.set;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 规格型号列表
 */

public class ModelSpecListResponseModel extends BaseResponseModel {


    public DataBean data;

    public class DataBean {
        public List<ModelspeclistBean> modelspeclist;

        public class ModelspeclistBean {
            public String modelspecid;
            public String modelspecname;
        }
    }
}
