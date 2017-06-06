package com.example.programmer.tbeacloudbusiness.activity.plumberManage;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.TopActivity;

/**
 * Created by programmer on 2017/6/4.
 */

public class PersonViewActivity extends TopActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber_manage_person_view);
        initTopbar("");
    }
}
