package com.example.programmer.tbeacloudbusiness.activity.my.set;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.TopActivity;


/**
 * 新增收货地址
 */

public class AddressEditActivity extends TopActivity implements View.OnClickListener {
    private Context mContext;
//    private Address mObj = new Address();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_edit);
        mContext = this;
        if("edit".equals(getIntent().getStringExtra("flag"))){
//            Gson gson = new Gson();
//            String objGson = getIntent().getStringExtra("obj");
//            mObj = gson.fromJson(objGson,Address.class);
            initTopbar("收货地址管理","删除",this);
//            getDate(mObj.getId());
        }else {
            initTopbar("收货地址管理","保存",this);
        }
    }

    /**
     * 验证手机格式 false不正确
     */
    public  boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][34578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (mobiles.equals("")) return false;
        else return mobiles.matches(telRegex);
    }

    @Override
    public void onClick(View v) {

    }
}
