package com.example.programmer.tbeacloudbusiness.activity.tbea.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.example.programmer.tbeacloudbusiness.model.Condition;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品介绍 -- 获取商品类别
 */

public class CommodityModelResponseModel extends BaseResponseModel {

    public CommodityModelModel data;

    public class CommodityModelModel {
        public List<Condition> commoditymodellist;

        public CommodityModelModel() {
            commoditymodellist = new ArrayList<>();
        }
    }


}
