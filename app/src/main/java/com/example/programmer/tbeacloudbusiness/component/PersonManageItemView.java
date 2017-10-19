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
    private boolean isMoney;
    public TextView mTextView4;
    public TextView mTextView5;
    Text4Click mText4Click;
    Text5Click mText5Click;

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
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PersonManageItemView, defStyle, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++)
        {
            int attr = a.getIndex(i);
            switch (attr)
            {
                case R.styleable.PersonManageItemView_text1:
                    mText1 = a.getString(attr);
                    break;
                case R.styleable.PersonManageItemView_text2:
                    mText2 = a.getString(attr);
                    break;
                case R.styleable.PersonManageItemView_text3:
                    mText3 = a.getString(attr);
                    break;
                case R.styleable.PersonManageItemView_text4:
                    mText4 = a.getString(attr);
                    break;
                case R.styleable.PersonManageItemView_text5:
                    mText5 = a.getString(attr);
                    break;
                case R.styleable.PersonManageItemView_icon:
                    //显示的图片
                    mLeftImageIcon = a.getResourceId(attr, 0);
                    break;
                case R.styleable.PersonManageItemView_isMoney:
                    isMoney = a.getBoolean(attr,false);
                    break;

            }

        }
        a.recycle();

        /**
         * 显示的视图
         */
        final FrameLayout layout = (FrameLayout) View.inflate(context, R.layout.activity_plumber_manage_person_manage_item_view, null);;
        TextView text1Tv = (TextView) layout.findViewById(R.id.person_manage_item_tv1);
        TextView text2Tv = (TextView) layout.findViewById(R.id.person_manage_item_tv2);
        TextView text3Tv = (TextView) layout.findViewById(R.id.person_manage_item_tv3);
        mTextView4 = (TextView) layout.findViewById(R.id.person_manage_item_tv4);
        mTextView5 = (TextView) layout.findViewById(R.id.person_manage_item_tv5);
        TextView text4LabelTv = (TextView) layout.findViewById(R.id.person_manage_item_tv4_label);
        TextView text5LabelTv = (TextView) layout.findViewById(R.id.person_manage_item_tv5_label);
        ImageView leftIv = (ImageView)layout.findViewById(R.id.person_manage_item_iv);

        LinearLayout layout4 = (LinearLayout)layout.findViewById(R.id.person_manage_item_tv4_layout);
//        if(mText4Click != null){
            layout4.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mText4Click != null){
                        mText4Click.onClick();
                    }else {
                        performClick();
                    }
                }
            });

            text2Tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mText4Click != null){
                        mText4Click.onClick();
                    }else {
                        performClick();
                    }
                }
            });
//        }

//        if(mText5Click != null){
            LinearLayout layout5 = (LinearLayout)layout.findViewById(R.id.person_manage_item_tv5_layout);
            layout5.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mText5Click != null){
                        mText5Click.onClick();
                    }else {
                        performClick();
                    }
                }
            });

            text3Tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mText5Click != null){
                        mText5Click.onClick();
                    }else {
                        performClick();
                    }
                }
            });
//        }

        if (isMoney){
            text4LabelTv.setVisibility(VISIBLE);
            text5LabelTv.setVisibility(VISIBLE);
        }

       text1Tv.setText(mText1);
        text2Tv.setText(mText2);
        text3Tv.setText(mText3);
        mTextView4.setText(mText4);
        mTextView5.setText(mText5);
        leftIv.setImageResource(mLeftImageIcon);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        addView(layout);
    }

    public interface Text4Click{
         void onClick();
    }

    public interface Text5Click{
        void onClick();
    }

    public void setText4Click(Text4Click click){
        mText4Click = click;
    }

    public void setText5Click(Text5Click click){
        mText5Click = click;
    }

     public void setText4(String text4){
         mTextView4.setText(text4);
     }



    public void setText5(String text5){
       mTextView5.setText(text5);
    }
}
