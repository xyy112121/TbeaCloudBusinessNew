package com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.model.info;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DELL on 2017/9/26.
 */

public class HaveFinishedResponseModel {

    public ElectricalcheckinfoBean electricalcheckinfo;

    public static class ElectricalcheckinfoBean {

        public String electricalcheckid;//检测Id
        public String subscribecode;//预约编号
        public String finishtime;//完工日期
        public String checkstatusid;
        public String checkstatus;//预约状态
        public String assignid;
        public String assigntime;//派单时间
        public String accepttime;
        public String uploadtime;
        public String electricianid;
        public String thumbpicture;//检测人员头像
        public String name;//检测人员姓名
        public String info;//职位
        public String checkresultid;//检测结果Id
        public ElectricalcheckinfoBean electricalcheckinfo;

    }
}
