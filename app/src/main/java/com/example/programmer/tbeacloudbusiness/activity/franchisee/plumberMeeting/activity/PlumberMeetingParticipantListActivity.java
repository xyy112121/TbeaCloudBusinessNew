package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.action.PlumberMeetingAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model.PlumberMeetingParticipantResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CircleImageView;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 参与人员
 */

public class PlumberMeetingParticipantListActivity extends BaseActivity {
    @BindView(R.id.listview)
    ListView mListView;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber_meeting_participant_list);
        ButterKnife.bind(this);
        initTopbar("参与人员");
        initView();
        getListData();
    }

    private void initView() {
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
    }

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
                            PlumberMeetingParticipantResponseModel model = (PlumberMeetingParticipantResponseModel) msg.obj;
                            if (model.isSuccess() && model.data != null) {
                                if (model.data.meetingparticipantlist != null) {
                                    mAdapter.addAll(model.data.meetingparticipantlist);
                                }

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
                        PlumberMeetingAction action = new PlumberMeetingAction();
                        String id = getIntent().getStringExtra("meetingId");
                        PlumberMeetingParticipantResponseModel model = action.getParticipantList(id);
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


    public class MyAdapter extends BaseAdapter {

        public List<PlumberMeetingParticipantResponseModel.MeetingParticipant> mList = new ArrayList<>();


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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.activity_plumber_meeting_participant_list_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            PlumberMeetingParticipantResponseModel.MeetingParticipant obj = mList.get(position);
            holder.mNameView.setText(obj.name);
            holder.mCompanynameView.setText(obj.companyandjobposition);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath()+obj.thumbpicture,holder.mHeadView);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath()+obj.persontypeicon,holder.mPersonjobtitleView);
            holder.mRightView.setVisibility(View.GONE);
            return convertView;
        }


        public void remove(int index) {
            if (index > 0) {
                mList.remove(index);
                notifyDataSetChanged();
            }
        }

        public void addAll(List<PlumberMeetingParticipantResponseModel.MeetingParticipant> list) {
            mList.addAll(list);
            notifyDataSetChanged();
        }

        public void removeAll() {
            mList.clear();
            notifyDataSetChanged();
        }

        class ViewHolder {
            @BindView(R.id.person_info_head)
            CircleImageView mHeadView;
            @BindView(R.id.person_info_name)
            TextView mNameView;
            @BindView(R.id.person_info_personjobtitle)
            ImageView mPersonjobtitleView;
            @BindView(R.id.person_info_companyname)
            TextView mCompanynameView;
            @BindView(R.id.person_info_right)
            ImageView mRightView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
