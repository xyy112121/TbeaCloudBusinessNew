package com.example.programmer.tbeacloudbusiness.component.picker;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;

import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.util.LogUtils;
import cn.qqtheme.framework.widget.WheelView;


/**
 * Created by programmer on 2017/6/10.
 */

public class CustomFilePicker extends OptionPicker {

    private String selectedOption = "";
    private String label = "";

    @Nullable
    @Override
    protected View makeHeaderView() {
        View  view = super.activity.getLayoutInflater().inflate(R.layout.picker_header,null);
        TextView textView = (TextView) view.findViewById(R.id.picker_header_tv);
        textView.setCompoundDrawables(ContextCompat.getDrawable(super.activity,R.drawable.icon_useralert),null,null,null);
        textView.setText("用户类型选择后将不能更改！");

//        ((TextView)mHeadView.findViewById(R.id.picker_header_tv)).setText("dddkkdkd");
        view.findViewById(R.id.picker_header_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;//顶部视图
    }

    @Override
    @NonNull
    protected View makeCenterView() {
        if (options.size() == 0) {
            throw new IllegalArgumentException("please initial options at first, can't be empty");
        }
        LinearLayout layout = new LinearLayout(activity);
        layout.setPadding(100,0,100,0);
        layout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT,WRAP_CONTENT));
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);
//        layout.setBackgroundColor(ContextCompat.getColor(super.activity,R.color.colorAccent));
        CustomWheelView optionView = new CustomWheelView(activity);
        optionView.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        optionView.setTextSize(textSize);
        optionView.setTextColor(textColorNormal, textColorFocus);
        optionView.setLineVisible(lineVisible);
        optionView.setLineColor(lineColor);
        optionView.setOffset(offset);
        optionView.setBackgroundColor(ContextCompat.getColor(super.activity,R.color.colorPrimary));
        layout.addView(optionView);
        TextView labelView = new TextView(activity);
        labelView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        labelView.setTextColor(textColorFocus);
        labelView.setTextSize(textSize);
//        View view = new View(super.activity);
//        view.setBackgroundColor(ContextCompat.getColor(super.activity,R.color.colorAccent));
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MATCH_PARENT,1);
//        view.setLayoutParams(params);
        layout.addView(labelView);
//        layout.addView(view);
        if (!TextUtils.isEmpty(label)) {
            labelView.setText(label);
        }
        if (TextUtils.isEmpty(selectedOption)) {
            optionView.setItems(options);
        } else {
            optionView.setItems(options, selectedOption);
        }
        optionView.setOnWheelViewListener(new CustomWheelView.OnWheelViewListener() {
            @Override
            public void onSelected(boolean isUserScroll, int selectedIndex, String item) {
                selectedOption = item;
            }
        });
        return layout;
    }

    public void setGravity(int gravity){
        getRootView().getChildAt(0).setBackground(ContextCompat.getDrawable(super.activity,R.drawable.picket_information_background));
        getWindow().setGravity(gravity);
    }


    @Nullable
    @Override
    protected View makeFooterView() {
        View view = super.activity.getLayoutInflater().inflate(R.layout.picker_footer,null);
        view.findViewById(R.id.picker_footer_comfrim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onSubmit();
            }
        });
        return view;//底部视图
    }


    public CustomFilePicker(Activity activity, String[] options) {
        super(activity, options);
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
