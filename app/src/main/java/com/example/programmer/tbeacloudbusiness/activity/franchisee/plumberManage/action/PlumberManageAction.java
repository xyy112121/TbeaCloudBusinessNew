package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.action;

import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model.PersonManageViewResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model.PlumberManageLoginDataResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model.PmMainListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model.PmScanCodeResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model.PmScanCodeStateResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model.PmSignHistoryResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model.PmWithdrawListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model.PmWithdrawalHistoryListResponseModel;
import com.example.programmer.tbeacloudbusiness.service.impl.BaseAction;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2017/8/4.
 */

public class PlumberManageAction extends BaseAction {
    /**
     * 水电工管理列表首页接口
     * name 名称搜索用
     * electricianownertypeid 水电工类型由  02水电工用户类型接口获取
     * zoneid 区域id
     * orderitem 排序项money
     * order   排序  desc  asc
     * page
     * pagesize
     */
    public PmMainListResponseModel getPlumberManageMainList
    (String fdistributorid, String name, String electricianownertypeid, String zoneid, String orderitem, String order, int page, int pagesize) throws Exception {
        PmMainListResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        if (fdistributorid != null && !"".equals(fdistributorid)) {
            pairs.add(new BasicNameValuePair("fdistributorid", fdistributorid));
        }
        pairs.add(new BasicNameValuePair("name", name));
        pairs.add(new BasicNameValuePair("electricianownertypeid", electricianownertypeid));
        pairs.add(new BasicNameValuePair("zoneid", zoneid));
        pairs.add(new BasicNameValuePair("orderitem", orderitem));
        pairs.add(new BasicNameValuePair("order", order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN004003001000", pairs);
        model = gson.fromJson(result, PmMainListResponseModel.class);
        return model;
    }

    /**
     * 水电工管理个人主页接口
     */
    public PersonManageViewResponseModel getPersonManageView(String electricianid) throws Exception {
        PersonManageViewResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("electricianid", electricianid));
        String result = sendRequest("TBEAYUN004003005000", pairs);
        model = gson.fromJson(result, PersonManageViewResponseModel.class);
        return model;
    }

    /**
     * 获取提现历史
     * electricianid  水电工id
     * startdate
     * enddate
     * orderitem    time，money
     * order
     * pagesize
     * page
     */
    public PmWithdrawalHistoryListResponseModel getPmWithdrawalHistoryList
    (String electricianid, String startdate, String enddate, String orderitem, String order, int page, int pagesize) throws Exception {
        PmWithdrawalHistoryListResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("electricianid", electricianid));
        pairs.add(new BasicNameValuePair("startdate", startdate));
        pairs.add(new BasicNameValuePair("enddate", enddate));
        pairs.add(new BasicNameValuePair("orderitem", orderitem));
        pairs.add(new BasicNameValuePair("order", order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN004003001001", pairs);
        model = gson.fromJson(result, PmWithdrawalHistoryListResponseModel.class);
        return model;
    }

    /**
     * 获取签到历史
     * electricianid  水电工id
     * meetingcode     先传空
     * zoneid          选择区域的ID
     * startdate
     * enddate
     * orderitem
     * order
     * page
     * pagesize
     */
    public PmSignHistoryResponseModel getSignHistoryList(String electricianid, String meetingcode, String zoneid, String startdate, String enddate, String orderitem, String order, int page, int pagesize) throws Exception {
        PmSignHistoryResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("electricianid", electricianid));
        pairs.add(new BasicNameValuePair("meetingcode", meetingcode));
        pairs.add(new BasicNameValuePair("zoneid", zoneid));
        pairs.add(new BasicNameValuePair("startdate", startdate));
        pairs.add(new BasicNameValuePair("enddate", enddate));
        pairs.add(new BasicNameValuePair("orderitem", orderitem));
        pairs.add(new BasicNameValuePair("order", order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN004003008000", pairs);
        model = gson.fromJson(result, PmSignHistoryResponseModel.class);
        return model;
    }

    public PmScanCodeStateResponseModel getStatus() throws Exception {
        PmScanCodeStateResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN001005001000", pairs);
        model = gson.fromJson(result, PmScanCodeStateResponseModel.class);
        return model;
    }

    /**
     * 水电工管理扫码返利-扫码
     * electricianid      水电工ID
     * startdate
     * enddate
     * confirmstatusid    状态ID
     * orderitem           time,money
     * order
     * page
     * pagesize
     */
    public PmScanCodeResponseModel getScanCodeList
    (String electricianid, String startdate, String enddate, String confirmstatusid, String orderitem, String order, int page, int pagesize) throws Exception {
        PmScanCodeResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("electricianid", electricianid));
        pairs.add(new BasicNameValuePair("startdate", startdate));
        pairs.add(new BasicNameValuePair("enddate", enddate));
        pairs.add(new BasicNameValuePair("confirmstatusid", confirmstatusid));
        pairs.add(new BasicNameValuePair("orderitem", orderitem));
        pairs.add(new BasicNameValuePair("order", order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN004003006000", pairs);
        model = gson.fromJson(result, PmScanCodeResponseModel.class);
        return model;
    }

    /**
     * 水电工管理扫码返利-提现
     * electricianid      水电工ID
     * startdate
     * enddate
     * orderitem           time,money
     * order
     * page
     * pagesize
     */
    public PmWithdrawListResponseModel getWithdrawList
    (String electricianid, String startdate, String enddate, String orderitem, String order, int page, int pagesize) throws Exception {
        PmWithdrawListResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("electricianid", electricianid));
        pairs.add(new BasicNameValuePair("startdate", startdate));
        pairs.add(new BasicNameValuePair("enddate", enddate));
        pairs.add(new BasicNameValuePair("orderitem", orderitem));
        pairs.add(new BasicNameValuePair("order", order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN004003007000", pairs);
        model = gson.fromJson(result, PmWithdrawListResponseModel.class);
        return model;
    }

    /**
     * 登录详情
     * enddate orderitem order page pagesize
     */
    public PlumberManageLoginDataResponseModel getLoginDataList
    (String startdate, String enddate, String orderitem, String order, int page, int pagesize) throws Exception {
        PlumberManageLoginDataResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("userid", MyApplication.instance.getUserId()));
        pairs.add(new BasicNameValuePair("startdate", startdate));
        pairs.add(new BasicNameValuePair("enddate", enddate));
        pairs.add(new BasicNameValuePair("orderitem", orderitem));
        pairs.add(new BasicNameValuePair("order", order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN004003009001", pairs);
        model = gson.fromJson(result, PlumberManageLoginDataResponseModel.class);
        return model;
    }

}
