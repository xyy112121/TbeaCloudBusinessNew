package com.example.programmer.tbeacloudbusiness.activity.my.main.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 子账户权限列表
 */

public class BypassAccountFunctionListResponseModel extends BaseResponseModel {


    public DataBean data;

    public static class DataBean {
        public List<ModulelistBean> modulelist;

        public static class ModulelistBean {
            public String moduleid;
            public String modulename;
            public String moduleicon;
            public String canview;//可查看 Yes or no
            public String canoperation;//可操作 Yes or no
        }
    }
}
