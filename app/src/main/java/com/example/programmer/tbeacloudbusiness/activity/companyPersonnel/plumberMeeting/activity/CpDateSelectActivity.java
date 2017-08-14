package com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by DELL on 2017/8/12.
 */

public class CpDateSelectActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_select);
        initTopbar("时间选择");
        listener();
    }

    public void listener() {
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
                Intent intent = new Intent();
                String startTime = ((TextView) findViewById(R.id.date_select_begin)).getText() + "";
                String endTime = ((TextView) findViewById(R.id.date_select_end)).getText() + "";
                intent.putExtra("startTime", startTime);
                intent.putExtra("endTime", endTime);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public void showPicker(final View view) {
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
        pvTime.show();
    }
}
