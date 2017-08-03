package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

/**
 *会议详细
 */

public class PlumberMeetingViewResponseModel extends BaseResponseModel {

    public PlumberMeetingView data;

    public class  PlumberMeetingView{
      public  MeetingBaseInfo meetingbaseinfo;
      public  OrganizeCompany organizecompanylist;
      public  MeetingOriginatorInfo meetingoriginatorinfo;
      public  Participant participantlist;
      public  MeetingInfo meetinginfo;
      public  MeetingSignInfo meetingsigninfo;
    }

    public class MeetingBaseInfo{
        public String id;
        public String meetingcode;
        public String meetingtime;
        public String meetingplace;
        public String meetingstatus;
    }

    public class OrganizeCompany {
        public String companyid;
        public String companyname;
        public String companymastername;
        public String companymasterheadpicture;
        public String companypersontypeicon;
    }

    public class MeetingOriginatorInfo {
        public String useid;
        public String headpicture;
        public String name;
        public String company;
        public String jobposition;
        public String persontypeicon;
    }

    public  class Participant{
        public String participantnumber;
    }

    public  class MeetingInfo{
        public String meetingitems;
        public String meetingsummary;
        public String meetingpicturenumber;
    }

    public  class MeetingSignInfo{
        public String signnumber;
    }

}
