package com.example.programmer.tbeacloudbusiness.activity.check.tbws.model.info;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 2017/9/26.
 */

public class SendOrdersPersonSelectResponseModel  extends BaseResponseModel{
    public DataBean data;

    public class DataBean {
        public List<ElectricianList> electricianlist;

        public class ElectricianList implements Serializable {
            public String electricianid;
            public String thumbpicture;
            public String info;
            public String name;
        }
    }
}
