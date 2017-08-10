package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model;

import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model.PlumberMeetingListStateResonpseModel;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;
import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * Created by programmer on 2017/8/9.
 */

public class PmScanCodeStateResponseModel extends BaseResponseModel{
    public PlumberMeetingListState data;

    public class  PlumberMeetingListState{
        public List<KeyValueBean> confirmstatuslist;

    }
}
