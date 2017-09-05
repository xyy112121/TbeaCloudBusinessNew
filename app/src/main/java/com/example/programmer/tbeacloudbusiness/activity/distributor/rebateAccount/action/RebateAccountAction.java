package com.example.programmer.tbeacloudbusiness.activity.distributor.rebateAccount.action;

import com.example.programmer.tbeacloudbusiness.activity.distributor.rebateAccount.model.RebateAccountInfoResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.distributor.rebateAccount.model.RebateAccountListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.distributor.rebateAccount.model.WithdrawCashViewResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.user.model.MyMainResponseModel;
import com.example.programmer.tbeacloudbusiness.http.MD5Util;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.service.impl.BaseAction;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by programmer on 2017/7/20.
 */

public class RebateAccountAction extends BaseAction {

    /**
     * 获取我的返利账户列表
     * @return
     * @throws Exception
     */
    public RebateAccountListResponseModel getRebateAccountList() throws Exception {
        RebateAccountListResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN005002007000", pairs);
        model = gson.fromJson(result, RebateAccountListResponseModel.class);
        return model;
    }

    /**
     * 获取返利详细
     * @return
     * @throws Exception
     */
//    public RebateAccountInfoResponseModel getRebateAccountInfo() throws Exception {
//        RebateAccountInfoResponseModel model;
//        List<NameValuePair> pairs = new ArrayList<>();
//        String result = sendRequest("TBEAYUN005002008000", pairs);
//        model = gson.fromJson(result, RebateAccountInfoResponseModel.class);
//        return model;
//    }

    /**
     * 获取返利详细
     * @return
     * @throws Exception
     */
    public ResponseInfo getRebateAccountInfo() throws Exception {
        ResponseInfo model;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN005002008000", pairs);
        model = gson.fromJson(result, ResponseInfo.class);
        return model;
    }

    /**
     * 生成二维码（提现）
     */
    public WithdrawCashViewResponseModel getWithdrawCashView(String money,String distributorid) throws Exception {
        WithdrawCashViewResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("money", money));
        pairs.add(new BasicNameValuePair("distributorid", distributorid));
        String result = sendRequest("TBEAYUN005002009000", pairs);
        model = gson.fromJson(result, WithdrawCashViewResponseModel.class);
        return model;
    }
}
