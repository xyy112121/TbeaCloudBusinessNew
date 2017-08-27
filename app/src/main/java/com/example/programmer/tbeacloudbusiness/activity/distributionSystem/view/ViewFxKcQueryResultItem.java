package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.KucunListItem;

public class ViewFxKcQueryResultItem extends FrameLayout {
	
	private TextView txtCPMC;
	private TextView txtID;
	private TextView txtDW;
	private TextView txtKC;


	public ViewFxKcQueryResultItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.view_fx_kc_query_result_item, this);
		txtCPMC=(TextView) findViewById(R.id.txtCPMC);
		txtID=(TextView) findViewById(R.id.txtID);
		txtDW=(TextView) findViewById(R.id.txtDW);
		txtKC=(TextView) findViewById(R.id.txtKC);
	}
	
	
	private KucunListItem data;
	public KucunListItem getData(){
		return data;
	}
	
	public void fullData(KucunListItem data){
		this.data=data;
		txtCPMC.setText(this.data.CommodityName);
		txtID.setText(this.data.CommodityCode);
		txtDW.setText(this.data.UnitofMeasurement);
		txtKC.setText(this.data.InventoryNumber);
	}
}
