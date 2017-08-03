package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.action;

import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model.PersonManageViewResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model.PlumberManageMainListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model.PlumberMeetingListMainResonpseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model.PlumberMeetingViewResponseModel;
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
     *水电工管理列表首页接口
     name 名称搜索用
     electricianownertypeid 水电工类型由  02水电工用户类型接口获取
     zoneid 区域id
     orderitem 排序项money
     order   排序  desc  asc
     page
     pagesize
     */
    public PlumberManageMainListResponseModel getPlumberManageMainList
            (String name, String electricianownertypeid, String zoneid, String orderitem, String order, int page, int pagesize) throws Exception {
        PlumberManageMainListResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("name", name));
        pairs.add(new BasicNameValuePair("electricianownertypeid", electricianownertypeid));
        pairs.add(new BasicNameValuePair("zoneid", zoneid));
        pairs.add(new BasicNameValuePair("orderitem", orderitem));
        pairs.add(new BasicNameValuePair("order", order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN004003001000", pairs);
        model = gson.fromJson(result, PlumberManageMainListResponseModel.class);
        return model;
    }

    /**
     * 水电工管理个人主页接口
     */
    public PersonManageViewResponseModel getPersonManageView(String electricianid) throws Exception {
        PersonManageViewResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("electricianid", electricianid ));
        String result = sendRequest("TBEAYUN004003005000", pairs);
        model = gson.fromJson(result, PersonManageViewResponseModel.class);
        return model;
    }
}
