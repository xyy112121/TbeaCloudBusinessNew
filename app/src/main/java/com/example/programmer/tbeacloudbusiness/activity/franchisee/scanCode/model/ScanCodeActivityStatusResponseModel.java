package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model;

import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;
import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * 已激活列表下拉项
 */

public class ScanCodeActivityStatusResponseModel extends BaseResponseModel{
    public ScanCodeActivityStatus data;

    public class ScanCodeActivityStatus{
        public List<KeyValueBean> activitystatuslist;
    }
}
