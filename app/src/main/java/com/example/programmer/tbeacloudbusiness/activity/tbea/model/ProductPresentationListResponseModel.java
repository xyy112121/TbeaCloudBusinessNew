package com.example.programmer.tbeacloudbusiness.activity.tbea.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品介绍
 */

public class ProductPresentationListResponseModel extends BaseResponseModel {
    public ProductPresentationListModel data;

    public class ProductPresentationListModel {
        public List<TbMainResponseModel.Product> commoditylist;

        public ProductPresentationListModel() {
            commoditylist = new ArrayList<>();
        }
    }


}
