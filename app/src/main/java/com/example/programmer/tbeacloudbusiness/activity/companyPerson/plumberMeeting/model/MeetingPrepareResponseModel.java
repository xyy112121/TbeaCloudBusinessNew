package com.example.programmer.tbeacloudbusiness.activity.companyPerson.plumberMeeting.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

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
