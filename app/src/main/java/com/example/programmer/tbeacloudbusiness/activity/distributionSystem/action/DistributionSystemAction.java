package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action;

import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.DsMainResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.tbea.model.ContactInfoResponseModel;
import com.example.programmer.tbeacloudbusiness.service.impl.BaseAction;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * 分销系统.
 */

public class DistributionSystemAction extends BaseAction {

    public DsMainResponseModel getMainList() throws Exception {
        DsMainResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest1("TBEA15000000", pairs);
        model = gson.fromJson(result, DsMainResponseModel.class);
        return model;
    }
}
