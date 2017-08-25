package com.example.programmer.tbeacloudbusiness.activity.tbea.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by programmer on 2017/8/24.
 */

public class ContactInfoResponseModel extends BaseResponseModel {

    public DataBean data;

    public  class DataBean {
        public ContactinfoBean contactinfo;

        public  class ContactinfoBean {
            public String name;
            public String address;
            public String zipcode;
            public String platformservicetel;
            public String fax;
            public String delantel;
            public String tbeatel;
        }
    }
}
