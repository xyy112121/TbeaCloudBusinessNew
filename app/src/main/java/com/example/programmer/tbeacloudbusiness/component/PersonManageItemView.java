package com.example.programmer.tbeacloudbusiness.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;

/**
 * Created by programmer on 2017/6/4.
 */

public class PersonManageItemView  extends LinearLayout{
    private int mLeftImageIcon;
    private String  mText1;//内容文字
    private String  mText2;//内容文字
    private String  mText3;//内容文字
    private String  mText4;//内容文字
    private String  mText5;//内容文字
    public PersonManageItemView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public PersonManageItemView(Context context)
    {
        this(context, null);
    }

    public PersonManageItemView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.personManageItemView, defStyle, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++)
        {
            int attr = a.getIndex(i);
            switch (attr)
            {
                case R.styleable.personManageItemView_text1:
                    mText1 = a.getString(attr);
                    break;
                case R.styleable.personManageItemView_text2:
                    mText2 = a.getString(attr);
                    break;
                case R.styleable.personManageItemView_text3:
                    mText3 = a.getString(attr);
                    break;
                case R.styleable.personManageItemView_text4:
                    mText4 = a.getString(attr);
                    break;
                case R.styleable.personManageItemView_text5:
                    mText5 = a.getString(attr);
                    break;
                case R.styleable.personManageItemView_icon:
                    //显示的图片
                    mLeftImageIcon = a.getResourceId(attr, 0);
                    break;

            }

        }
        a.recycle();

        /**
         * 显示的视图
         */
        FrameLayout layout = (FrameLayout) View.inflate(context, R.layout.activity_plumber_manage_person_manage_item_view, null);;
        TextView text1Tv = (TextView) layout.findViewById(R.id.person_manage_item_tv1);
        TextView text2Tv = (TextView) layout.findViewById(R.id.person_manage_item_tv2);
        TextView text3Tv = (TextView) layout.findViewById(R.id.person_manage_item_tv3);
        TextView text4Tv = (TextView) layout.findViewById(R.id.person_manage_item_tv4);
        TextView text5Tv = (TextView) layout.findViewById(R.id.person_manage_item_tv5);
        ImageView leftIv = (ImageView)layout.findViewById(R.id.person_manage_item_iv);

       text1Tv.setText(mText1);
        text2Tv.setText(mText2);
        text3Tv.setText(mText3);
        text4Tv.setText(mText4);
        text5Tv.setText(mText5);
        leftIv.setImageResource(mLeftImageIcon);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        addView(layout);
    }

     public void setmText4(String text4){
         mText4 = text4;
     }

    public void setmText5(String text5){
        mText5 = text5;
    }
}
