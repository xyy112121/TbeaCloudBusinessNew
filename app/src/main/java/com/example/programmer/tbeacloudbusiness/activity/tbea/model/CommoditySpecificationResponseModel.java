package com.example.programmer.tbeacloudbusiness.activity.tbea.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.example.programmer.tbeacloudbusiness.model.Condition;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品介绍--产品规格
 */

public class CommoditySpecificationResponseModel extends BaseResponseModel {

    public CommoditySpecificationModel data;

    public class CommoditySpecificationModel {
        public List<Condition> commodityspecificationlist;

        public CommoditySpecificationModel() {
            commodityspecificationlist = new ArrayList<>();
        }
    }
}
