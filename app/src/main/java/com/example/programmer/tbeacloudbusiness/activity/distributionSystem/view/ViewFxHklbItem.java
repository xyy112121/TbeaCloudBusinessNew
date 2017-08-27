package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.OrderListItem;


public class ViewFxHklbItem extends FrameLayout {
	
	private TextView txtGSMC;
	private TextView txtSJ;
	private TextView txtDDBH;
	private TextView txtDDZE;
	private TextView txtYFHJE;
	private TextView txtYHKJE;
	private TextView txtYSYE;
	


	public ViewFxHklbItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.view_fx_hklb_item, this);
		
		txtGSMC=(TextView) findViewById(R.id.txtGSMC);
		txtSJ=(TextView) findViewById(R.id.txtSJ);
		txtDDBH=(TextView) findViewById(R.id.txtDDBH);
		txtDDZE=(TextView) findViewById(R.id.txtDDZE);
		txtYFHJE=(TextView) findViewById(R.id.txtYFHJE);
		txtYHKJE=(TextView) findViewById(R.id.txtYHKJE);
		txtYSYE=(TextView) findViewById(R.id.txtYSYE);
	}
	
	
	private OrderListItem data;
	public OrderListItem getData(){
		return data;
	}
	
	public void fullData(OrderListItem data){
		this.data=data;
		txtGSMC.setText(this.data.CustomerName);
		txtSJ.setText(this.data.OrderDate);
		txtDDBH.setText(this.data.OrderCode);
		txtDDZE.setText("￥"+this.data.OrderMoney);
		txtYFHJE.setText("￥"+this.data.ReceivedMoney);
		txtYHKJE.setText("￥"+this.data.DeliveryMoney);
		txtYSYE.setText("￥"+this.data.LeftMoney);
	}
}
