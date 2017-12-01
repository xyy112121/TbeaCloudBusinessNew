package com.example.programmer.tbeacloudbusiness.activity.franchisee.scan.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;

import com.example.programmer.tbeacloudbusiness.utils.UtilAssistants;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * 扫码界面
 */

public class ScanCodeAcctivity extends BaseActivity {
    private boolean  mFlag = false;//控制是否打开闪关灯
    private final  int REQUEST_IMAGE = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sacn_code);
        //多个权限获取
        checkPermission(new CheckPermListener() {
            @Override
            public void Granted() {
                /**
                 /* 执行扫面Fragment的初始化操作
                 */
                CaptureFragment captureFragment = new CaptureFragment();
                // 为二维码扫描界面设置定制化界面
                CodeUtils.setFragmentArgs(captureFragment, R.layout.activity_scancode_my_camera);

                captureFragment.setAnalyzeCallback(analyzeCallback);
                /**
                 * 替换我们的扫描控件
                 */
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
            }

            @Override
            public void Denied() {
                //如果选择不在提示
                finish();
                showMessage("请开启权限！");
            }
        }, "请求获取照相机和读取文件权限", Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        listener();
    }


    private void listener(){

        findViewById(R.id.scan_code_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent localIntent2 = new Intent();
                localIntent2.addCategory(Intent.CATEGORY_OPENABLE);
                localIntent2.setType("image/*");
                localIntent2.putExtra("return-data", true);
                localIntent2
                        .setAction("android.intent.action.GET_CONTENT");
                startActivityForResult(localIntent2, REQUEST_IMAGE);
            }
        });

        findViewById(R.id.scan_code_flashlight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mFlag == false){
                    /**
                     * 打开闪光灯
                     */
                    CodeUtils.isLightEnable(true);
                    mFlag = true;
                }else {
                    CodeUtils.isLightEnable(false);
                    mFlag = false;
                }
            }
        });

        findViewById(R.id.scan_code_input).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,ScanCodeInputActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.top_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    CodeUtils.analyzeBitmap(UtilAssistants.getPath(mContext,uri), new CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                            provingScanCode(result);//二维码有效性检验
                        }

                        @Override
                        public void onAnalyzeFailed() {
                            showMessage("解析二维码失败");
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, final String result) {
            provingScanCode(result);//二维码有效性检验
        }

        @Override
        public void onAnalyzeFailed() {
            showMessage("解析二维码失败");
        }
    };

    public  void provingScanCode(final String result){
        if(result.indexOf("tbscrfl") != -1){
            Intent intent = new Intent(mContext,ScanCodePayConfirmActivity.class);
            intent.putExtra("code",result);
            startActivity(intent);
            finish();
        }else {
            //二维码无效
            Intent intent = new Intent(mContext,CodeBraceActivity.class);
            startActivity(intent);
        }
    }
}
