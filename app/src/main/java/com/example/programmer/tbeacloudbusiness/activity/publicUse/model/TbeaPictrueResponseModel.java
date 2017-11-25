package com.example.programmer.tbeacloudbusiness.activity.publicUse.model;

import com.example.programmer.tbeacloudbusiness.activity.user.model.PicturelistBean;
import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * Created by DELL on 2017/11/24.
 */

public class TbeaPictrueResponseModel extends BaseResponseModel {

    public DataBean data;

    public class DataBean {
        public List<PicturelistBean> picturelist;

    }
}
