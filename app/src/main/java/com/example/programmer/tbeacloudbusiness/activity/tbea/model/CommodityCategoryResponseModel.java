package com.example.programmer.tbeacloudbusiness.activity.tbea.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.example.programmer.tbeacloudbusiness.model.Condition;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品介绍 -- 获取商品类别
 */

public class CommodityCategoryResponseModel extends BaseResponseModel {

    public CommodityCategoryModel data;

    public class CommodityCategoryModel {
        public List<Condition> commoditycategorylist;

        public CommodityCategoryModel() {
            commoditycategorylist = new ArrayList<>();
        }
    }


}
