package com.example.programmer.tbeacloudbusiness.activity.my.set.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by programmer on 2017/8/16.
 */

public class AddrListResponseModel extends BaseResponseModel {

    public DataBean data;

    public static class DataBean {
        public List<AddressModel> recvaddrlist;

    }
}
