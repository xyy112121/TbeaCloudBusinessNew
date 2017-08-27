package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.ContactPersonItem;

public class ViewFxLxwmItem extends FrameLayout {
	
	private TextView txtXM;
	private TextView txtZW;
	private TextView txtDH;
	private TextView txtYJ;
	private ImageView imgDDH;


	public ViewFxLxwmItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.view_fx_lxwm_item, this);
		
		txtXM=(TextView) findViewById(R.id.txtXM);
		txtZW=(TextView) findViewById(R.id.txtZW);
		txtDH=(TextView) findViewById(R.id.txtDH);
		txtYJ=(TextView) findViewById(R.id.txtYJ);
		imgDDH=(ImageView) findViewById(R.id.imgDDH);
	}
	
	private ContactPersonItem data;
	public ContactPersonItem getData(){
		return data;
	}
	
	public void fullData(ContactPersonItem data,OnClickListener listener){
		this.data=data;
		txtXM.setText(this.data.Name);
		txtZW.setText(this.data.JobTitle);
		txtDH.setText(this.data.MobileNumber);
		txtYJ.setText(this.data.EmailAddress);
		imgDDH.setTag(data);
		imgDDH.setOnClickListener(listener);
	}
}
