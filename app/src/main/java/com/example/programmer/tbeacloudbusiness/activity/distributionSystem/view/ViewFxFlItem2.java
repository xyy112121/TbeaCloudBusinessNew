package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;


public class ViewFxFlItem2 extends FrameLayout {

    private TextView txtDDBH;
    private TextView txtZT;
    private TextView txtDDJE;
    private TextView txtFLJE;


    public ViewFxFlItem2(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_fx_fl_item2, this);
        txtDDBH = (TextView) findViewById(R.id.txtDDBH);
        txtZT = (TextView) findViewById(R.id.txtZT);
        txtDDJE = (TextView) findViewById(R.id.txtDDJE);
        txtFLJE = (TextView) findViewById(R.id.txtFLJE);
    }

	/*
	private RebatedListItem data;
	public RebatedListItem getData(){
		return data;
	}
	
	public void fullData(RebatedListItem data,String imgBaseUrl){
		this.data=data;
		txtDDBH.setText(this.data.);
		txtZT.setText(this.data.Title);
		txtDDJE.setText(this.data.Title);
		txtFLJE.setText(this.data.TaskStatus);
	}*/
}
