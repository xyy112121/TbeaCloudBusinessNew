package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.activity;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;

public class ActivityUserInfoChang6 extends Activity implements OnClickListener{
    private String title;
    private long value;

    private ImageButton butOK;
    private TextView txtTitle;
    private TextView txtTitle2;

    private DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title=getIntent().getStringExtra("title");
        setContentView(R.layout.activity_user_info_change6);
        init();
        txtTitle.setText(title);
        txtTitle2.setText(title);
        if(value>0){
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(value);
            datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);
        }else{
            Calendar cal = Calendar.getInstance();
            datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);
        }
    }

    private void init(){
        txtTitle=(TextView) findViewById(R.id.txtTitle);
        txtTitle2=(TextView) findViewById(R.id.txtTitle2);
        butOK=(ImageButton) findViewById(R.id.butOK);
        datePicker=(DatePicker) findViewById(R.id.datePicker);
        butOK.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==butOK)toOK();
    }

    private void toOK(){
        int year=datePicker.getYear();
        int month=datePicker.getMonth();
        int day=datePicker.getDayOfMonth();


        Intent intent=new Intent();
        intent.putExtra("year", year);
        intent.putExtra("month", month+1);
        intent.putExtra("day", day);
        setResult(RESULT_OK, intent);
        finish();
    }
}
