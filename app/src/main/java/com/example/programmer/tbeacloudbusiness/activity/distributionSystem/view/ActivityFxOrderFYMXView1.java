package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.OrderInfo;

public class ActivityFxOrderFYMXView1 extends FrameLayout{
	
	private TextView txtKFMC;
	private TextView txtDDBH;
	private TextView txtQDRQ;
	private TextView txtJHQ;
	private TextView txtDDZE;
	private TextView txtFHJE;
	
	private TextView txtDHCPMX;


	public ActivityFxOrderFYMXView1(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.activity_fx_order_fymx_view1, this);
		txtKFMC=(TextView)findViewById(R.id.txtKFMC);
		txtDDBH=(TextView)findViewById(R.id.txtDDBH);
		txtQDRQ=(TextView)findViewById(R.id.txtQDRQ);
		
		txtJHQ=(TextView)findViewById(R.id.txtJHQ);
		txtDDZE=(TextView)findViewById(R.id.txtDDZE);
		txtFHJE=(TextView)findViewById(R.id.txtFHJE);
		
	
		txtDHCPMX=(TextView)findViewById(R.id.txtDHCPMX);
	}
	

	private OrderInfo data;
	
	public OrderInfo getData(){
		return data;
	}
	
	public void fullData(OrderInfo data,OnClickListener listener){
		this.data=data;
		txtKFMC.setText(data.CustomerName);
		txtDDBH.setText(data.OrderCode);
		txtQDRQ.setText(data.OrderDate);
		txtJHQ.setText(data.AllDeliveryDate);
		txtDDZE.setText("￥"+data.OrderMoney);
		txtFHJE.setText("￥"+data.DeliveryMoney);
		txtDHCPMX.setOnClickListener(listener);
		txtDHCPMX.setTag(new Object[]{"dhcpmx",this.data});
	}
}
