package com.example.programmer.tbeacloudbusiness.activity.my.set.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by programmer on 2017/8/16.
 */

public class SetResponseModel extends BaseResponseModel {

    public DataBean data;

    public static class DataBean {
        public BaseinfoBean baseinfo;

        public static class BaseinfoBean {
            public String mobilenumber;
            public int recvaddrnumber;
        }
    }
}
