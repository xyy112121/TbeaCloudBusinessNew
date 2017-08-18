package com.example.programmer.tbeacloudbusiness.activity.my.main.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 *关注的店铺
 */

public class AttentionStoreResponseModel extends BaseResponseModel {
    public DataBean data;


    public  class DataBean {
        public List<MyfocuslistBean> myfocuslist;

        public  class MyfocuslistBean {

            public String focusid;
            public String shopid;//店铺 Id
            public String thumbpicture;//缩略图
            public String name;//店铺名称
            public String usertypeicon;//店铺用户类型
            public String fansnumber;//粉丝数

            public boolean isCheck = false;//是否中
        }
    }
}
