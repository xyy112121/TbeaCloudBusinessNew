package com.example.programmer.tbeacloudbusiness.activity.my.main.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DELL on 2017/8/28.
 */

public class MessageTypeListResponseModel extends BaseResponseModel {

    public DataBean data;

    public static class DataBean {
        public List<MessagecategorylistBean> messagecategorylist;

        public static class MessagecategorylistBean {
            public String id;
            public String picture;
            public String newcount;
            public String name;
            public String lasttime;
            public String lastmessagetitle;
        }
    }
}
