package com.example.programmer.tbeacloudbusiness.activity.distributor.rebateAccount.action;

import com.example.programmer.tbeacloudbusiness.activity.distributor.rebateAccount.model.RebateAccountListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.distributor.rebateAccount.model.WithdrawCashViewResponseModel;
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
     *
     * @return
     * @throws Exception
     */
    public RebateAccountListResponseModel getRebateAccountList(int page, int pagesize) throws Exception {
        RebateAccountListResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN005002007000", pairs);
        model = gson.fromJson(result, RebateAccountListResponseModel.class);
        return model;
    }

    /**
     * 删除提取二维码
     */
    public ResponseInfo delectTakeMoneyCodeId(String takemoneycodeid) throws Exception {
        ResponseInfo rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("takemoneyqrcodeid", takemoneycodeid));
        String result = sendRequest("TBEAYUN005002011000", pairs);
        rspInfo = gson.fromJson(result, ResponseInfo.class);
        return rspInfo;
    }


    /**
     * 根据id获取二维码
     */
    public ResponseInfo createCodeById(String takemoneyqrcodeid) throws Exception {
        ResponseInfo rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("takemoneyqrcodeid", takemoneyqrcodeid));
        String result = sendRequest("TBEAYUN005002010000", pairs);
        rspInfo = gson.fromJson(result, ResponseInfo.class);
        return rspInfo;
    }

    /**
     * 获取返利详细
     *
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
    public WithdrawCashViewResponseModel getWithdrawCashView(String money, String distributorid) throws Exception {
        WithdrawCashViewResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("money", money));
        pairs.add(new BasicNameValuePair("distributorid", distributorid));
        String result = sendRequest("TBEAYUN005002009000", pairs);
        model = gson.fromJson(result, WithdrawCashViewResponseModel.class);
        return model;
    }
}
