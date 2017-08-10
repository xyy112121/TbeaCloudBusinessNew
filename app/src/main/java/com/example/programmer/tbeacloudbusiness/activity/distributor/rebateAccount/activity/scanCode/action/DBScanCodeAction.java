package com.example.programmer.tbeacloudbusiness.activity.distributor.rebateAccount.activity.scanCode.action;

import com.example.programmer.tbeacloudbusiness.activity.distributor.rebateAccount.activity.scanCode.model.DBScanCodeMainResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.ScanCodeMainResponseModel;
import com.example.programmer.tbeacloudbusiness.service.impl.BaseAction;

import org.apache.http.NameValuePair;

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
}
