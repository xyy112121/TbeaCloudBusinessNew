package com.example.programmer.tbeacloudbusiness.activity.user.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * Created by DELL on 2017/7/9.
 */

public class HomeMainResponseModel extends BaseResponseModel{
    public HomeMainModel data;
    
    public class HomeMainModel{
        public List<FunctionModel> functionmodulelist;
        public List<SystemMessage> systemmessagelist;
        public List<StaticsItem> staticsitemlist;
    }

    public class FunctionModel{
        public String enable;
        public String moduleicon ;
        public String moduleid ;
        public String modulename ;
        public String newmessagenumber ;
    }
    public class SystemMessage{
        public String id;
        public String icon ;
        public String title ;
        public String url ;
    }
    public class StaticsItem{
        public String staticsitemid;
        public String icon ;
        public String name ;
        public String style ;
        public List<Subitem> subitemlist;
    }

    public class Subitem{
        public String name;
        public String value;
        public String value1;
        public String value2;
    }
}
