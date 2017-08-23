package com.example.programmer.tbeacloudbusiness.activity.publicUse.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DELL on 2017/8/23.
 */

public class SearchResponseModel extends BaseResponseModel {

    public DataBean data;

    public static class DataBean {
        public List<SearchresultlistBean> searchresultlist;

        public static class SearchresultlistBean {
            public String objecttype;
            public String objecttypelable;
            public String objectprimarykey;
            public String thumbpicture;
            public String name;
            public String description;
        }
    }
}
