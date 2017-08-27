package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.ShouldRebateListItem;


public class ViewFxFlItem1 extends FrameLayout {
	
	private TextView txtDDBH;
	private TextView txtZT;
	private TextView txtDDJE;
	private TextView txtFLJE;


	public ViewFxFlItem1(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.view_fx_fl_item1, this);
		txtDDBH=(TextView) findViewById(R.id.txtDDBH);
		txtZT=(TextView) findViewById(R.id.txtZT);
		txtDDJE=(TextView) findViewById(R.id.txtDDJE);
		txtFLJE=(TextView) findViewById(R.id.txtFLJE);
	}
	
	
	private ShouldRebateListItem data;
	public ShouldRebateListItem getData(){
		return data;
	}
	
	public void fullData(ShouldRebateListItem data){
		this.data=data;
		txtDDBH.setText(this.data.OrderCode);
		txtZT.setText(this.data.RebateStatus);
		txtDDJE.setText("￥"+this.data.OrderMoney);
		txtFLJE.setText("￥"+this.data.RebateFee);
	}
}
