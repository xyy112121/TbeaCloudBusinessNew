package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.AnnouncementListItem;

public class ViewFxGgItem extends FrameLayout {
	
	private TextView txtBT;
	private TextView txtBH;
	private TextView txtFBRQ;
	private TextView txtZZRQ;
	private TextView txtFBR;
	private TextView txtNR;
	


	public ViewFxGgItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.view_fx_gg_item, this);
		
		txtBT=(TextView) findViewById(R.id.txtBT);
		txtBH=(TextView) findViewById(R.id.txtBH);
		txtFBRQ=(TextView) findViewById(R.id.txtFBRQ);
		txtZZRQ=(TextView) findViewById(R.id.txtZZRQ);
		txtFBR=(TextView) findViewById(R.id.txtFBR);
		txtNR=(TextView) findViewById(R.id.txtNR);
	}
	
	
	private AnnouncementListItem data;
	public AnnouncementListItem getData(){
		return data;
	}
	
	public void fullData(AnnouncementListItem data){
		this.data=data;
		txtBT.setText(this.data.PublishDate);
		txtBH.setText(this.data.Code);
		txtFBRQ.setText(this.data.PublishDate);
		txtZZRQ.setText(this.data.EndDate);
		txtFBR.setText(this.data.Author);
		txtNR.setText(this.data.Description);
	}
}
