package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by programmer on 2017/8/24.
 */

public class DsMainResponseModel{

    public HomepageInfoBean HomepageInfo;

    public static class HomepageInfoBean {
        public String ShouldReveivedMoney;
        public int OSTBAL;
        public String PersonPicture;
        public List<Announcement> Announcement;
    }

    public class Announcement {
        public String Id;
        public String Name;
    }
}
