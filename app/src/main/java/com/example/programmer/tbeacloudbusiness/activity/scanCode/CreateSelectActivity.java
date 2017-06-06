package com.example.programmer.tbeacloudbusiness.activity.scanCode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.TopActivity;

/**
 * 型号规格
 */

public class CreateSelectActivity extends TopActivity{
    private Activity mContext;
    private final int RESULT_TYPE = 1000;//型号
    private final int RESULT_NORMS = 1001;//规格

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_code_select);
        mContext = this;
        initTopbar("型号规格");
        listerner();
    }

    private void  listerner(){
        findViewById(R.id.create_code_select_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext,TypeSelectActivity.class);
               startActivityForResult(intent,RESULT_TYPE);
            }
        });

        findViewById(R.id.create_code_select_norms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext,NormsSelectActivity.class);
                startActivityForResult(intent,RESULT_NORMS);
            }
        });

        findViewById(R.id.top_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView selectView = (TextView)findViewById(R.id.create_code_select_type_norms);
                Intent intent = new Intent();
                intent.putExtra("value",selectView.getText()+"");
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if(resultCode == RESULT_OK){
           TextView typeView = (TextView)findViewById(R.id.create_code_select_type);
           TextView normsView = (TextView)findViewById(R.id.create_code_select_norms);
           TextView selectView = (TextView)findViewById(R.id.create_code_select_type_norms);

           switch (requestCode){
               case RESULT_TYPE://型号
                   typeView.setText(data.getStringExtra("value"));
                   break;
               case RESULT_NORMS://规格
                   normsView.setText(data.getStringExtra("value"));
                   break;
           }
           selectView.setText(typeView.getText()+"   "+ normsView.getText()+"");
       }
    }
}
