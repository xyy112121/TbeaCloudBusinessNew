package com.example.programmer.tbeacloudbusiness.activity.scanCode;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.TopActivity;

import cn.qqtheme.framework.picker.DatePicker;

/**
 * Created by programmer on 2017/6/1.
 */

public class DateSelectActivity extends TopActivity {
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_select);
        mContext = this;
        initTopbar("时间选择");
        listener();
    }

    public  void listener(){
        findViewById(R.id.date_select_begin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPicker(v);
            }
        });

        findViewById(R.id.date_select_end).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPicker(v);
            }
        });

        findViewById(R.id.finish_bth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public  void showPicker(final View view){
        DatePicker picker = new DatePicker((Activity) mContext, DatePicker.YEAR_MONTH_DAY);
        picker.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        picker.setLabel("","","");
//                picker.setLineColor(ContextCompat.getColor(mContext, R.color.white));
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                if(view.getId() == R.id.date_select_begin){

                }else if(view.getId() == R.id.date_select_end){

                }
                ((TextView)view).setText(year+" - "+month+" - "+day);
            }
        });
        picker.setAnimationStyle(R.style.PopWindowAnimationFade);
        picker.show();
    }
}
