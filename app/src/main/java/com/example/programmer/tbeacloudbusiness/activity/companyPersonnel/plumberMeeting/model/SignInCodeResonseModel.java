package com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

/**
 * Created by programmer on 2017/11/9.
 */

public class SignInCodeResonseModel extends BaseResponseModel {


    public Data data;

    public class Data {
        public Meetinginfo meetinginfo;

        public class Meetinginfo {
            public String meetingid;
            public String meetingcode;
            public String meetingtime;
            public String meetingplace;
            public String meetingqrcodepicture;
        }
    }
}
