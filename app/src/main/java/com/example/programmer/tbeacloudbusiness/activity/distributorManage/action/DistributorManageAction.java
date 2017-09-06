package com.example.programmer.tbeacloudbusiness.activity.distributorManage.action;

import com.example.programmer.tbeacloudbusiness.activity.distributorManage.model.DMMainListReponseModel;
import com.example.programmer.tbeacloudbusiness.activity.distributorManage.model.DMWithdrawalHistoryListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model.PmMainListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model.PmWithdrawalHistoryListResponseModel;
import com.example.programmer.tbeacloudbusiness.service.impl.BaseAction;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by programmer on 2017/9/6.
 */

public class DistributorManageAction extends BaseAction {
    /**
     * 分销商管理 -- 分销商列表
     * zoneid   startdate   enddate   orderitem   order    pagesize    page
     */
    public DMMainListReponseModel getMainList
    (String zoneid, String startdate, String enddate, String orderitem, String order, int page, int pagesize) throws Exception {
        DMMainListReponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("startdate", startdate));
        pairs.add(new BasicNameValuePair("enddate", enddate));
        pairs.add(new BasicNameValuePair("zoneid", zoneid));
        pairs.add(new BasicNameValuePair("orderitem", orderitem));
        pairs.add(new BasicNameValuePair("order", order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN004005001000", pairs);
        model = gson.fromJson(result, DMMainListReponseModel.class);
        return model;
    }

    /**
     * 总经销商-扫码返利-提现数据-用户历史-分销商
     * distributorid    startdate   enddate    orderitem   order    pagesize    page
     */
    public DMWithdrawalHistoryListResponseModel getDmWithdrawalHistoryList
    (String distributorid, String startdate, String enddate, String orderitem, String order, int page, int pagesize) throws Exception {
        DMWithdrawalHistoryListResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("distributorid", distributorid));
        pairs.add(new BasicNameValuePair("startdate", startdate));
        pairs.add(new BasicNameValuePair("enddate", enddate));
        pairs.add(new BasicNameValuePair("orderitem", orderitem));
        pairs.add(new BasicNameValuePair("order", order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN004005002000", pairs);
        model = gson.fromJson(result, DMWithdrawalHistoryListResponseModel.class);
        return model;
    }


}
