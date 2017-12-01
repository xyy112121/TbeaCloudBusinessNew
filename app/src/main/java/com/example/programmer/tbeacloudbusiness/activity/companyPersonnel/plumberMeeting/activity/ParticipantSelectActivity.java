package com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.action.CpPlumberMeetingAction;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.ParticipantSelectlListResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CircleImageView;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 公司人员-准备-参与人员选择(多选)
 */

public class ParticipantSelectActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.cp_franchiser_list_number)
    TextView mNumberView;
    private ListView mListView;
    private MyAdapter mAdapter;
    List<ParticipantSelectlListResponseModel.DataBean.MeetingparticipantlistBean> mSelectList = new ArrayList<>();
    String[] mSelectIds = new String[]{};
    // 用来控制CheckBox的选中状况
    private static HashMap<Integer, Boolean> isSelected;
//    private boolean mFlag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cp_franchiser_select_list);
//        mFlag = getIntent().getBooleanExtra("flag", false);//true可选择
//        if(mFlag == false){
        initTopbar("用户列表");
//        }else {
//            initTopbar("用户列表", "确定", this);
//        }
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        String ids = getIntent().getStringExtra("ids");
        if (!"".equals(ids) && ids != null) {
            mSelectIds = ids.split(",");
        }
        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new MyAdapter(mContext, R.layout.activity_cp_franchiser_select_list_item);
        mListView.setAdapter(mAdapter);
        getListData();
    }

    /**
     * 从服务器获取数据
     */
    private void getListData() {
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
                            ParticipantSelectlListResponseModel model = (ParticipantSelectlListResponseModel) msg.obj;
                            if (model.isSuccess() && model.data != null) {
                                if (model.data.meetingparticipantlist != null) {
                                    mAdapter.initDate(model.data.meetingparticipantlist.size(), model.data.meetingparticipantlist);
                                    mAdapter.addAll(model.data.meetingparticipantlist);
                                }
//                                if (model.data.meetingparticipantlist != null) {
//                                    mAdapter.initDate(model.data.meetingparticipantlist.size(),model.data.meetingparticipantlist);
//                                    mAdapter.addAll(model.data.meetingparticipantlist);
//                                }

                            } else {
                                showMessage(model.getMsg());
                            }
                            break;
                        case ThreadState.ERROR:
                            showMessage("操作失败！");
                            break;
                    }
                }
            };

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        CpPlumberMeetingAction action = new CpPlumberMeetingAction();
                        ParticipantSelectlListResponseModel model;
//                        if (mFlag) {
//                            model = action.getParticipantListAll();
//                        } else {
                        String meetingId = getIntent().getStringExtra("meetingId");
                        model = action.getParticipantList(meetingId);
//                        }
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

    @Override
    public void onClick(View view) {
        if (mSelectList.size() > 0) {
            Intent in = new Intent();
            in.putExtra("selectObj", (Serializable) mSelectList);
            setResult(RESULT_OK, in);
            finish();
        } else {
            showMessage("未选择用户");
        }
    }

    public class MyAdapter extends ArrayAdapter<ParticipantSelectlListResponseModel.DataBean.MeetingparticipantlistBean> {
        int resourceId;

        public MyAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);
            resourceId = resource;
            isSelected = new HashMap<>();
            // 初始化数据
            initDate(getCount(), null);
        }

        // 初始化isSelected的数据
        public void initDate(int count, List<ParticipantSelectlListResponseModel.DataBean.MeetingparticipantlistBean> list) {
            for (int i = 0; i < count; i++) {
                if (mSelectIds.length > 0) {
                    for (String item : mSelectIds) {
                        if (item.equals(list.get(i).userid)) {
                            getIsSelected().put(i, true);
                            break;
                        } else {
                            getIsSelected().put(i, false);
                        }
                    }
                } else {
                    getIsSelected().put(i, false);
                }
            }
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(resourceId, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final ParticipantSelectlListResponseModel.DataBean.MeetingparticipantlistBean obj = getItem(position);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.thumbpicture, holder.mHeadView);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.persontypeicon, holder.mPersonjobtitleView);
            holder.mNameView.setText(obj.name);
            holder.mCompanyNameView.setText(obj.companyandjobposition);
            holder.mCkView.setVisibility(View.GONE);
//            if (mFlag == false) {
//                holder.mCkView.setVisibility(View.GONE);
//            } else {
//                holder.mCkView.setChecked(isSelected.get(position));
//                if(isSelected.get(position) == true){
//                    mSelectList.add(getItem(position));
//                    removeDuplicate(mSelectList);
//                }
//                convertView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (isSelected.get(position)) {
//                            isSelected.put(position, false);
//                            mSelectList.remove(getItem(position));
//                            mNumberView.setText(mSelectList.size() + "");
//                        } else {
//                            isSelected.put(position, true);
//                            mSelectList.add(getItem(position));
//                            removeDuplicate(mSelectList);
//                        }
//                        notifyDataSetChanged();
//                    }
//                });
//            }
            holder.mRightView.setVisibility(View.GONE);
            return convertView;
        }

        public HashMap<Integer, Boolean> getIsSelected() {
            return isSelected;
        }


        class ViewHolder {
            @BindView(R.id.person_info_head)
            CircleImageView mHeadView;
            @BindView(R.id.person_info_name)
            TextView mNameView;
            @BindView(R.id.person_info_personjobtitle)
            ImageView mPersonjobtitleView;
            @BindView(R.id.person_info_companyname)
            TextView mCompanyNameView;
            @BindView(R.id.person_info_right)
            ImageView mRightView;
            @BindView(R.id.cp_franchiser_select_item_ck)
            CheckBox mCkView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

    }

    public void removeDuplicate(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        mSelectList.addAll(h);
        mNumberView.setText(mSelectList.size() + "");
    }
}