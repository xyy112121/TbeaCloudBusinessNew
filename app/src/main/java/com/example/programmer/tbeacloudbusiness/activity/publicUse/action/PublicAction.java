package com.example.programmer.tbeacloudbusiness.activity.publicUse.action;

import com.example.programmer.tbeacloudbusiness.activity.publicUse.model.NetUrlResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.publicUse.model.SearchResponseModel;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.service.impl.BaseAction;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2017/8/23.
 */

public class PublicAction extends BaseAction {

    /**
     * 获取输入搜索关键词接口
     */
    public SearchResponseModel getSearchList(String searchtype, String keyword) throws Exception {
        SearchResponseModel rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("searchitem", searchtype));
        pairs.add(new BasicNameValuePair("keyword", keyword));
        String result = sendRequest("TBEAYUN002005003000", pairs);
        rspInfo = gson.fromJson(result, SearchResponseModel.class);
        return rspInfo;
    }

    /**
     * 获取html的Url
     */
    public NetUrlResponseModel getUrl() throws Exception {
        NetUrlResponseModel rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN015001002000", pairs);
        rspInfo = gson.fromJson(result, NetUrlResponseModel.class);
        return rspInfo;
    }
}
