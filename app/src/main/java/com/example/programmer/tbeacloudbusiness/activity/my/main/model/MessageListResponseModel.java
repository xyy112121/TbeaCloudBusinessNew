package com.example.programmer.tbeacloudbusiness.activity.my.main.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DELL on 2017/8/28.
 */

public class MessageListResponseModel extends BaseResponseModel {

    public DataBean data;


    public class DataBean {
        public PageinfoBean pageinfo;
        public List<MessagelistBean> messagelist;

        public class PageinfoBean {
            public String title;
        }

        public class MessagelistBean {
            public String id;
            public String messagetime;
            public String title;
            public String isnew;
        }
    }
}
