package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model;

import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;
import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * 区域选择
 */

public class RegionSeleteResponseModel extends BaseResponseModel {
    public RegionSelete data;

    public class RegionSelete{
       public List<KeyValueBean> zonelist;
    }

}
