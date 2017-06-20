package com.example.programmer.tbeacloudbusiness.component.picker;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;

import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.popup.BottomPopup;

/**
 * 单项选择
 */

public class CustomOptionPicker extends OptionPicker{
    private String headTitle;

    @Nullable
    @Override
    protected View makeHeaderView() {
       View mHeadView = super.activity.getLayoutInflater().inflate(R.layout.picker_header,null);
        if(!"".equals(headTitle)){
            ((TextView)mHeadView.findViewById(R.id.picker_header_tv)).setText(headTitle);
        }
        mHeadView.findViewById(R.id.picker_header_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return mHeadView;//顶部视图
    }

    public void setGravity(int gravity){
        getRootView().getChildAt(0).setBackground(ContextCompat.getDrawable(super.activity,R.drawable.picket_information_background));
        getWindow().setGravity(gravity);
    }

    @Nullable
    @Override
    protected View makeFooterView() {
        View view = super.activity.getLayoutInflater().inflate(R.layout.picker_footer,null);
        view.findViewById(R.id.picker_footer_comfrim).setBackgroundResource(R.drawable.btn_bg_blue);
        view.findViewById(R.id.picker_footer_comfrim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onSubmit();
            }
        });
        return view;//底部视图
    }

    public CustomOptionPicker(Activity activity,String headTitle, String[] data) {
        super(activity, data);
        this.headTitle = headTitle;
        WindowManager m = activity.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        setSize((int) (d.getWidth() * 0.9), BottomPopup.WRAP_CONTENT);
        setTextColor(ContextCompat.getColor(activity,R.color.text_color2),ContextCompat.getColor(activity,R.color.text_color));
        setLineColor(ContextCompat.getColor(activity,R.color.text_color));
        setTopLineVisible(false);
        setAnimationStyle(R.style.PopWindowAnimationFade);
    }

    @Override
    public void onSubmit() {
        super.onSubmit();//点击确定
    }

    @Override
    protected void onCancel() {
        super.onCancel();//点击取消
    }

}
