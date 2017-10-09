package com.example.programmer.tbeacloudbusiness.activity.user.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by programmer on 2017/10/9.
 */

public class UpdateResponseModel {

    public VersioninfoBean versioninfo;

    public  class VersioninfoBean {
        public String tipswitch;
        public String upgradedescription;
        public String mustupgrade;
        public int versioncode;
        public String versionname;
        public String jumpurl;
    }
}
