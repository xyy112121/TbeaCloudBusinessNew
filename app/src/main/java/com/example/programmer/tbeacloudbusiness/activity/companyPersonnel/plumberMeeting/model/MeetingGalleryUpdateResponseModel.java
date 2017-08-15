package com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * Created by programmer on 2017/8/15.
 */

public class MeetingGalleryUpdateResponseModel extends BaseResponseModel {
    public DataBean data;

    public static class DataBean {
        public PictureInfo pictureinfo;

        public class PictureInfo {
            public String picturesavenames;
        }
    }
}
