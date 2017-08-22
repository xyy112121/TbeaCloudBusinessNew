package com.example.programmer.tbeacloudbusiness.activity.user.model;

import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;
import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by programmer on 2017/8/22.
 */

public class UserTypeResponseModel extends BaseResponseModel {

    public DataBean data;

    public static class DataBean {
        public List<KeyValueBean> usertypelist;

    }
}
