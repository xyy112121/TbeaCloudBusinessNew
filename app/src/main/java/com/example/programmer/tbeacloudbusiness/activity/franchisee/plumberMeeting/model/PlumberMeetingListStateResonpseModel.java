package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model;

import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;
import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * 会议列表
 */

public class PlumberMeetingListStateResonpseModel extends BaseResponseModel{
    public PlumberMeetingListState data;

    public class  PlumberMeetingListState{
        public List<KeyValueBean> meetingstatuslist;

    }
}
