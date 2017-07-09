package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.example.programmer.tbeacloudbusiness.model.Condition;

import java.util.List;

/**
 * Created by DELL on 2017/7/9.
 */

public class ScanCodeTypeSelectReponseModel extends BaseResponseModel {

    public ScanCodeTypeSelect data;

    public class ScanCodeTypeSelect {
        public List<Condition> commoditymodellist;
    }


}
