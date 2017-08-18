package com.example.programmer.tbeacloudbusiness.activity.my.main.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by programmer on 2017/8/18.
 */

public class FansListResponseModel extends BaseResponseModel {

    public DataBean data;

    public static class DataBean {
        public List<MyfocuslistBean> myfocuslist;

        public static class MyfocuslistBean {
            public String fansid;
            public String userid;
            public String thumbpicture;
            public String name;
            public String persontypeicon;
            public String info;

            public boolean isCheck = false;//是否中
        }
    }
}
