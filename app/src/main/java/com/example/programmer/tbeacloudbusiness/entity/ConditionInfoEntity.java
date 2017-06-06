package com.example.programmer.tbeacloudbusiness.entity;

import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;

import java.util.List;

/**
 * Created by programmer on 2017/5/29.
 */

public class ConditionInfoEntity {
    List<KeyValueBean> dateType;//时间
    List<KeyValueBean> moneyType;//金额
    List<KeyValueBean> numberType;//数量
    List<KeyValueBean> stateType;//激活状态
//    List<KeyValueBean> sortType;

    public List<KeyValueBean> getDateType() {
        return dateType;
    }

    public void setDateType(List<KeyValueBean> dateType) {
        this.dateType = dateType;
    }

    public List<KeyValueBean> getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(List<KeyValueBean> moneyType) {
        this.moneyType = moneyType;
    }

    public List<KeyValueBean> getNumberType() {
        return numberType;
    }

    public void setNumberType(List<KeyValueBean> numberType) {
        this.numberType = numberType;
    }

    public List<KeyValueBean> getStateType() {
        return stateType;
    }

//    public void setStateType(List<KeyValueBean> stateType) {
//        this.stateType = stateType;
//    }
//
//    public List<KeyValueBean> getSortType() {
//        return sortType;
//    }

//    public void setSortType(List<KeyValueBean> sortType) {
//        this.sortType = sortType;
//    }
}
