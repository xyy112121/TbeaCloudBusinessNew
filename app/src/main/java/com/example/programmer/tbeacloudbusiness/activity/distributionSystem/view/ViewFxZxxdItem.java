package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.PreOrderListItem;


public class ViewFxZxxdItem extends FrameLayout {
	
	private TextView txtMC;
	private TextView txtSJ;
	private TextView txtJGZJ;
	private TextView txtDJRQ;
	private TextView txtKFMC;
	
	private TextView txtCKXQ;
	private TextView txtSC;
	private TextView txtTJ;
	
	

	public ViewFxZxxdItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.view_fx_zxxd_item, this);
	
		txtMC=(TextView) findViewById(R.id.txtMC);
		txtSJ=(TextView) findViewById(R.id.txtSJ);
		txtJGZJ=(TextView) findViewById(R.id.txtJGZJ);
		txtDJRQ=(TextView) findViewById(R.id.txtDJRQ);
		txtKFMC=(TextView) findViewById(R.id.txtKFMC);
		txtCKXQ=(TextView) findViewById(R.id.txtCKXQ);
		txtSC=(TextView) findViewById(R.id.txtSC);
		txtTJ=(TextView) findViewById(R.id.txtTJ);

		
	}
	
	
	private PreOrderListItem data;
	public PreOrderListItem getData(){
		return data;
	}
	
	public void fullData(PreOrderListItem data,OnClickListener listener){
		this.data=data;

		txtMC.setText(this.data.OrderCode);
		txtSJ.setText(this.data.OrderStatus);
		txtJGZJ.setText("￥"+this.data.OrderTotleMoney);
		txtDJRQ.setText(this.data.OrderDate);
		txtKFMC.setText(this.data.CustomerName);
		txtCKXQ.setTag(new Object[]{"ckxq",this.data});
		txtSC.setTag(new Object[]{"sc",this.data});
		txtTJ.setTag(new Object[]{"tj",this.data});
		txtCKXQ.setOnClickListener(listener);
		txtSC.setOnClickListener(listener);
		txtTJ.setOnClickListener(listener);
	}
	
}
