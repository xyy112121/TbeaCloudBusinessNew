package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * Created by DELL on 2017/8/8.
 */

public class PmSignHistoryResponseModel extends BaseResponseModel {

    /**
     * data : {"electricianinfo":{"userid":"iunkaslkdfpp3982","thumbpicture":null,"personname":null,"cityzone":" ","zone":"","companyname":" ","persontypeicon":null,"personorcompany":"1","totleattendtimes":"36"},"attendmeetinglist":[{"attendid":"asdfasfadasdf16","meetingcode":"sh838170721","zone":"旌阳区","attendtime":"2017-07-21 12:32:32"},{"attendid":"asdfasfadasdf21","meetingcode":"sh838170616","zone":"旌阳区","attendtime":"2017-06-16 12:32:32"}]}
     * success : true
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * electricianinfo : {"userid":"iunkaslkdfpp3982","thumbpicture":null,"personname":null,"cityzone":" ","zone":"","companyname":" ","persontypeicon":null,"personorcompany":"1","totleattendtimes":"36"}
         * attendmeetinglist : [{"attendid":"asdfasfadasdf16","meetingcode":"sh838170721","zone":"旌阳区","attendtime":"2017-07-21 12:32:32"},{"attendid":"asdfasfadasdf21","meetingcode":"sh838170616","zone":"旌阳区","attendtime":"2017-06-16 12:32:32"}]
         */

        private ElectricianinfoBean electricianinfo;
        private List<AttendmeetinglistBean> attendmeetinglist;

        public ElectricianinfoBean getElectricianinfo() {
            return electricianinfo;
        }

        public void setElectricianinfo(ElectricianinfoBean electricianinfo) {
            this.electricianinfo = electricianinfo;
        }

        public List<AttendmeetinglistBean> getAttendmeetinglist() {
            return attendmeetinglist;
        }

        public void setAttendmeetinglist(List<AttendmeetinglistBean> attendmeetinglist) {
            this.attendmeetinglist = attendmeetinglist;
        }

        public static class ElectricianinfoBean {
            /**
             * userid : iunkaslkdfpp3982
             * thumbpicture : null
             * personname : null
             * cityzone :
             * zone :
             * companyname :
             * persontypeicon : null
             * personorcompany : 1
             * totleattendtimes : 36
             */

            private String userid;
            private Object thumbpicture;
            private Object personname;
            private String cityzone;
            private String zone;
            private String companyname;
            private Object persontypeicon;
            private String personorcompany;
            private String totleattendtimes;

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public Object getThumbpicture() {
                return thumbpicture;
            }

            public void setThumbpicture(Object thumbpicture) {
                this.thumbpicture = thumbpicture;
            }

            public Object getPersonname() {
                return personname;
            }

            public void setPersonname(Object personname) {
                this.personname = personname;
            }

            public String getCityzone() {
                return cityzone;
            }

            public void setCityzone(String cityzone) {
                this.cityzone = cityzone;
            }

            public String getZone() {
                return zone;
            }

            public void setZone(String zone) {
                this.zone = zone;
            }

            public String getCompanyname() {
                return companyname;
            }

            public void setCompanyname(String companyname) {
                this.companyname = companyname;
            }

            public Object getPersontypeicon() {
                return persontypeicon;
            }

            public void setPersontypeicon(Object persontypeicon) {
                this.persontypeicon = persontypeicon;
            }

            public String getPersonorcompany() {
                return personorcompany;
            }

            public void setPersonorcompany(String personorcompany) {
                this.personorcompany = personorcompany;
            }

            public String getTotleattendtimes() {
                return totleattendtimes;
            }

            public void setTotleattendtimes(String totleattendtimes) {
                this.totleattendtimes = totleattendtimes;
            }
        }

        public static class AttendmeetinglistBean {
            /**
             * attendid : asdfasfadasdf16
             * meetingcode : sh838170721
             * zone : 旌阳区
             * attendtime : 2017-07-21 12:32:32
             */

            private String attendid;
            private String meetingcode;
            private String zone;
            private String attendtime;

            public String getAttendid() {
                return attendid;
            }

            public void setAttendid(String attendid) {
                this.attendid = attendid;
            }

            public String getMeetingcode() {
                return meetingcode;
            }

            public void setMeetingcode(String meetingcode) {
                this.meetingcode = meetingcode;
            }

            public String getZone() {
                return zone;
            }

            public void setZone(String zone) {
                this.zone = zone;
            }

            public String getAttendtime() {
                return attendtime;
            }

            public void setAttendtime(String attendtime) {
                this.attendtime = attendtime;
            }
        }
    }
}
