package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;

public class ActivityFxKcQuery extends Activity implements OnClickListener {
	
	private EditText edtQuery;
	private Button butQuery;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.instance.addActivity(this);
		setContentView(R.layout.activity_fx_kc_query);
		init();
		setView();
	}
	
	
	
	private void init(){
		edtQuery=(EditText)findViewById(R.id.edtQuery);
		butQuery=(Button)findViewById(R.id.butQuery);
	}
	
	private void setView(){
		butQuery.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		Intent intent=new Intent(this, ActivityFxKcQueryResult.class);
		intent.putExtra("name", edtQuery.getText().toString());
		startActivity(intent);
		finish();
	}
}
