package com.example.programmer.tbeacloudbusiness.activity.distributor.scanCode.action;

import com.example.programmer.tbeacloudbusiness.activity.distributor.scanCode.model.DBScanCodeMainResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.ScanCodeRebateListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.WithdrawDepositDateResponseModel;
import com.example.programmer.tbeacloudbusiness.service.impl.BaseAction;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * 分销商-扫码返利
 */

public class DBScanCodeAction extends BaseAction{

    /**
     * 获取主页显示数据
     */
    public DBScanCodeMainResponseModel getMainData() throws Exception {
        DBScanCodeMainResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN005002001000", pairs);
        model = gson.fromJson(result, DBScanCodeMainResponseModel.class);
        return model;
    }

    /**
     * 收支明细——已支付接口
     * paystatusid  提现类型 通过TBEAYUN001003002000这个接口获取
     * payeetypeid  提现用户类型通过TBEAYUN001003001000获取
     * starttime     开始时间
     * endtime      结束时间
     * orderitem    排序项  time  money
     * order         desc  asc
     * pagesize
     * page
     */
    public WithdrawDepositDateResponseModel getWithdrawDepositDateList(String paystatusid, String payeetypeid,
                                                                       String starttime, String endtime, String orderitem, String order, int page, int pagesize) throws Exception {
        WithdrawDepositDateResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("paystatusid", paystatusid));
        pairs.add(new BasicNameValuePair("payeetypeid", "electrician"));
        pairs.add(new BasicNameValuePair("starttime", starttime));
        pairs.add(new BasicNameValuePair("endtime", endtime));
        pairs.add(new BasicNameValuePair("orderitem", orderitem));
        pairs.add(new BasicNameValuePair("order", order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN005002004000", pairs);
        model = gson.fromJson(result, WithdrawDepositDateResponseModel.class);
        return model;
    }

    /**
     *收支明细——已提现接口
     starttime
     endtime
     orderitem   使用time或者money
     order       使用asc或者desc
     pagesize
     page
     */
    public WithdrawDepositDateResponseModel getWithdrawDepositDateList1(
                                                                       String starttime, String endtime, String orderitem, String order, int page, int pagesize) throws Exception {
        WithdrawDepositDateResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("starttime", starttime));
        pairs.add(new BasicNameValuePair("endtime", endtime));
        pairs.add(new BasicNameValuePair("orderitem", orderitem));
        pairs.add(new BasicNameValuePair("order", order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN005002005000", pairs);
        model = gson.fromJson(result, WithdrawDepositDateResponseModel.class);
        return model;
    }

    /**
     * 获取提现排名
     */
    public ScanCodeRebateListResponseModel getWithdrawDepositDateList(String zoneids, String orderitem, String order, int page, int pagesize) throws Exception {
        ScanCodeRebateListResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("zoneids", zoneids));
        pairs.add(new BasicNameValuePair("orderitem", orderitem));
        pairs.add(new BasicNameValuePair("order", order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN005002002000", pairs);
        model = gson.fromJson(result, ScanCodeRebateListResponseModel.class);
        return model;
    }
}
