package com.example.programmer.tbeacloudbusiness.activity.user;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.TopActivity;
import com.example.programmer.tbeacloudbusiness.component.picker.CustomAddressPicker;
import com.example.programmer.tbeacloudbusiness.component.picker.CustomDatePicker;
import com.example.programmer.tbeacloudbusiness.utils.AssetsUtils;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.example.zhouwei.library.CustomPopWindow;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.popup.BottomPopup;

/**
 * Created by programmer on 2017/6/7.
 */

public class CompletionDataActivity extends TopActivity {
    private String mProvince = "四川省";
    private String mCity = "德阳市";
    private String mCounty = "旌阳区";
    private Context mContext;
    List<String> sexList = new ArrayList<>();
    private CustomPopWindow mCustomPopWindow;
    private View parentLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completion_data);
        mContext = this;
        parentLayout = findViewById(R.id.completion_data_layout);
        sexList.add("男");
        sexList.add("女");
        initTopbar("资料补全");
        listener();
    }

    private void listener(){
        findViewById(R.id.completion_data_area).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showAddrPicker();
            }
        });

        findViewById(R.id.completion_data_sex).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSexPicker();
            }
        });

        findViewById(R.id.completion_data_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }

    /**
     * 显示时间控件
     */
    private void showDatePicker(){
        try {
            Calendar calendar = Calendar.getInstance();
            CustomDatePicker picker = new CustomDatePicker((Activity) mContext);
            picker.setRange(1990, 2030);
            picker.setTopLineVisible(false);
            picker.setTextSize(14);
            picker.setTextColor(ContextCompat.getColor(mContext,R.color.text_color2),ContextCompat.getColor(mContext,R.color.text_color));
            picker.setLineColor(ContextCompat.getColor(mContext,R.color.text_color));
            picker.setSelectedItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
           picker.setAnimationStyle(R.style.PopWindowAnimationFade);
            picker.setOnDatePickListener(new CustomDatePicker.OnYearMonthDayPickListener() {
                @Override
                public void onDatePicked(String year, String month, String day) {
                    ((TextView)findViewById(R.id.completion_data_date))
                            .setText( year.replace("年","")+"-"+month.replace("月","")+"-"+day.replace("日",""));
                }
            });
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
            picker.setSize((int) (d.getWidth() * 0.9), BottomPopup.WRAP_CONTENT);
            picker.show();
            picker.setGravity(Gravity.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 显示性别
     */
    private void showSexPicker(){
        LinearLayout contentView = (LinearLayout) getLayoutInflater().inflate(R.layout.pop_window_user_type,null);
        View  headerView = getLayoutInflater().inflate(R.layout.pop_window_user_type_header,null);
        headerView.findViewById(R.id.picker_header_tv).setVisibility(View.GONE);
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
        for (String item:sexList) {
            View  tv = getLayoutInflater().inflate(R.layout.pop_window_user_type_tv,null);
            TextView  textView = (TextView)tv.findViewById(R.id.pop_window_user_type_tv) ;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mCustomPopWindow != null){
                        mCustomPopWindow.dissmiss();
                    }
                    ((TextView)findViewById(R.id.completion_data_sex)).setText(((TextView)v).getText()+"");
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
    }

    /**
     * 显示地址控件
     */
    private void showAddrPicker(){
        try {
            ArrayList<AddressPicker.Province> data = new ArrayList<>();
            String json = AssetsUtils.readText(mContext, "city.json");
            data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
            final CustomAddressPicker picker = new CustomAddressPicker((Activity) mContext, data);
            picker.setAnimationStyle(R.style.PopWindowAnimationFade);
            picker.setTopLineVisible(false);
            picker.setTextSize(14);
            picker.setTextColor(ContextCompat.getColor(mContext,R.color.text_color2),ContextCompat.getColor(mContext,R.color.text_color));
            picker.setLineColor(ContextCompat.getColor(mContext,R.color.text_color));
            picker.setSelectedItem(mProvince, mCity, mCounty);
            //picker.setHideProvince(true);//加上此句举将只显示地级及县级
            //picker.setHideCounty(true);//加上此句举将只显示省级及地级
            picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                @Override
                public void onAddressPicked(String province, String city, String county) {
                    mProvince = province;
                    mCity = city;
                    mCounty = county;
                    ((TextView)findViewById(R.id.completion_data_area)).setText(province + city + county);
                    //                        ToastUtil.showMessage(province + city + county);
                }
            });
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
            picker.setSize((int) (d.getWidth() * 0.9), BottomPopup.WRAP_CONTENT);
            picker.show();
            ((TextView)picker.getRootView().getChildAt(0).findViewById(R.id.picker_header_tv)).setText(mProvince+mCity+mCounty);
            picker.setGravity(Gravity.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
