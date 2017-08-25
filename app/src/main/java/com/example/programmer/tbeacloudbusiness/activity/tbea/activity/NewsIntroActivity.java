package com.example.programmer.tbeacloudbusiness.activity.tbea.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.publicUse.activity.WebViewActivity;
import com.example.programmer.tbeacloudbusiness.activity.tbea.action.TbeaAction;
import com.example.programmer.tbeacloudbusiness.activity.tbea.model.CompanyIntroListResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻资讯
 */

public class NewsIntroActivity extends BaseActivity {
    private int mIndex = 0;
    private int mIndex2 = -1;//前一次点击的下标
    private List<FrameLayout> mListLayout = new ArrayList<>();
    private WebView mWebView;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tbea_company_intro);
        initView();
        getData("companynews");
    }

    public void initView() {
        initTopLayout();

        mWebView = getViewById(R.id.company_intro_wb);
        ListView listView = (ListView) findViewById(R.id.listview);
        mAdapter = new MyAdapter();
        listView.setAdapter(mAdapter);
    }

    /**
     * 初始化顶部点击菜单
     */
    private void initTopLayout() {
        initTopbar("新闻资讯");
        String[] topTexts = new String[]{"公司动态", "行业新闻", "家装资讯"};

        //获取屏幕的宽度
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int screenWidth = outMetrics.widthPixels;

        LinearLayout parentLayout = (LinearLayout) findViewById(R.id.company_intro_top_layout);
        for (int i = 0; i < topTexts.length; i++) {
            final int index = i;
            final FrameLayout layout = (FrameLayout) getLayoutInflater().inflate(R.layout.fragment_company_top_layout_item, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(screenWidth / topTexts.length, LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.setLayoutParams(lp);
            TextView t = (TextView) layout.findViewById(R.id.fragment_company_top_tv);
            t.setText(topTexts[i]);
            if (i == 0) {
                ((TextView) layout.findViewById(R.id.fragment_company_top_tv)).setTextColor(ContextCompat.getColor(mContext, R.color.blue));
//
            }
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mIndex2 = mIndex;
                    mIndex = index;
                    if (mIndex2 != -1 && mIndex != mIndex2) {
                        ((TextView) view.findViewById(R.id.fragment_company_top_tv)).setTextColor(ContextCompat.getColor(mContext, R.color.blue));
                        setViewColor();
                    }
                    String type = "";
                    switch (mIndex) {
                        case 0:
                            type = "companynews";
                            break;
                        case 1:
                            type = "industrynews";
                            break;
                        case 2:
                            type = "decorationnews";
                            break;
                    }
                    mAdapter.removeAll();
                    getData(type);

                }
            });
            mListLayout.add(layout);
            parentLayout.addView(layout);
        }
    }

    private void setViewColor() {
        FrameLayout layout = mListLayout.get(mIndex2);
        ((TextView) layout.findViewById(R.id.fragment_company_top_tv)).setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
    }

    private void getData(final String type) {
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("加载中...");
        dialog.show();
        try {
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    dialog.dismiss();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            CompanyIntroListResponseModel model = (CompanyIntroListResponseModel) msg.obj;
                            if (model.isSuccess()) {
                                if (model.data != null && model.data.newslist != null)
                                    mAdapter.addAll(model.data.newslist);
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
                    try {
                        TbeaAction action = new TbeaAction();
                        CompanyIntroListResponseModel model = action.getCompanyDynamic(type);
                        handler.obtainMessage(ThreadState.SUCCESS, model).sendToTarget();
                    } catch (Exception e) {
                        handler.sendEmptyMessage(ThreadState.ERROR);
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class MyAdapter extends BaseAdapter {

        public List<CompanyIntroListResponseModel.CompanyIntro> mList = new ArrayList<>();

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = getLayoutInflater().inflate(
                        R.layout.activity_tbea_company_intro_list_item, null);
            }
            final CompanyIntroListResponseModel.CompanyIntro obj = mList.get(position);

            ImageView imageView = (ImageView) view.findViewById(R.id.tbea_company_intro_item_thumb);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.thumb, imageView);
            ((TextView) view.findViewById(R.id.tbea_company_intro_item_title)).setText(obj.title);
            ((TextView) view.findViewById(R.id.tbea_company_intro_item_author)).setText(obj.author);
            ((TextView) view.findViewById(R.id.tbea_company_intro_item_time)).setText(obj.time);

             view.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Intent intent = new Intent(mContext, WebViewActivity.class);
                     String url = "http://121.42.193.154:6696/index.php/h5forapp/Index/viewnews?newsid="+ obj.id;
                     intent.putExtra("url", url);
                     intent.putExtra("title", "新闻资讯");
                     startActivity(intent);
                 }
             });
            return view;
        }


        public void remove(int index) {
            if (index > 0) {
                mList.remove(index);
                notifyDataSetChanged();
            }
        }

        public void addAll(List<CompanyIntroListResponseModel.CompanyIntro> list) {
            mList.addAll(list);
            notifyDataSetChanged();
        }

        public void removeAll() {
            mList.clear();
            notifyDataSetChanged();
        }
    }
}
