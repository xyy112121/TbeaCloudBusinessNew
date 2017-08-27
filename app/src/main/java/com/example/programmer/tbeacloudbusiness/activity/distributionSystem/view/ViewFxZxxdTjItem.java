package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.PreOrderProductListItem;


public class ViewFxZxxdTjItem extends FrameLayout {

    private TextView txtDDBH;
    private TextView txtCPBM;
    private TextView txtGG;
    private TextView txtDW;

    private TextView txtSL;
    private TextView txtFDBL;
    private TextView txtDJ;
    private TextView txtBZ;


    private ImageView imgClose;


    public ViewFxZxxdTjItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_fx_zxxd_tj_item, this);

        txtDDBH = (TextView) findViewById(R.id.txtDDBH);
        txtCPBM = (TextView) findViewById(R.id.txtCPBM);
        txtGG = (TextView) findViewById(R.id.txtGG);
        txtDW = (TextView) findViewById(R.id.txtDW);

        txtSL = (TextView) findViewById(R.id.txtSL);
        txtFDBL = (TextView) findViewById(R.id.txtFDBL);
        txtDJ = (TextView) findViewById(R.id.txtDJ);
        txtBZ = (TextView) findViewById(R.id.txtBZ);

        imgClose = (ImageView) findViewById(R.id.imgClose);
    }


    private PreOrderProductListItem data;

    public PreOrderProductListItem getData() {
        return data;
    }

    public void fullData(boolean canEdit, PreOrderProductListItem data, OnClickListener listener) {
        this.data = data;
        txtDDBH.setText(this.data.ProductName);
        txtCPBM.setText(this.data.ProductCode);
        txtGG.setText("");
        txtDW.setText(this.data.UnitofMeasurement);
        txtSL.setText(this.data.Count);
        txtFDBL.setText(this.data.FloatRatio);
        txtDJ.setText("￥" + this.data.Price);
        txtBZ.setText(this.data.Specification);
        if (canEdit) {
            imgClose.setVisibility(View.VISIBLE);
            imgClose.setTag(this.data);
            imgClose.setOnClickListener(listener);
        } else {
            imgClose.setVisibility(View.INVISIBLE);
            imgClose.setTag(this.data);
            imgClose.setOnClickListener(listener);
        }
    }
}
