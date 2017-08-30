package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.set;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DELL on 2017/8/29.
 */

public class StoreIntroduceResponseModel extends BaseResponseModel {

    public DataBean data;

    public static class DataBean {
        public ShopinfoBean shopinfo;

        public static class ShopinfoBean {
            public String shopdescription;
        }
    }
}
