package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * Created by DELL on 2017/8/4.
 */

public class PmMainListResponseModel extends BaseResponseModel {
    public PlumberManageMainList data;

    public class PlumberManageMainList {
        public List<Electrician> electricianlist;
    }

    public class Electrician {
        public String cityzone;
        public String personname;
        public String personorcompany;
        public String persontypeicon;
        public String thumbpicture;
        public String totlemoney;
        public String userid;
    }
}
