package com.example.programmer.tbeacloudbusiness.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

/**
 * Created by DELL on 2017/7/9.
 */

public class SpannableUtils {
    private Context mContext;

    public SpannableString format(String value,int color,int beginIndex,int endIndex ){
        SpannableString spannableString = new SpannableString(value);
        spannableString.setSpan(new ForegroundColorSpan(color), beginIndex,endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
       return  spannableString;
    }
}
