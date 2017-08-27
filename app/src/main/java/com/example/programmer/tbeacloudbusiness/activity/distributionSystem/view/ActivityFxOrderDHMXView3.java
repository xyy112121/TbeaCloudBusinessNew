package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.CommodityListItem;

public class ActivityFxOrderDHMXView3 extends FrameLayout {

    private TextView txtBH;
    private TextView txtMS;
    private TextView txtDW;
    private TextView txtSL;
    private TextView txtYFS;


    public ActivityFxOrderDHMXView3(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.activity_fx_order_dhmx_view3, this);
        txtBH = (TextView) findViewById(R.id.txtBH);
        txtMS = (TextView) findViewById(R.id.txtMS);
        txtDW = (TextView) findViewById(R.id.txtDW);

        txtSL = (TextView) findViewById(R.id.txtSL);
        txtYFS = (TextView) findViewById(R.id.txtYFS);
    }


    private CommodityListItem data;

    public CommodityListItem getData() {
        return data;
    }

    public void fullData(CommodityListItem data) {
        this.data = data;
        txtBH.setText(data.CommodityCode);
        txtMS.setText(data.CommodityName);
        txtDW.setText(data.UnitofMeasurement);
        txtSL.setText(data.TotleNumber);
        txtYFS.setText(data.DeliveryNumber);
    }
}
