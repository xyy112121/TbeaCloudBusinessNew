package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DELL on 2017/8/28.
 */

public class RotateADListResponseModel extends BaseResponseModel {


    public DataBean data;

    public  class DataBean {
        public List<ShopadvertiselistBean> shopadvertiselist;

        public  class ShopadvertiselistBean {
            public String advertiseid;//广告 Id
            public String title;
            public String picture;
            public String urltype;//链接类型 店铺商品： Commodity 店铺动态： News
            public String url;//链接页面
            public String publishtime;
        }
    }
}
