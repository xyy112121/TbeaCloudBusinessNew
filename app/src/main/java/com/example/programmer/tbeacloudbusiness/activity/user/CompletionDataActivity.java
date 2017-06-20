package com.example.programmer.tbeacloudbusiness.activity.user;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.TopActivity;
import com.example.programmer.tbeacloudbusiness.component.CustomPopWindow1;
import com.example.programmer.tbeacloudbusiness.component.picker.CustomAddressPicker;
import com.example.programmer.tbeacloudbusiness.component.picker.CustomDatePicker;
import com.example.programmer.tbeacloudbusiness.utils.AssetsUtils;
import com.example.zhouwei.library.CustomPopWindow;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.qqtheme.framework.picker.AddressPicker;

/**
 * Created by programmer on 2017/6/7.
 */

public class CompletionDataActivity extends TopActivity {
    private String mProvince = "四川省";
    private String mCity = "德阳市";
    private String mCounty = "旌阳区";
    private Context mContext;
    List<String> sexList = new ArrayList<>();
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

        findViewById(R.id.completion_data_header).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 showImage();
            }
        });
    }

    /**
     * 显示选择拍照，图库
     */
    private void showImage(){
        List<String> photoOperationList = new ArrayList<>();
        photoOperationList.add("拍照上传");
        photoOperationList.add("本地上传");
        CustomPopWindow1 popWindow1 = new CustomPopWindow1(mContext);
        popWindow1.init(parentLayout,R.layout.pop_window_header1,R.layout.pop_window_tv,photoOperationList);
        popWindow1.setItemClick(new CustomPopWindow1.ItemClick() {
            @Override
            public void onItemClick(String text) {

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
            picker.setTextSize(14);
            picker.setSelectedItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
            picker.setOnDatePickListener(new CustomDatePicker.OnYearMonthDayPickListener() {
                @Override
                public void onDatePicked(String year, String month, String day) {
                    ((TextView)findViewById(R.id.completion_data_date))
                            .setText( year.replace("年","")+"-"+month.replace("月","")+"-"+day.replace("日",""));
                }
            });

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
        CustomPopWindow1 popWindow1 = new CustomPopWindow1(mContext);
        popWindow1.init(parentLayout,R.layout.pop_window_header1,R.layout.pop_window_tv,sexList);
        popWindow1.setItemClick(new CustomPopWindow1.ItemClick() {
            @Override
            public void onItemClick(String text) {
                ((TextView)findViewById(R.id.completion_data_sex)).setText(text);
            }
        });
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
            picker.setTextSize(14);
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
            picker.show();
            ((TextView)picker.getRootView().getChildAt(0).findViewById(R.id.picker_header_tv)).setText(mProvince+mCity+mCounty);
            picker.setGravity(Gravity.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
