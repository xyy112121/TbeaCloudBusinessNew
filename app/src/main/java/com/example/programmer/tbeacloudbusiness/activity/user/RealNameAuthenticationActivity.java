package com.example.programmer.tbeacloudbusiness.activity.user;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;

/**
 * Created by programmer on 2017/6/16.
 */

public class RealNameAuthenticationActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_name_authentication);
        initTopbar("实名认证");
//        listener();
    }

//    private void listener(){
//        findViewById(R.id.)
//    }

    /**
     * 显示选择拍照，图库
     */
//    private void showImage(){
//        List<String> photoOperationList = new ArrayList<>();
//        photoOperationList.add("拍照上传");
//        photoOperationList.add("本地上传");
//        CustomPopWindow1 popWindow1 = new CustomPopWindow1(mContext);
//        popWindow1.init(parentLayout,R.layout.pop_window_header1,R.layout.pop_window_tv,photoOperationList);
//        popWindow1.setItemClick(new CustomPopWindow1.ItemClick() {
//            @Override
//            public void onItemClick(String text) {
//
//            }
//        });
//    }
}
