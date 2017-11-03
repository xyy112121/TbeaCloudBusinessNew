package com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.model.info;

import com.example.programmer.tbeacloudbusiness.activity.user.model.PicturelistBean;
import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;


import java.io.Serializable;
import java.util.List;

/**
 * Created by programmer on 2017/9/18.
 */

public class MyPictureListResponseModel extends BaseResponseModel {

    public DataBean data;

    public  class DataBean {
        public List<PicturelistBean> picturelist;
    }


}
