package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.action;

import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model.PlumberMeetingListMainResonpseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model.PlumberMeetingListStateResonpseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model.PlumberMeetingParticipantResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model.PlumberMeetingViewResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model.RegionSeleteResponseModel;
import com.example.programmer.tbeacloudbusiness.service.impl.BaseAction;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * 水电工会议（经销商下）
 */

public class PlumberMeetingAction extends BaseAction {

    //获取水电工会议列表
    public PlumberMeetingListStateResonpseModel getStatus() throws Exception {
        PlumberMeetingListStateResonpseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN001005002000", pairs);
        model = gson.fromJson(result, PlumberMeetingListStateResonpseModel.class);
        return model;
    }

    /**
     *水电工会议列表
     meetingcode 查询的时候用
     zoneid  区域id
     meetingstatusid 会议状态id
     meetingstarttime 会议开始时间  查询时用
     meetingendtime  会议结束时间 查询时用
     orderitem  排序项 meetingtime，meetingcode
     order   排序 desc  asc
     page
     pagesize
     */
    public PlumberMeetingListMainResonpseModel getPlumberMeetingMainList
            (String meetingcode,String zoneid ,String meetingstatusid ,String meetingstarttime, String meetingendtime,String orderitem, String order, int page, int pagesize) throws Exception {
        PlumberMeetingListMainResonpseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("meetingcode", meetingcode));
        pairs.add(new BasicNameValuePair("zoneid", zoneid));
        pairs.add(new BasicNameValuePair("meetingstatusid", meetingstatusid));
        pairs.add(new BasicNameValuePair("meetingstarttime", meetingstarttime));
        pairs.add(new BasicNameValuePair("meetingendtime", meetingendtime));
        pairs.add(new BasicNameValuePair("orderitem", orderitem));
        pairs.add(new BasicNameValuePair("order", order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN004004001000", pairs);
        model = gson.fromJson(result, PlumberMeetingListMainResonpseModel.class);
        return model;
    }

    /**
     * 水电工会议列表
     meetingcode 查询的时候用
     zoneid  区域id
     meetingstatusid 会议状态id
     meetingstarttime 会议开始时间  查询时用
     meetingendtime  会议结束时间 查询时用
     orderitem  排序项 meetingtime，meetingcode
     order   排序 desc  asc
     page
     pagesize
     */
    public PlumberMeetingListMainResonpseModel getPlumberMeetingListAll
    (String meetingcode,String zoneid ,String meetingstatusid ,String meetingstarttime, String meetingendtime,String orderitem, String order, int page, int pagesize) throws Exception {
        PlumberMeetingListMainResonpseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("meetingcode", meetingcode));
        pairs.add(new BasicNameValuePair("zoneid", zoneid));
        pairs.add(new BasicNameValuePair("meetingstatusid", meetingstatusid));
        pairs.add(new BasicNameValuePair("meetingstarttime", meetingstarttime));
        pairs.add(new BasicNameValuePair("meetingendtime", meetingendtime));
        pairs.add(new BasicNameValuePair("orderitem", orderitem));
        pairs.add(new BasicNameValuePair("order", order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN004004002000", pairs);
        model = gson.fromJson(result, PlumberMeetingListMainResonpseModel.class);
        return model;
    }

    /**
     * 获取区域
     */
    public RegionSeleteResponseModel getRegionSelete() throws Exception {
        RegionSeleteResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN001001003000", pairs);
        model = gson.fromJson(result, RegionSeleteResponseModel.class);
        return model;
    }

    /**
     * 获取会议详细
     */
    public PlumberMeetingViewResponseModel getPlumberMeetingView(String meetingid )throws  Exception {
        PlumberMeetingViewResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("meetingid", meetingid ));
        String result = sendRequest("TBEAYUN004004003000", pairs);
        model = gson.fromJson(result, PlumberMeetingViewResponseModel.class);
        return model;
    }

    /**
     * 获取会议参与人员
     */
    public PlumberMeetingParticipantResponseModel getParticipantList(String meetingid )throws  Exception {
        PlumberMeetingParticipantResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("meetingid", meetingid ));
        String result = sendRequest("TBEAYUN004004003001", pairs);
        model = gson.fromJson(result, PlumberMeetingParticipantResponseModel.class);
        return model;
    }
}
