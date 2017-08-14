package com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 2017/8/12.
 */

public class FranchiserSelectListResponseModel extends BaseResponseModel {

    public DataBean data;

    public  class DataBean {
        public List<DistributorlistBean> distributorlist;

        public  class DistributorlistBean implements Serializable{
            public String masterthumbpicture;
            public String mastername;
            public String id;
            public String name;
            public String companytypeid;
            public String companytypeicon;
        }
    }
}
