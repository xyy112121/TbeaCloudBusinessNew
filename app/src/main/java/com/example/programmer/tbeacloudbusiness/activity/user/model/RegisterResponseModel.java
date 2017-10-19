package com.example.programmer.tbeacloudbusiness.activity.user.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by programmer on 2017/10/19.
 */

public class RegisterResponseModel extends BaseResponseModel {

    public DataBean data;


    public  class DataBean {
        public UserinfoBean userinfo;

        public  class UserinfoBean {
            public String id;
            public String picture;
            public String name;
            public String account;
            public String usertypeid;
            public String mobile;
            public String mailaddr;
            public String whetheridentifiedid;
        }
    }
}
