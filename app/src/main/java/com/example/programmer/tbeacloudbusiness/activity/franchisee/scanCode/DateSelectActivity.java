package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.qqtheme.framework.picker.DatePicker;

/**
 * 自定义时间选择
 */

public class DateSelectActivity extends BaseActivity {

    @BindView(R.id.date_select_begin)
    TextView mBeginView;
    @BindView(R.id.date_select_end)
    TextView mEndView;
    Calendar mStartCalendar;
    Calendar mEndCalendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_select);
        ButterKnife.bind(this);
        try {
            String startTime = getIntent().getStringExtra("startTime");
            String endTime = getIntent().getStringExtra("endTime");
            mBeginView.setText(startTime);
            mEndView.setText(endTime);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (startTime != null) {
                Date date = sdf.parse(startTime);
                mStartCalendar = Calendar.getInstance();
                mStartCalendar.setTime(date);
            } else {
                mStartCalendar = null;
            }

            if (endTime != null) {
                Date date = sdf.parse(endTime);
                mEndCalendar = Calendar.getInstance();
                mEndCalendar.setTime(date);
            } else {
                mEndCalendar = null;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        initTopbar("时间选择");
        listener();
    }

    public void listener() {
        findViewById(R.id.date_select_begin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPicker(v,mStartCalendar);
            }
        });

        findViewById(R.id.date_select_end).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPicker(v,mEndCalendar);
            }
        });

        findViewById(R.id.finish_bth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startTime = mBeginView.getText() + "";
                String endTime = mEndView.getText() + "";
                if (compareTime(startTime, endTime) == 1) {
                    showMessage("开始时间不能大于结束时间！");
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("startTime", startTime);
                    intent.putExtra("endTime", endTime);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });
    }

    public int compareTime(String date1, String date2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }


    public void showPicker(final View view,Calendar c) {
        DatePicker picker = new DatePicker(mContext, DatePicker.YEAR_MONTH_DAY);
        picker.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        picker.setLabel("", "", "");
        if(c == null){
            c = Calendar.getInstance();
        }
        picker.setSelectedItem(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                if (view.getId() == R.id.date_select_begin) {

                } else if (view.getId() == R.id.date_select_end) {

                }
                ((TextView) view).setText(year + "-" + month + "-" + day);
            }
        });
        picker.setAnimationStyle(R.style.PopWindowAnimationFade);
        picker.show();
    }
}
