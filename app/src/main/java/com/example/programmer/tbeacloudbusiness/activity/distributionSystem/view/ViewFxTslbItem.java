package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.ComplainItem;

public class ViewFxTslbItem extends FrameLayout {
	
	private TextView txtSJ;
	private TextView txtZT;
	private TextView txtDH;
	private TextView txtNR;
	

	public ViewFxTslbItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.view_fx_tslb_item, this);
		
		txtSJ=(TextView) findViewById(R.id.txtSJ);
		txtZT=(TextView) findViewById(R.id.txtZT);
		txtDH=(TextView) findViewById(R.id.txtDH);
		txtNR=(TextView) findViewById(R.id.txtNR);
		
	}
	
	
	private ComplainItem data;
	public ComplainItem getData(){
		return data;
	}
	
	public void fullData(ComplainItem data){
		this.data=data;
		txtSJ.setText(this.data.Date);
		txtZT.setText(this.data.ComplainStatus);
		txtDH.setText("联系电话:"+this.data.ContactMobile);
		txtNR.setText(this.data.Content);
	}
}
