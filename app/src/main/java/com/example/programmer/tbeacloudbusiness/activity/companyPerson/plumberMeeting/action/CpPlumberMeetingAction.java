package com.example.programmer.tbeacloudbusiness.activity.companyPerson.plumberMeeting.action;

import com.example.programmer.tbeacloudbusiness.activity.companyPerson.plumberMeeting.model.FranchiserSelectListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.companyPerson.plumberMeeting.model.MeetingPrepareRequestModel;
import com.example.programmer.tbeacloudbusiness.activity.companyPerson.plumberMeeting.model.MeetingPrepareMesResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.companyPerson.plumberMeeting.model.MeetingPrepareResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.companyPerson.plumberMeeting.model.ParticipantSelectlListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model.PlumberMeetingListMainResonpseModel;
import com.example.programmer.tbeacloudbusiness.service.impl.BaseAction;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2017/8/12.
 */

public class CpPlumberMeetingAction extends BaseAction {

    /**
     * 公司人员-水电工会议首页接口
     * meetingcode   之前 为搜索预留的，先传空
     * zoneid        区域ID和其它选择相同
     * meetingstatusid   会议状态id
     * meetingstarttime  可用meetingtime，meetingcode
     * meetingendtime
     * orderitem
     * order
     * page
     * pagesize
     *
     * @return
     * @throws Exception
     */
    public PlumberMeetingListMainResonpseModel getPlumberMeetingListAll
    (String meetingcode, String zoneid, String meetingstatusid, String meetingstarttime, String meetingendtime, String orderitem, String order, int page, int pagesize) throws Exception {
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
        String result = sendRequest("TBEAYUN008003001000", pairs);
        model = gson.fromJson(result, PlumberMeetingListMainResonpseModel.class);
        return model;
    }

    /**
     * 公司人员-水电工会议发起会议准备接口
     * 会议编号和发起人员信息获取
     */
    public MeetingPrepareMesResponseModel getPrepareData() throws Exception {
        MeetingPrepareMesResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN008003002001", pairs);
        model = gson.fromJson(result, MeetingPrepareMesResponseModel.class);
        return model;
    }

    /**
     * 举办单位接口
     * provinceidwithuser   传 1
     * cityidwithuser  传 1
     */
    public FranchiserSelectListResponseModel getPrepareDistributorList() throws Exception {
        FranchiserSelectListResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("provinceidwithuser", "1"));
        pairs.add(new BasicNameValuePair("cityidwithuser", "1"));
        String result = sendRequest("TBEAYUN001004002000", pairs);
        model = gson.fromJson(result, FranchiserSelectListResponseModel.class);
        return model;
    }

    /**
     * 获取参与人员
     * @return
     * @throws Exception
     */
    public ParticipantSelectlListResponseModel getParticipantList() throws Exception {
        ParticipantSelectlListResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("companyid", null));
        pairs.add(new BasicNameValuePair("userworkrole", "market"));
        String result = sendRequest("TBEAYUN001004003001", pairs);
        model = gson.fromJson(result, ParticipantSelectlListResponseModel.class);
        return model;
    }

    /**
     * 获取参与人员全部
     * @return
     * @throws Exception
     */
    public ParticipantSelectlListResponseModel getParticipantListAll() throws Exception {
        ParticipantSelectlListResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("companyid", null));
        pairs.add(new BasicNameValuePair("userworkrole", "market"));
        String result = sendRequest("TBEAYUN001004003000", pairs);
        model = gson.fromJson(result, ParticipantSelectlListResponseModel.class);
        return model;
    }

    /**
     * 公司人员-发会议
     * meetingcode   会议编号
     * meetingstarttime  会议开始时间
     * meetingendtime    结束时间
     * organizecompanylist    举办单位id通过逗号分开
     * meetingprovinceid   选择的省
     * meetingcityid    选择的市
     * meetingzoneid   选择的区域
     * meetingaddr     详细地址
     * originatoruserid  登录用户的ID，头部信息里面已传，这里也加上
     * participantlist   参与人员id通过逗号分开
     * meetingitems   会议安排
     */
    public MeetingPrepareResponseModel initiateMeeting(MeetingPrepareRequestModel model) throws Exception {
        MeetingPrepareResponseModel resultModel;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("meetingcode", model.meetingcode));
        pairs.add(new BasicNameValuePair("meetingstarttime", model.meetingstarttime));
        pairs.add(new BasicNameValuePair("meetingendtime", model.meetingendtime));
        pairs.add(new BasicNameValuePair("organizecompanylist", model.organizecompanylist));
        pairs.add(new BasicNameValuePair("meetingprovinceid", model.meetingprovinceid));
        pairs.add(new BasicNameValuePair("meetingcityid", model.meetingcityid));
        pairs.add(new BasicNameValuePair("meetingzoneid", model.meetingzoneid));
        pairs.add(new BasicNameValuePair("meetingaddr", model.meetingaddr));
        pairs.add(new BasicNameValuePair("originatoruserid", model.originatoruserid));
        pairs.add(new BasicNameValuePair("participantlist", model.participantlist));
        pairs.add(new BasicNameValuePair("meetingitems", model.meetingitems));
        String result = sendRequest("TBEAYUN008003002000", pairs);
        resultModel = gson.fromJson(result, MeetingPrepareResponseModel.class);
        return resultModel;
    }


}
