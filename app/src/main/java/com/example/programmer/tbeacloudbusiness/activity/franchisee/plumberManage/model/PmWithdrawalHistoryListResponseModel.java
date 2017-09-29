package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DELL on 2017/8/8.
 */

public class PmWithdrawalHistoryListResponseModel extends BaseResponseModel {
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {

        private ElectricianinfoBean electricianinfo;
        private List<TakemoneylistBean> takemoneylist;

        public ElectricianinfoBean getElectricianinfo() {
            return electricianinfo;
        }


        public List<TakemoneylistBean> getTakemoneylist() {
            return takemoneylist;
        }


        public static class ElectricianinfoBean {
            /**
             * userid : uprmrg1490497827ewrznc
             * thumbpicture : /public/upload/s_58d7e033f1a1b.jpg
             * personname : 李白3
             * persontypeicon : /public/static/imagesforapp/electrician.png
             * zone : 成都市 金牛区
             */

            private String userid;
            private String thumbpicture;
            private String personname;
            private String persontypeicon;
            private String zone;

            public String getUserid() {
                return userid;
            }


            public String getThumbpicture() {
                return thumbpicture;
            }


            public String getPersonname() {
                return personname;
            }


            public String getPersontypeicon() {
                return persontypeicon;
            }

            public String getZone() {
                return zone;
            }

        }

        public static class TakemoneylistBean {
            /**
             * takemoneyid : iunkaslkdfpp3982
             * time : 2017-05-05 18:55:57
             * zone : 旌阳区
             * money : 540.00
             */

            private String takemoneyid;
            private String time;
            private String zone;
            private String money;

            public String getTakemoneyid() {
                return takemoneyid;
            }


            public String getTime() {
                return time;
            }


            public String getZone() {
                return zone;
            }


            public String getMoney() {
                return money;
            }
        }
    }
}
