package com.example.programmer.tbeacloudbusiness.activity.user.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

/**
 * Created by DELL on 2017/7/9.
 */

public class LoginUserModel extends BaseResponseModel {
    public User data;

    public class User{
       public UserInfo userinfo;
    }

    public  class UserInfo{
        public String account;
        public String id;
        public String mailaddr;
        public String mobile;
        public String name;
        public String picture;
        public String usertypeid;
        public String whetheridentifiedid;

    }

}
