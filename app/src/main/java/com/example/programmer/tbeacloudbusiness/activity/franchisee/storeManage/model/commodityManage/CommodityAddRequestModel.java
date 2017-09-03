package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage;

/**
 * Created by DELL on 2017/9/3.
 */

public class CommodityAddRequestModel {
    public String commodityid;//商品 Id
    public String name;//商品名称
    public String categoryid;//商品分类
    public String moditymodelid;//型号
    public String modityspecid;//规格
    public String price;//价格
    public String discountmoney;//优惠金额
    public String unit;//计量单位
    public String stocknumber;//库存数量
    public String description;//描述
    public String thumblist;//商品缩略图 最多 6 张
    public String picturelist;//详情图片 最多 20 张
    public String recommended;// 是否推荐商品 1：是 0：不
}
