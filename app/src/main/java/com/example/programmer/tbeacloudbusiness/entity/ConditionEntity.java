package com.example.programmer.tbeacloudbusiness.entity;

/**
 * Created by programmer on 2017/5/29.
 */

public class ConditionEntity {
    private String resultCode;
    private String resultInfo;
    private ConditionInfoEntity info;
    public String getResultCode() {
        return resultCode;
    }
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
    public String getResultInfo() {
        return resultInfo;
    }
    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }
    public ConditionInfoEntity getInfo() {
        return info;
    }
    public void setInfo(ConditionInfoEntity info) {
        this.info = info;
    }
}
