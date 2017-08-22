package com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

/**
 * Created by programmer on 2017/8/15.
 */

public class MeetingGalleryUpdateResponseModel extends BaseResponseModel {

    public DataBean data;

    public static class DataBean {
        public PictureinfoBean pictureinfo;

        public static class PictureinfoBean {
            public String picturesavenames;
        }
    }
}
