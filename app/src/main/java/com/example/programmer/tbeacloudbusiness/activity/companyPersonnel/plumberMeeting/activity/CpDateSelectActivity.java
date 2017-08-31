package com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by DELL on 2017/8/12.
 */

public class CpDateSelectActivity extends BaseActivity {
    Calendar mStartCalendar;
    Calendar mEndCalendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_select);
        initTopbar("时间选择");
        listener();
        try {
            String startTime = getIntent().getStringExtra("startTime");
            String endTime = getIntent().getStringExtra("endTime");
            ((TextView)findViewById(R.id.date_select_begin)).setText(startTime);
            ((TextView)findViewById(R.id.date_select_end)).setText(endTime);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
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

    }

    public void listener() {
        findViewById(R.id.date_select_begin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPicker(v, mStartCalendar);

            }
        });

        findViewById(R.id.date_select_end).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPicker(v, mEndCalendar);

            }
        });

        findViewById(R.id.finish_bth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String startTime = ((TextView) findViewById(R.id.date_select_begin)).getText() + "";
                String endTime = ((TextView) findViewById(R.id.date_select_end)).getText() + "";
                if (compareTime(startTime, endTime) == 1) {
                    ToastUtil.showMessage("起始时间不能大于终止时间！");
                } else {
                    intent.putExtra("startTime", startTime);
                    intent.putExtra("endTime", endTime);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    public int compareTime(String date1, String date2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
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

    public void showPicker(final View view, Calendar calendar) {
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String str = sdf.format(date);
                ((TextView) view).setText(str);
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, true, true, false}).build();
        if (calendar != null)
            pvTime.setDate(calendar);
        pvTime.show();
    }
}
