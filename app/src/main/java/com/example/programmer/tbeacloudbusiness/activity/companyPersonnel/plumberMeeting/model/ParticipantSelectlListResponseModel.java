package com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 2017/8/12.
 */

public class ParticipantSelectlListResponseModel extends BaseResponseModel {
    public DataBean data;


    public class DataBean {
        public List<MeetingparticipantlistBean> meetingparticipantlist;

        public class MeetingparticipantlistBean {
            public String userid;
            public String thumbpicture;
            public String name;
            public String persontypeicon;
            public String companyandjobposition;
        }
    }
}
