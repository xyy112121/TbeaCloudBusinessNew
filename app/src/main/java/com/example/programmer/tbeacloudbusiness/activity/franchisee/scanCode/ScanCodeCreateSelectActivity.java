package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.model.Condition;

/**
 * 型号规格
 */

public class ScanCodeCreateSelectActivity extends BaseActivity {
    private final int RESULT_TYPE = 1000;//型号
    private final int RESULT_NORMS = 1001;//规格
    private Condition mType;
    private Condition mNorms;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_code_select);
        initTopbar("型号选择");
        initView();
    }

    private void  initView(){

        findViewById(R.id.create_code_select_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext,ScanCodeTypeSelectActivity.class);
               startActivityForResult(intent,RESULT_TYPE);
            }
        });

        findViewById(R.id.create_code_select_norms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext,ScanCodeNormsSelectActivity.class);
                startActivityForResult(intent,RESULT_NORMS);
            }
        });

        findViewById(R.id.top_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("type",mType);
                intent.putExtra("norms",mNorms);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if(resultCode == RESULT_OK){
           TextView mTypeView = (TextView)findViewById(R.id.create_code_select_type);
           TextView mNormsView = (TextView)findViewById(R.id.create_code_select_norms);
           TextView mSelectView = (TextView)findViewById(R.id.create_code_select_type_norms);

           switch (requestCode){
               case RESULT_TYPE://型号
                   mType = (Condition) data.getSerializableExtra("obj");
                   mTypeView.setText(mType.getName());
                   break;
               case RESULT_NORMS://规格
                   mNorms = (Condition) data.getSerializableExtra("obj");
                   mNormsView.setText(mNorms.getName());
                   break;
           }
           mSelectView.setText(mTypeView.getText()+"   "+ mNormsView.getText());
       }
    }
}
