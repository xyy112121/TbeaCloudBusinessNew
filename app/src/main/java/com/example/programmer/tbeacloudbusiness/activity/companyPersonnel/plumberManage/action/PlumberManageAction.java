package com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberManage.action;

import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberManage.model.DranchiseeSeleteResonpseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model.PmMainListResponseModel;
import com.example.programmer.tbeacloudbusiness.service.impl.BaseAction;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by programmer on 2017/8/15.
 */

public class PlumberManageAction extends BaseAction {

    public DranchiseeSeleteResonpseModel getDranchiseeSeleteList
            (String orderitem, String order) throws Exception {
        DranchiseeSeleteResonpseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("orderitem", orderitem));
        pairs.add(new BasicNameValuePair("order", order));
//        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
//        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN008001002000", pairs);
        model = gson.fromJson(result, DranchiseeSeleteResonpseModel.class);
        return model;
    }
}
