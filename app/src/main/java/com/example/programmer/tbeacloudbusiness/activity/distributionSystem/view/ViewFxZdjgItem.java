package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.ReferencePriceItem;


public class ViewFxZdjgItem extends FrameLayout {

    private TextView txtCPMC;
    private TextView txtDW;
    private TextView txtZDJ;


    public ViewFxZdjgItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_fx_zdjg_item, this);
        txtCPMC = (TextView) findViewById(R.id.txtCPMC);
        txtDW = (TextView) findViewById(R.id.txtDW);
        txtZDJ = (TextView) findViewById(R.id.txtZDJ);
    }


    private ReferencePriceItem data;

    public ReferencePriceItem getData() {
        return data;
    }

    public void fullData(ReferencePriceItem data) {
        this.data = data;
        txtCPMC.setText(this.data.CommodityName);
        txtDW.setText(this.data.UnitofMeasurement);
        txtZDJ.setText("￥" + this.data.ReferencePrice);
    }
}
