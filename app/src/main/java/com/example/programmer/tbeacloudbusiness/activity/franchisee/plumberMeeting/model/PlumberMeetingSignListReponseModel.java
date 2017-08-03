package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * Created by DELL on 2017/8/3.
 */

public class PlumberMeetingSignListReponseModel extends BaseResponseModel {
    public PlumberMeetingSignList data;

    public class PlumberMeetingSignList {
        public List<MeetingSign> meetingsignlist;
        public MeetingSignTotleInfo meetingsigntotleinfo;
    }

    public class MeetingSign {
        public String name;
        public String persontypeicon;
        public String signtime;
        public String thumbpicture;
        public String userid;
        public String zone;
        public String signid;
        public String totlesignnumber;
    }

    public class MeetingSignTotleInfo {
        public String totlesignnumber;
    }

}
