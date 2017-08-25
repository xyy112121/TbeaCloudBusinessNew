package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.OrderListItem2;


public class ViewFxOrderItem extends FrameLayout{

    private TextView txtDDH;
    private TextView txtZT;
    private TextView txtGSM;

    private TextView txtQDRQ;
    private TextView txtJHQ;
    private TextView txtDDZE;
    private TextView txtFHJE;

    private TextView txtCKMX;
    private TextView txtDHMX;



    public ViewFxOrderItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_fx_order_item, this);

        txtDDH=(TextView) findViewById(R.id.txtDDH);
        txtZT=(TextView) findViewById(R.id.txtZT);
        txtGSM=(TextView) findViewById(R.id.txtGSM);

        txtQDRQ=(TextView) findViewById(R.id.txtQDRQ);
        txtJHQ=(TextView) findViewById(R.id.txtJHQ);
        txtDDZE=(TextView) findViewById(R.id.txtDDZE);
        txtFHJE=(TextView) findViewById(R.id.txtFHJE);

        txtCKMX=(TextView) findViewById(R.id.txtCKMX);
        txtDHMX=(TextView) findViewById(R.id.txtDHMX);

    }


    private OrderListItem2 data;
    public OrderListItem2 getData(){
        return data;
    }

    public void fullData(OrderListItem2 data,OnClickListener listener){
        this.data=data;

        txtDDH.setText(this.data.OrderCode);
        txtZT.setText(this.data.OrderStatus);
        txtGSM.setText(this.data.CustomerName);

        txtQDRQ.setText(this.data.OrderDate);
        txtJHQ.setText(this.data.AllDeliveryDate);
        txtDDZE.setText("￥"+this.data.OrderMoney);
        txtFHJE.setText("￥"+this.data.DeliveryMoney);

        txtCKMX.setOnClickListener(listener);
        txtCKMX.setTag(new Object[]{"1",data});
        txtDHMX.setOnClickListener(listener);
        txtDHMX.setTag(new Object[]{"2",data});
    }
}
