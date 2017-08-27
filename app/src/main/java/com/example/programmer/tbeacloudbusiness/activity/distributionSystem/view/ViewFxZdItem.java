package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.RuleListItem;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ViewFxZdItem extends FrameLayout {
	
	private ImageView imgIcon;
	private TextView txtName;
	private TextView txtSize;
	private TextView txtType;
	private TextView txtTime;
	

	public ViewFxZdItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.view_fx_zd_item, this);
		
		imgIcon=(ImageView) findViewById(R.id.imgIcon);
		
		txtName=(TextView) findViewById(R.id.txtName);
		txtSize=(TextView) findViewById(R.id.txtSize);
		txtType=(TextView) findViewById(R.id.txtType);
		txtTime=(TextView) findViewById(R.id.txtTime);
		
	}
	
	
	private RuleListItem data;
	public RuleListItem getData(){
		return data;
	}
	
	public void fullData(RuleListItem data,String imgBaseUrl){
		this.data=data;
		ImageLoader.getInstance().displayImage(imgBaseUrl+this.data.DocumentTypeIcon,imgIcon);
		txtName.setText(this.data.Name);
		txtSize.setText(this.data.DocumentSize);
		txtType.setText(this.data.RuleCategory);
		txtTime.setText(this.data.Date);
	}
}
