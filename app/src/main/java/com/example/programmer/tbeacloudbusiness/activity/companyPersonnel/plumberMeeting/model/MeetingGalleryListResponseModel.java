package com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model;

import com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.model.info.MyPictureListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.user.model.PicturelistBean;
import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by programmer on 2017/8/14.
 */

public class MeetingGalleryListResponseModel extends BaseResponseModel {
    public DataBean data;

    public  class DataBean {
        public MeetinginfoBean meetinginfo;
        public List<PicturelistBean> picturelist;

        public  class MeetinginfoBean {
            public String meetingname;
            public String primarypicture;
        }
    }
}
