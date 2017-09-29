package com.example.programmer.tbeacloudbusiness.activity.my.main.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.my.main.action.MyAction;
import com.example.programmer.tbeacloudbusiness.activity.my.main.model.AttentionPersonResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.my.main.model.FansListResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CircleImageView;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.DensityUtil;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 我的粉丝
 */

public class MyFansActivity extends BaseActivity implements View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {
    @BindView(R.id.my_fans_selete)
    TextView mFansSeleteView;
    @BindView(R.id.my_fans_cancel)
    Button mFansCancelView;
    @BindView(R.id.select_all)
    CheckBox mSelectAllView;
    private View mSelectAllLayout;
    private TextView rightView;
    private ListView mListView;
    private MyAdapter mAdapter;
    private int mPage = 1;
    private int mPagesiz = 10;
    private BGARefreshLayout mRefreshLayout;
    public boolean isSelect = false;
    private String mId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fans);
        ButterKnife.bind(this);
        mId = getIntent().getStringExtra("id");
        if(mId == null){
            initTopbar("我的粉丝", "编辑", this);
        }else {
            initTopbar("我的粉丝");
        }

        initUI();

    }

    /**
     * 实例化组件
     */
    private void initUI() {
        mFansCancelView.setText("删除");
        mSelectAllLayout = findViewById(R.id.fans_select_all_layout);
        rightView = (TextView) findViewById(R.id.top_right_text);
        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
        mRefreshLayout.beginRefreshing();

        mSelectAllView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mAdapter.btnSelectAllList();
                    mFansSeleteView.setText("全不选");
                } else {
                    mAdapter.btnNoList();
                    mFansSeleteView.setText("全选");
                }
            }
        });

        mFansCancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ids = "";
                if (mAdapter.mList != null) {
                    for (FansListResponseModel.DataBean.MyfocuslistBean item : mAdapter.mList) {
                        if (item.isCheck == true) {
                            if (ids.length() > 0) {
                                ids += ",";
                            }
                            ids += item.fansid;
                        }
                    }
                    if ("".equals(ids)) {
                        ToastUtil.showMessage("请选择需要取消关注的粉丝");
                    } else {
                        cancelAttention(ids);
                    }
                } else {
                    ToastUtil.showMessage("没有粉丝");
                }
            }
        });
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        //下拉
        mAdapter.removeAll();
        mPage = 1;
        getListData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        getListData();
        return true;
    }

    private void getListData() {
        try {
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    mRefreshLayout.endRefreshing();
                    mRefreshLayout.endLoadingMore();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            FansListResponseModel model = (FansListResponseModel) msg.obj;
                            if (model.isSuccess()) {
                                if (model.data.myfanslist != null) {
                                    mAdapter.addAll(model.data.myfanslist);
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
                    try {
                        MyAction action = new MyAction();
                        FansListResponseModel model = action.getFansList(mId,mPage++, mPagesiz);
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


    class MyAdapter extends BaseAdapter {

        private List<FansListResponseModel.DataBean.MyfocuslistBean> mList = new ArrayList<>();

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
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.fragment_attention_store_list_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (isSelect) {
                holder.mCk.setVisibility(View.VISIBLE);
            } else {
                holder.mCk.setVisibility(View.GONE);
            }

            holder.mCk.setChecked(mList.get(position).isCheck);

            //注意这里设置的不是onCheckedChangListener，还是值得思考一下的
            holder.mCk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mList.get(position).isCheck) {
                        mList.get(position).isCheck = false;
                    } else {
                        mList.get(position).isCheck = true;
                    }
                }
            });
//
            FansListResponseModel.DataBean.MyfocuslistBean obj = mList.get(position);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.thumbpicture, holder.mHeadView);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.persontypeicon, holder.mPersontypeiconView);
            holder.mNameView.setText(obj.name);
            holder.mJobtitleView.setText(obj.info);
            return convertView;
        }

        /**
         * 全选
         */
        public void btnSelectAllList() {
            if (isSelect) {
                for (int i = 0; i < mList.size(); i++) {
                    mList.get(i).isCheck = true;
                }
                notifyDataSetChanged();
            }
        }

        /**
         * 全不选
         */
        public void btnNoList() {
            if (isSelect) {
                for (int i = 0; i < mList.size(); i++) {
                    mList.get(i).isCheck = false;
                }
                notifyDataSetChanged();
            }
        }

        public void removeAll() {
            mList.clear();
            notifyDataSetChanged();
        }

        public void addAll(List<FansListResponseModel.DataBean.MyfocuslistBean> list) {
            mList.addAll(list);
            notifyDataSetChanged();
        }

        class ViewHolder {
            @BindView(R.id.attention_item_ck)
            CheckBox mCk;
            @BindView(R.id.attention_item_head)
            CircleImageView mHeadView;
            @BindView(R.id.attention_item_name)
            TextView mNameView;
            @BindView(R.id.attention_item_persontypeicon)
            ImageView mPersontypeiconView;
            @BindView(R.id.attention_item_jobtitle)
            TextView mJobtitleView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if ("编辑".equals(rightView.getText() + "")) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mListView.getLayoutParams();
            layoutParams.bottomMargin = DensityUtil.dip2px(mContext, 44);
            mListView.setLayoutParams(layoutParams);
            rightView.setText("完成");
            mSelectAllLayout.setVisibility(View.VISIBLE);
            isSelect = true;
            mAdapter.notifyDataSetChanged();
        } else {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mListView.getLayoutParams();
            layoutParams.bottomMargin = 0;
            mListView.setLayoutParams(layoutParams);
            rightView.setText("编辑");
            mSelectAllLayout.setVisibility(View.GONE);
            isSelect = false;
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 取消关注
     */
    public void cancelAttention(final String ids) {
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("请等待...");
        dialog.show();
        try {
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    dialog.dismiss();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            ResponseInfo model = (ResponseInfo) msg.obj;
                            if (model.isSuccess()) {
                                mRefreshLayout.beginRefreshing();
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
                        MyAction action = new MyAction();
                        ResponseInfo model = action.cancelFans(ids);
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
}
