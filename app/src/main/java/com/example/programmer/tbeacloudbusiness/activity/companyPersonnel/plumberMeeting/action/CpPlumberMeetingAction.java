package com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.action;

import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.FranchiserSelectListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.MeetingGalleryListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.MeetingGalleryUpdateResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.MeetingPrepareMesResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.MeetingPrepareRequestModel;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.MeetingPrepareResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.ParticipantSelectlListAllResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.ParticipantSelectlListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model.PlumberMeetingListMainResonpseModel;
import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.service.impl.BaseAction;
import com.luck.picture.lib.entity.LocalMedia;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 获取参与人员(只是参与)
     * @return
     * @throws Exception
     */
    public ParticipantSelectlListResponseModel getParticipantList(String meetingid) throws Exception {
        ParticipantSelectlListResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("meetingid", meetingid));
        String result = sendRequest("TBEAYUN004004003001", pairs);
        model = gson.fromJson(result, ParticipantSelectlListResponseModel.class);
        return model;
    }

    /**
     * 获取参与人员全部
     * @return
     * @throws Exception
     */
    public ParticipantSelectlListAllResponseModel getParticipantListAll() throws Exception {
        ParticipantSelectlListAllResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("companyid", null));
        pairs.add(new BasicNameValuePair("userworkrole", "market"));
        String result = sendRequest("TBEAYUN001004003000", pairs);
        model = gson.fromJson(result, ParticipantSelectlListAllResponseModel.class);
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
        pairs.add(new BasicNameValuePair("meetingprovince", model.meetingprovince));
        pairs.add(new BasicNameValuePair("meetingcity", model.meetingcity));
        pairs.add(new BasicNameValuePair("meetingzone", model.meetingzone));
        pairs.add(new BasicNameValuePair("meetingaddr", model.meetingaddr));
        pairs.add(new BasicNameValuePair("originatoruserid", model.originatoruserid));
        pairs.add(new BasicNameValuePair("participantlist", model.participantlist));
        pairs.add(new BasicNameValuePair("meetingitems", model.meetingitems));
        String result = sendRequest("TBEAYUN008003002000", pairs);
        resultModel = gson.fromJson(result, MeetingPrepareResponseModel.class);
        return resultModel;
    }


    /**
     * 删除会议
     */
    public BaseResponseModel deleteMeeting(String meetingid) throws Exception {
        BaseResponseModel resultModel;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("meetingid", meetingid));
        String result = sendRequest("TBEAYUN008003005000", pairs);
        resultModel = gson.fromJson(result, BaseResponseModel.class);
        return resultModel;
    }

    /**
     * 保存会议纪要
     */
    public BaseResponseModel saveSummary(String meetingid,String meetingcontent ) throws Exception {
        BaseResponseModel resultModel;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("meetingid", meetingid));
        pairs.add(new BasicNameValuePair("meetingcontent", meetingcontent));
        String result = sendRequest("TBEAYUN008003003000", pairs);
        resultModel = gson.fromJson(result, BaseResponseModel.class);
        return resultModel;
    }

    /**
     * 获取现场图片
     */
    public MeetingGalleryListResponseModel getGalleryList(String meetingid ) throws Exception {
        MeetingGalleryListResponseModel resultModel;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("meetingid", meetingid));
        String result = sendRequest("TBEAYUN004004003004", pairs);
        resultModel = gson.fromJson(result, MeetingGalleryListResponseModel.class);
        return resultModel;
    }


    /**
     * 现场图片上传
     */
    public MeetingGalleryUpdateResponseModel uploadImage(List<LocalMedia> list) throws Exception {
        MeetingGalleryUpdateResponseModel model;
        Map<String, String> paramsIn = new HashMap<>();
        Map<String, String> fileIn = new HashMap<>();
        for(int i = 0;i< list.size();i++){
            if(list.get(i).getCompressPath() != null){
                fileIn.put("file"+i, list.get(i).getCompressPath());
            }else {
                fileIn.put("file"+i, list.get(i).getPath());
            }

        }
        String result = uploadImage("TBEAYUN001007001000", paramsIn, fileIn);
        model = gson.fromJson(result, MeetingGalleryUpdateResponseModel.class);
        return model;
    }

    /**
     * 现场图片路径上传
     * primarypictureindex 设置主图的图片
     */
    public ResponseInfo uploadGallery(String meetingid, String picturetitle, String pictures, int primarypictureindex) throws Exception {
        ResponseInfo resultModel;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("meetingid", meetingid));
        pairs.add(new BasicNameValuePair("picturetitle", picturetitle));
        pairs.add(new BasicNameValuePair("pictures", pictures));
        pairs.add(new BasicNameValuePair("primarypictureindex", String.valueOf(primarypictureindex)));
        String result = sendRequest("TBEAYUN008003003001", pairs);
        resultModel = gson.fromJson(result, ResponseInfo.class);
        return resultModel;
    }

    /**
     * 删除现场图片
     */
    public ResponseInfo deleteGallery(String pictureid) throws Exception {
        ResponseInfo rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("pictureid", pictureid));
        String result = sendRequest("TBEAYUN008003003002", pairs);
        rspInfo = gson.fromJson(result, ResponseInfo.class);
        return rspInfo;
    }

}
