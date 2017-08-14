package com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

/**
 * Created by DELL on 2017/8/12.
 */

public class MeetingPrepareResponseModel extends BaseResponseModel {

    public DataBean data;

    public static class DataBean {
        public MeetinginfoBean meetinginfo;

        public static class MeetinginfoBean {
            public String meetingid;
        }
    }
}
