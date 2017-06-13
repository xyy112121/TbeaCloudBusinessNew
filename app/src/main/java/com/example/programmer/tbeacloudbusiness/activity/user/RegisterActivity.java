package com.example.programmer.tbeacloudbusiness.activity.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.component.picker.CustomFilePicker;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.example.zhouwei.library.CustomPopWindow;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.popup.BottomPopup;

/**
 * Created by programmer on 2017/6/7.
 */

public class RegisterActivity extends Activity implements View.OnClickListener{
    private Context mContext;
    private View parentLayout;
    List<String> userTypeList = new ArrayList<>();
    private CustomPopWindow mCustomPopWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        parentLayout = findViewById(R.id.register_layout);
        userTypeList.add("分销商");
        userTypeList.add("其他商家");
        userTypeList.add("物流人员");
        mContext = this;
        listener();
    }

    private void listener(){
        findViewById(R.id.register_btn).setOnClickListener(this);
        findViewById(R.id.register_user_type).setOnClickListener(this);//用户类型选择
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_btn:
                Intent intent = new Intent(mContext,RegisterSuccessActivity.class);
                startActivity(intent);
                break;
            case R.id.register_user_type:
                LinearLayout contentView = (LinearLayout) getLayoutInflater().inflate(R.layout.pop_window_user_type,null);
                View  headerView = getLayoutInflater().inflate(R.layout.pop_window_user_type_header,null);
                headerView.findViewById(R.id.picker_header_close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mCustomPopWindow != null){
                            mCustomPopWindow.dissmiss();
                        }

                    }
                });

//                TextView textView = (TextView) view.findViewById(R.id.picker_header_tv);
                contentView.addView(headerView);
                for (String item:userTypeList) {
                    View  tv = getLayoutInflater().inflate(R.layout.pop_window_user_type_tv,null);
                    TextView  textView = (TextView)tv.findViewById(R.id.pop_window_user_type_tv) ;
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(mCustomPopWindow != null){
                                mCustomPopWindow.dissmiss();
                            }
                            ToastUtil.showMessage(((TextView)v).getText()+"");
                        }
                    });
                    textView.setText(item);
                    contentView.addView(tv);
                }

                mCustomPopWindow =  new CustomPopWindow.PopupWindowBuilder(this)
                        .setView(contentView)
                        .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                        .setBgDarkAlpha(0.5f) // 控制亮度
                        .setAnimationStyle(R.style.PopWindowAnimationFade)
                        .create()
                        .showAtLocation(parentLayout,Gravity.CENTER,0,0);
                break;
        }

    }
}
