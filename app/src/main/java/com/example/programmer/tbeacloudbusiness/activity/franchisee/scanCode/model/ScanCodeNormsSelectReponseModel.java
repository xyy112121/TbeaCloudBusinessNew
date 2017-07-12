package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.example.programmer.tbeacloudbusiness.model.Condition;

import java.util.List;

/**
 * 规格选择
 */

public class ScanCodeNormsSelectReponseModel extends BaseResponseModel {

    public ScanCodeTypeSelect data;

    public class ScanCodeTypeSelect {
        public List<Condition> commodityspecificationlist;
    }


}
