package com.example.programmer.tbeacloudbusiness.activity.my.main.model;

import java.util.List;

/**
 * 实名认证失败原因
 */

public class RealNameAuthenticationFailResponseModel {

    public List<FailedreasonBean> failedreasonlist;

    public class FailedreasonBean {
        public String reason;
    }
}
