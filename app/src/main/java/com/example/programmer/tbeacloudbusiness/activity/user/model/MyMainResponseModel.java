package com.example.programmer.tbeacloudbusiness.activity.user.model;

import java.util.List;

/**
 * Created by programmer on 2017/7/20.
 */

public class MyMainResponseModel {
    public List<List<Item>> itemlist;
    public UserPersonInfo userpersoninfo;

    public class Item {
        public String displayvalue;
        public String icon;
        public String id;
        public String name;

    }

    public class UserPersonInfo {
        public String thumbpicture;
        public String usercompanyname;
        public String username;
        public String usertype;
    }
}
