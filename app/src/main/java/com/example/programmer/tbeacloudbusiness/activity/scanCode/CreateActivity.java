package com.example.programmer.tbeacloudbusiness.activity.scanCode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.TopActivity;

/**
 * 生成二维码
 */

public class CreateActivity extends TopActivity  implements View.OnClickListener {
    private final int REQEST_TYPE_NORMS = 1004;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_code);
        mContext = this;
        initTopbar("生成返利二维码","历史记录",this);
        listener();
    }

    private  void listener(){
        findViewById(R.id.create_code_type_norms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext,CreateSelectActivity.class);
               startActivityForResult(intent,REQEST_TYPE_NORMS);
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(mContext,HistoryListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if(resultCode == RESULT_OK && requestCode == REQEST_TYPE_NORMS){
           ((TextView)findViewById(R.id.create_code_type_norms)).setText(data.getStringExtra("value"));
       }
    }
}
