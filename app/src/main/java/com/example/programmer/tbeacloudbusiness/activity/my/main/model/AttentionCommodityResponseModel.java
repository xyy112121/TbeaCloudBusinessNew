package com.example.programmer.tbeacloudbusiness.activity.my.main.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 关注的店铺
 */

public class AttentionCommodityResponseModel extends BaseResponseModel {

    public DataBean data;


    public static class DataBean {
        public List<MyfocuslistBean> myfocuslist;

        public static class MyfocuslistBean {
            public String focusid;
            public String commodityid;
            public String thumbpicture;
            public String name;
            public String price;

            public boolean isCheck = false;//是否中
        }
    }
}
