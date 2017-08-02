package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * Created by DELL on 2017/8/2.
 */

public class PlumberMeetingListAllResonpseModel extends BaseResponseModel {

    public PlumberMeetingListAll data;

    public class PlumberMeetingListAll {
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
