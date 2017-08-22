package com.example.programmer.tbeacloudbusiness.activity.user.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

/**
 * Created by programmer on 2017/8/22.
 */

public class RegisterRequestModel extends BaseResponseModel {
    public String mobilenumber;//手机号码
    public String password;//用户密码
    public String verifycode;//验证码
    public String usertypeid;//用户类型
}
