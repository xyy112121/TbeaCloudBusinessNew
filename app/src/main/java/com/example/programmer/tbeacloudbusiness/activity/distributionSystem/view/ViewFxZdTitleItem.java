package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.RuleCategoryItem;


public class ViewFxZdTitleItem extends FrameLayout {
	
	private TextView txtTitle;
	private View viewFlag;
	

	public ViewFxZdTitleItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.view_fx_zd_title_item, this);
	
		txtTitle=(TextView) findViewById(R.id.txtTitle);
		viewFlag=(View) findViewById(R.id.viewFlag);
		
	}
	
	
	private RuleCategoryItem data;
	public RuleCategoryItem getData(){
		return data;
	}
	
	public void fullData(RuleCategoryItem data){
		this.data=data;
		txtTitle.setText(this.data.Name);
	}
	
	public void setFlag(boolean flag){
		if(flag){
			viewFlag.setBackgroundColor(Color.parseColor("#0068b7"));
		}else{
			viewFlag.setBackgroundColor(Color.parseColor("#00000000"));
		}
	}
}
