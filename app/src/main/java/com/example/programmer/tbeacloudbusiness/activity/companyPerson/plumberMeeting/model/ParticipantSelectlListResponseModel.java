package com.example.programmer.tbeacloudbusiness.activity.companyPerson.plumberMeeting.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 2017/8/12.
 */

public class ParticipantSelectlListResponseModel extends BaseResponseModel {
    public DataBean data;

    public static class DataBean {
        public List<CompanyemployeelistBean> companyemployeelist;

        public static class CompanyemployeelistBean implements Serializable {
            public String userid;
            public String thumbpicture;
            public String personname;
            public String cityzone;
            public String zone;
            public String companyname;
            public String persontypeicon;
            public String personorcompany;
        }
    }
}
