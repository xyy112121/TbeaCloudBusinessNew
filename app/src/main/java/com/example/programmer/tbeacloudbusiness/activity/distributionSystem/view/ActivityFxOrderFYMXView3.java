package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.TransitionListItem;

public class ActivityFxOrderFYMXView3 extends FrameLayout{
	
	private TextView txtZYDH;
	private TextView txtZCDH;
	private TextView txtCYDW;
	private TextView txtZCRQ;
	private Button butQRSH;
	


	public ActivityFxOrderFYMXView3(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.activity_fx_order_fymx_view3, this);
		txtZYDH=(TextView)findViewById(R.id.txtZYDH);
		txtZCDH=(TextView)findViewById(R.id.txtZCDH);
		txtCYDW=(TextView)findViewById(R.id.txtCYDW);
		txtZCRQ=(TextView)findViewById(R.id.txtZCRQ);
		butQRSH=(Button)findViewById(R.id.butQRSH);
	}
	

	private TransitionListItem data;
	
	public TransitionListItem getData(){
		return data;
	}
	
	public void fullData(TransitionListItem data,OnClickListener listener){
		this.data=data;
		txtZYDH.setText(data.TransitionCode);
		txtZCDH.setText(data.TransitionCarCode);
		txtCYDW.setText(data.TransitionCompany);
		txtZCRQ.setText(data.TransitionDate);
		butQRSH.setOnClickListener(listener);
		butQRSH.setTag(new Object[]{"qrsh",this.data});
	}
}
