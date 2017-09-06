package com.example.programmer.tbeacloudbusiness.activity.distributorManage.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by programmer on 2017/9/6.
 */

public class DMWithdrawalHistoryListResponseModel extends BaseResponseModel {

    public DataBean data;

    public class DataBean {
        public DistributorinfoBean distributorinfo;
        public List<TakemoneylistBean> takemoneylist;

        public class DistributorinfoBean {
            public String distributorid;
            public String thumbpicture;
            public String mastername;
            public String persontypeicon;
            public String companyname;
            public int totletakemoney;
        }

        public class TakemoneylistBean {
            public String takemoneyid;
            public String time;
            public int money;
        }
    }
}
