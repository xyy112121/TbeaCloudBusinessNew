package com.example.programmer.tbeacloudbusiness.activity.publicUse.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.example.programmer.tbeacloudbusiness.activity.publicUse.model.JsToJavaPictureTbeaModuel;
import com.example.programmer.tbeacloudbusiness.activity.publicUse.model.TbeaPictrueResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.tbea.action.TbeaAction;
import com.example.programmer.tbeacloudbusiness.activity.tbea.model.ProductPresentationListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.user.model.PicturelistBean;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;

/**
 * Created by DELL on 2017/11/24.
 */

public class JsToJava {
    Context mContext;
    public JsToJava(Context c) {
        mContext= c;
    }

    /**
     * 与js交互时用到的方法，在js里直接调用的(特变电工-图片参数)
     */
    @JavascriptInterface
    public void showlargepicture(String value) {
        if(value != null ||!value.isEmpty()){
            Gson gson = new GsonBuilder().serializeNulls().create();
            final JsToJavaPictureTbeaModuel moduel = gson.fromJson(value,JsToJavaPictureTbeaModuel.class);
            final Handler handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            TbeaPictrueResponseModel model = (TbeaPictrueResponseModel) msg.obj;
                            if (model.isSuccess()) {
                                if (model.data != null ){
                                    Intent intent = new Intent(mContext, PictureShowActivity.class);
                                    intent.putExtra("images", (Serializable) model.data.picturelist);
                                    intent.putExtra("index", moduel.sequence);
                                    mContext.startActivity(intent);
                                }

                            } else {
                                ToastUtil.showMessage(model.getMsg());
                            }
                            break;
                        case ThreadState.ERROR:
                            ToastUtil.showMessage("操作失败！");
                            break;
                    }
                }
            };

            new Thread(new Runnable() {
                @Override
                public void run() {
                    TbeaAction action = new TbeaAction();
                    try {
                        TbeaPictrueResponseModel model = action.getCommodityPicture(moduel.id);
                        handler.obtainMessage(ThreadState.SUCCESS, model).sendToTarget();
                    } catch (Exception e) {
                        handler.sendEmptyMessage(ThreadState.ERROR);
                    }
                }
            }).start();
        }
    }
}
