package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage;

/**
 * 店铺商品列表
 */

public class CommodityManageListRequestModel {
    public String orderitem;//排序项  销量：SaleNumber 商家时间： Time 价格：Price
    public String order;//排序项 排序方式 倒序：DESC 正序：ASC， 默认倒序。
    public int page;
    public int pageSize;
}
