package com.example.programmer.tbeacloudbusiness.activity.distributorManage.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by programmer on 2017/9/6.
 */

public class DMMainListReponseModel extends BaseResponseModel {


    public DataBean data;

    public static class DataBean {
        public List<DistributelistBean> distributelist;

        public static class DistributelistBean {
            public String distributorid;
            public String thumbpicture;
            public String mastername;
            public String persontypeicon;
            public String companyname;
            public String zone;
            public String totletakemoney;
        }
    }
}
