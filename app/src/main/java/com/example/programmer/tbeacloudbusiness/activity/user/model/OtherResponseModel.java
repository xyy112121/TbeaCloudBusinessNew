package com.example.programmer.tbeacloudbusiness.activity.user.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DELL on 2017/8/24.
 */

public class OtherResponseModel extends BaseResponseModel {

    public DataBean data;

    public static class DataBean {
        public List<HomeMainResponseModel.FunctionModel> functionmodulelist;
    }
}
