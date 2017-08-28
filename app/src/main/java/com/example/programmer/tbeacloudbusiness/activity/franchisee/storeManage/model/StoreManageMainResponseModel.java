package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DELL on 2017/8/28.
 */

public class StoreManageMainResponseModel extends BaseResponseModel {

    public DataBean data;

    public  class DataBean {
        public List<FunctionmodulelistBean> functionmodulelist;
        public List<StaticsitemlistBean> staticsitemlist;

        public  class FunctionmodulelistBean {
            public String moduleid;
            public String moduleicon;
            public String modulename;
            public String newmessagenumber;
            public String enable;
        }

        public  class StaticsitemlistBean {
            public String moduleid;
            public String icon;
            public String name;
            public String style;
            public List<SubitemlistBean> subitemlist;

            public  class SubitemlistBean {
                public String name;
                public String value;
                public String ismoney;
            }
        }
    }
}
