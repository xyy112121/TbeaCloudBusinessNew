package com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberManage.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * Created by programmer on 2017/8/15.
 */

public class DranchiseeSeleteResonpseModel extends BaseResponseModel{

    public DataBean data;

    public static class DataBean {
        public List<FirstLevelDistributorListBean> firstleveldistributorlist;

        public static class FirstLevelDistributorListBean {
            public String fdistributorid;
            public String thumbpicture;
            public String mastername;
            public String companyname;
            public int electriciannumber;
        }
    }
}
