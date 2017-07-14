package com.example.programmer.tbeacloudbusiness.component;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.utils.DensityUtil;

/**
 * 发布的单行输入框
 */

public class PublishTextRowView extends LinearLayout {
    private String mTitleText;
    private String mHiht;
    private int mTextSize;
    private int mTextColor;
    private boolean mIsTopLineShow;
    private boolean mIsBottomLineShow;
    private boolean mIsSelect;//true 是，
    private TextView mValueView;
    private ClearEditText mValueView2;
    private int mWidth;

    public PublishTextRowView(Context context) {
        this(context,null);
    }

    public PublishTextRowView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PublishTextRowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PublishTextRowView,defStyleAttr,0);
        mTitleText = a.getString(R.styleable.PublishTextRowView_PublishTextRowViewText);
        mHiht = a.getString(R.styleable.PublishTextRowView_PublishTextRowViewHiht);
        mIsBottomLineShow = a.getBoolean(R.styleable.PublishTextRowView_PublishTextRowViewIsBottomLineShow,true);
        mTextColor = a.getColor(R.styleable.PublishTextRowView_PublishTextRowViewTextColor, ContextCompat.getColor(context,R.color.tab_text_normal));
        mTextSize = a.getDimensionPixelSize(R.styleable.PublishTextRowView_PublishTextRowViewTextSize, DensityUtil.dip2px(context,12));
        mIsSelect = a.getBoolean(R.styleable.PublishTextRowView_PublishTextRowViewIsSelect,false);
        mWidth = a.getDimensionPixelSize(R.styleable.PublishTextRowView_PublishTextRowViewLeftWidth,DensityUtil.dip2px(context,140));
        initView(context);
    }

    public void setValueText(String text){
        mValueView.setText(text);
    }

    public String getValueText(){
        if(mValueView.getVisibility() == VISIBLE){
            return mValueView.getText()+"";
        }else {
            return mValueView2.getText()+"";
        }
    }

    public void initView(Context context){
        View view = ((Activity)context).getLayoutInflater().inflate(R.layout.view_pulish_text_row,null);
        TextView leftView = (TextView) view.findViewById(R.id.left);
        mValueView2 = (ClearEditText) view.findViewById(R.id.rowvalue);
        mValueView = (TextView) view.findViewById(R.id.row_value);
        View bottomView = view.findViewById(R.id.bottom_line);
        ImageView rightView = (ImageView)view.findViewById(R.id.right_image);
        leftView.setText(mTitleText);
        mValueView2.setHint(mHiht);
        leftView.setTextColor(mTextColor);
        LayoutParams params = new LayoutParams(mWidth,LayoutParams.MATCH_PARENT);
        leftView.setLayoutParams(params);

        if (mIsBottomLineShow == false){
            bottomView.setVisibility(GONE);
        }
        if (mIsSelect){
            mValueView2.setVisibility(GONE);
            mValueView.setVisibility(VISIBLE);
            rightView.setVisibility(VISIBLE);
        }else {
            mValueView2.setVisibility(VISIBLE);
            mValueView.setVisibility(GONE);
        }
        addView(view);
    }

}
