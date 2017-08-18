package com.example.programmer.tbeacloudbusiness.activity.my.main.attention;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.my.main.action.MyAction;
import com.example.programmer.tbeacloudbusiness.activity.my.main.activity.MyAttentionActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.main.model.AttentionPersonResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.my.main.model.AttentionStoreResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CircleImageView;
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

public class PersonFragment extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private View mView;
    private ListView mListView;
    private MyAdapter mAdapter;
    private int mPage = 1;
    private int mPagesiz = 10;
    private BGARefreshLayout mRefreshLayout;
    public boolean isSelect = false;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_attention_list, container, false);
        initUI();//实例化控件
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mRefreshLayout.beginRefreshing();
    }


    /**
     * 实例化组件
     */
    private void initUI() {
        mListView = (ListView) mView.findViewById(R.id.listview);
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
        mRefreshLayout = (BGARefreshLayout) mView.findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
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
                            AttentionPersonResponseModel model = (AttentionPersonResponseModel) msg.obj;
                            if (model.isSuccess()) {
                                if (model.data.myfocuslist != null) {
                                    mAdapter.addAll(model.data.myfocuslist);
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
                        AttentionPersonResponseModel model = action.getAttentionPersonList(mPage++, mPagesiz);
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

    public void refreshDate() {
        if (mRefreshLayout != null) {
            mRefreshLayout.beginRefreshing();
        }
    }

    public void refresh() {
        try {
            if (mAdapter != null) {
                if (isSelect) {
                    isSelect = false;
                    mAdapter.notifyDataSetChanged();
                    ((MyAttentionActivity) getActivity()).mSelectAllLayout.setVisibility(View.GONE);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mListView.getLayoutParams();
                    layoutParams.bottomMargin = 0;
                    mListView.setLayoutParams(layoutParams);
                } else {
                    isSelect = true;
                    mAdapter.notifyDataSetChanged();
                    ((MyAttentionActivity) getActivity()).mSelectAllLayout.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mListView.getLayoutParams();
                    layoutParams.bottomMargin = DensityUtil.dip2px(getActivity(), 44);
                    mListView.setLayoutParams(layoutParams);
                }
                ((CheckBox) ((MyAttentionActivity) getActivity()).mSelectAllLayout.findViewById(R.id.select_all)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        TextView tv = ((TextView) ((MyAttentionActivity) getActivity()).mSelectAllLayout.findViewById(R.id.select_all_text));
                        if (isChecked) {
                            tv.setTag("全不选");
                            mAdapter.btnSelectAllList();
                        } else {
                            tv.setTag("全选");
                            mAdapter.btnNoList();
                        }
                    }
                });

                ((MyAttentionActivity) getActivity()).mSelectAllLayout.findViewById(R.id.my_attention_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ids = "";
                        if (mAdapter.mList != null) {
                            for (AttentionPersonResponseModel.DataBean.MyfocuslistBean item : mAdapter.mList) {
                                if (item.isCheck == true) {
                                    if (ids.length() > 0) {
                                        ids += ",";
                                    }
                                    ids += item.focusid;
                                }
                            }
                            if ("".equals(ids)) {
                                ToastUtil.showMessage("请选择需要取消关注的个人");
                            } else {
                                ((MyAttentionActivity) getActivity()).cancelAttention(ids);
                            }
                        } else {
                            ToastUtil.showMessage("没有关注的个人");
                        }
                    }
                });
            }
        } catch (Exception e) {
            Log.e("", "");
        }

    }

    class MyAdapter extends BaseAdapter {

        private List<AttentionPersonResponseModel.DataBean.MyfocuslistBean> mList = new ArrayList<>();

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
                convertView = getActivity().getLayoutInflater().inflate(R.layout.fragment_attention_store_list_item, null);
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
            AttentionPersonResponseModel.DataBean.MyfocuslistBean obj = mList.get(position);
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

        public void addAll(List<AttentionPersonResponseModel.DataBean.MyfocuslistBean> list) {
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
}

