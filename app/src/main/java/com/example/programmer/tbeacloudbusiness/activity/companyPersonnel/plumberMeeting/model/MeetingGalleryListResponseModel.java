package com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by programmer on 2017/8/14.
 */

public class MeetingGalleryListResponseModel extends BaseResponseModel {

    public DataBean data;

    public static class DataBean {
        public MeetinginfoBean meetinginfo;
        public List<PictureBean> picturelist;

        public static class MeetinginfoBean {
            public String meetingname;
            public String primarypicture;
        }

        public class PictureBean{
            public String pictureid;
            public String thumbpictureurl;//缩略图
            public String largepictureurl;
        }
    }
}
