package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *会议详细
 */

public class PlumberMeetingViewResponseModel extends BaseResponseModel {
    public DataBean data;

    public static class DataBean {
        public MeetingbaseinfoBean meetingbaseinfo;//基本信息
        public MeetingoriginatorinfoBean meetingoriginatorinfo;//发起人
        public ParticipantlistBean participantlist;//参与人员
        public MeetingInfo meetinginfo;//会议信息
        public MeetingSignInfo meetingsigninfo;//会议签到
        public List<OrganizeCompany> organizecompanylist;//举办单位

        public  class MeetingbaseinfoBean {
            public String id;
            public String meetingcode;
            public String meetingtime;
            public String meetingstarttime;
            public String meetingendtime;
            public String meetingprovince;
            public String meetingcity;
            public String meetingzone;
            public String meetingaddr;
            public String meetingplace;
            public String meetingstatus;
            public String participantlist;
            public String originatoruserid;
        }

        public  class MeetingoriginatorinfoBean {
            public String userid;
            public String headpicture;
            public String name;
            public String company;
            public String jobposition;
            public String persontypeicon;
        }

        public  class ParticipantlistBean {
            public int participantnumber;
            public String participantlist;
        }
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

    public  class MeetingInfo{
        public String meetingitems;
        public String meetingsummary;
        public String meetingpicturenumber;
    }

    public  class MeetingSignInfo{
        public String signnumber;
    }

}
