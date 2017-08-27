package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.ProductListItem;


public class ViewFxZxxdTjCxJgItem extends FrameLayout {

    private TextView txtDDBH;
    private TextView txtMC;
    private TextView txtDW;
    private TextView txtMS;


    private ImageView imgTJ;


    public ViewFxZxxdTjCxJgItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_fx_zxxd_tj_cx_jg_item, this);

        txtDDBH = (TextView) findViewById(R.id.txtDDBH);
        txtMC = (TextView) findViewById(R.id.txtMC);
        txtDW = (TextView) findViewById(R.id.txtDW);
        txtMS = (TextView) findViewById(R.id.txtMS);

        imgTJ = (ImageView) findViewById(R.id.imgTJ);
    }


    private ProductListItem data;

    public ProductListItem getData() {
        return data;
    }

    public void fullData(ProductListItem data, OnClickListener listener) {
        this.data = data;

        txtDDBH.setText(this.data.ProductCode);
        txtMC.setText(this.data.ProductName);
        txtDW.setText(this.data.UnitofMeasurement);
        txtMS.setText(this.data.ProductDescription);
        imgTJ.setOnClickListener(listener);
        imgTJ.setTag(this.data);
    }
}
