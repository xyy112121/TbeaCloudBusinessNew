package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model;

import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;
import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * Created by programmer on 2017/7/10.
 */

public class PayStatusResponseModel extends BaseResponseModel {
    public PayStatus data;

    public class PayStatus{
        public List<KeyValueBean> paystatuslist;
    }
}
