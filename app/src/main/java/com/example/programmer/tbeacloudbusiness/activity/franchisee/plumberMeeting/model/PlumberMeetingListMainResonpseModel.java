package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * 水电工列表首页
 */

public class PlumberMeetingListMainResonpseModel extends BaseResponseModel{
    public PlumberMeetingListMain data;

    public class PlumberMeetingListMain {
        public List<Meeting> meetinglist;
    }


    public class Meeting {
        public String id;
        public String meetingcode;
        public String meetingstatus;
        public String meetingtime;
        public String zone;

    }
}
