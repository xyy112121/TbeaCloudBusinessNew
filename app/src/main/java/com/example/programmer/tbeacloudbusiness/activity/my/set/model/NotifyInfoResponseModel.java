package com.example.programmer.tbeacloudbusiness.activity.my.set.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by programmer on 2017/8/17.
 */

public class NotifyInfoResponseModel extends BaseResponseModel {

    public DataBean data;

    public static class DataBean {
        public SettinginfoBean settinginfo;

        public static class SettinginfoBean {
            public String notifyonoroff;
        }
    }
}
