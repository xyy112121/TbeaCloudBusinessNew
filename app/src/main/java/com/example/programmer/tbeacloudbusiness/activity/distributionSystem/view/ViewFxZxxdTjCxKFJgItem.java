package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.CustomerListItem;


public class ViewFxZxxdTjCxKFJgItem extends FrameLayout {
	
	private TextView txtKFBM;
	private TextView txtKFMC;
	private TextView txtKFDM;
	private ImageView imgTJ;
	
	

	public ViewFxZxxdTjCxKFJgItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.view_fx_zxxd_tj_kf_cx_jg_item, this);
	
		txtKFBM=(TextView) findViewById(R.id.txtKFBM);
		txtKFMC=(TextView) findViewById(R.id.txtKFMC);
		txtKFDM=(TextView) findViewById(R.id.txtKFDM);
		imgTJ=(ImageView) findViewById(R.id.imgTJ);
	}
	
	
	private CustomerListItem data;
	public CustomerListItem getData(){
		return data;
	}
	
	public void fullData(CustomerListItem data,OnClickListener listener){
		this.data=data;
		
		txtKFBM.setText(this.data.CustomerCode);
		txtKFMC.setText(this.data.CustomerName);
		txtKFDM.setText(this.data.CustomerShortName);
		imgTJ.setOnClickListener(listener);
		imgTJ.setTag(this.data);
	}
}
