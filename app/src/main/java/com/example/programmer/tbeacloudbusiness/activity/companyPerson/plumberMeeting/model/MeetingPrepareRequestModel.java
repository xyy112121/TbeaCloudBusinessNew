package com.example.programmer.tbeacloudbusiness.activity.companyPerson.plumberMeeting.model;

/**
 * Created by DELL on 2017/8/12.
 */

public class MeetingPrepareRequestModel {
    public String meetingcode;//会议编号
    public String meetingstarttime;//会议开始时间
    public String meetingendtime;//结束时间
    public String organizecompanylist;//举办单位id通过逗号分开
    public String meetingprovinceid;//选择的省
    public String meetingcityid;//选择的市
    public String meetingzoneid;//选择的区域
    public String meetingaddr;//详细地址
    public String originatoruserid;//登录用户的ID
    public String participantlist;//参与人员id通过逗号分开
    public String meetingitems;//会议安排

}
