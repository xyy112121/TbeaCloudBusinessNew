package com.example.programmer.tbeacloudbusiness.activity.publicUse.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by programmer on 2017/10/27.
 */

public class NetUrlResponseModel extends BaseResponseModel {

    public DataBean data;

    public class DataBean {
        public String url;
    }
}
