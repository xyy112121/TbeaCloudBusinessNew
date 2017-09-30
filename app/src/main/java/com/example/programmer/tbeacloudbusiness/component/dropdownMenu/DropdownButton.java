package com.example.programmer.tbeacloudbusiness.component.dropdownMenu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;


/**
 * Created by Administrator on 2015/5/28.
 */
public class DropdownButton extends RelativeLayout {
    public TextView textView;
    public ImageView imageView;
    public boolean isCheck;
    public Context mContext;

    public DropdownButton(Context context) {
        this(context, null);
    }

    public DropdownButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropdownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        View view =  LayoutInflater.from(getContext()).inflate(R.layout.expand_tab_button_item,this, true);
        textView = (TextView) view.findViewById(R.id.textView);
//        imageView = (ImageView)view.findViewById(R.id.imageView);
    }

    public void setViewGravity(){
        textView.setGravity(Gravity.LEFT);
    }
    public void setViewColor(int color){
        Drawable icon = getResources().getDrawable(R.drawable.icon_arrow_blue);
        textView.setTextColor(color);
        textView.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
    }

    public void setViewColor(int color,int id){
        Drawable icon = getResources().getDrawable(id);
        textView.setTextColor(color);
        textView.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
    }


    public void setText(CharSequence text) {
        textView.setText(text);
    }

    public void setChecked(boolean checked) {
        Drawable icon;
        isCheck = checked;
        if (checked) {

            icon = getResources().getDrawable(R.drawable.icon_arrow_blue);
            textView.setTextColor(getResources().getColor(R.color.text_color2));

        } else {
            icon = getResources().getDrawable(R.drawable.icon_arrow_gray);
            textView.setTextColor(getResources().getColor(R.color.text_color));
        }
//        imageView.setImageDrawable(icon);
        textView.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
    }


}
