package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * Created by programmer on 2017/8/9.
 */

public class PmScanCodeResponseModel extends BaseResponseModel {

    public DataBean data;


    public static class DataBean {
        public List<RebatescanlistBean> rebatescanlist;

        public static class RebatescanlistBean {
            public String rebatescanid;
            public String scantime;
            public String confirmstatus;
            public String money;
        }
    }
}
