package com.example.programmer.tbeacloudbusiness.activity.check.tbws.model.info;



import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * Created by DELL on 2017/9/17.
 */

public class TestDetailsResponseModel extends BaseResponseModel {

    public DataBean data;

    public  class DataBean {
        public CheckdescriptioninfoBean checkdescriptioninfo;
        public List<ElectricalcheckworklistBean> electricalcheckworklist;

        public  class CheckdescriptioninfoBean {
            public String description;
        }

        public  class ElectricalcheckworklistBean {
            public String electricalcheckworkid;
            public String checkitemid;
            public String checkitem;
            public String checkresult;
        }
    }
}
