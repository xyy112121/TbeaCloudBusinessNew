package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.set;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DELL on 2017/8/28.
 */

public class VisualGraphResonpseModel extends BaseResponseModel {

    public DataBean data;

    public class DataBean {
        public ShoppictureinfoBean shoppictureinfo;

        public class ShoppictureinfoBean {
            public String picture;
        }
    }
}
