package com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 2017/8/31.
 */

public class ParticipantSelectlListAllResponseModel extends BaseResponseModel {

    public DataBean data;

    public class DataBean {
        public List<CompanyemployeelistBean> companyemployeelist;

        public class CompanyemployeelistBean implements Serializable {
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
