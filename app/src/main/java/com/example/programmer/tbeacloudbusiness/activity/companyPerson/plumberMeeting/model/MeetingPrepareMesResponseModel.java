package com.example.programmer.tbeacloudbusiness.activity.companyPerson.plumberMeeting.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

/**
 * Created by DELL on 2017/8/12.
 */

public class MeetingPrepareMesResponseModel extends BaseResponseModel {

    public DataBean data;

    public class DataBean {
        public MeetinginfoBean meetinginfo;
        public MeetingoriginatorinfoBean meetingoriginatorinfo;

        public class MeetinginfoBean {
            public String meetingcode;
            public String meetingstatusid;
            public String meetingstatus;
        }

        public class MeetingoriginatorinfoBean {
            public String userid;
            public String headpicture;
            public String name;
            public String company;
            public String jobposition;
            public String persontypeicon;
        }
    }
}
