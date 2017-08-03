package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * 参与人员
 */

public class PlumberMeetingParticipantResponseModel extends BaseResponseModel {
    public PlumberMeetingParticipant data;

    public class PlumberMeetingParticipant {

        public List<MeetingParticipant> meetingparticipantlist;
    }

    public class MeetingParticipant {
        public String userid;
        public String thumbpicture;
        public String name;
        public String persontypeicon;
        public String companyandjobposition;
    }
}
