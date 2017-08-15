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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.action.CpPlumberMeetingAction;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.MeetingGalleryListResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.example.programmer.tbeacloudbusiness.utils.UtilAssistants;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 现场图片查看
 */

public class MeetingGalleryListActivity extends BaseActivity implements View.OnClickListener {

//    List<LocalMedia> mSelectList = new ArrayList<>();
    GridAdapter mGridAdapter;
    @BindView(R.id.gridView)
    GridView mGridView;
    @BindView(R.id.cp_meeting_gallery_image)
    ImageView mGalleryImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cp_meeting_gallery_list);
        ButterKnife.bind(this);
        initTopbar("现场图片", "上传", this);
        initView();
    }

    private void initView() {
        mGridAdapter = new GridAdapter(mContext, R.layout.activity_shop_dynamic_add_image_item);
        mGridView.setAdapter(mGridAdapter);
//        mGridAdapter.addAll(mSelectList);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mGridAdapter.clear();
        getData();
    }

    //保存会议纪要
    private void getData() {
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
                            MeetingGalleryListResponseModel model = (MeetingGalleryListResponseModel) msg.obj;
                            if (model.isSuccess() && model.data != null) {
                                if (model.data.picturelist != null) {
                                    mGridAdapter.addAll(model.data.picturelist);
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
                        CpPlumberMeetingAction action = new CpPlumberMeetingAction();
                        String id = getIntent().getStringExtra("meetingid");
                        MeetingGalleryListResponseModel model = action.getGalleryList(id);
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
    public void onClick(View v) {
        Intent intent = new Intent(mContext,MeetingGalleryUploadActivity.class);
        startActivity(intent);

    }


    public class GridAdapter extends ArrayAdapter<MeetingGalleryListResponseModel.DataBean.PictureBean> {
        int resourceId;


        public GridAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);
            resourceId = resource;
        }

        @Override
        public View getView(final int postion, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = getLayoutInflater().inflate(resourceId, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            int displayWidth = UtilAssistants.getDisplayWidth(mContext);
            holder.mImageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (displayWidth / 4)));
            MeetingGalleryListResponseModel.DataBean.PictureBean obj = getItem(postion);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.thumbpictureurl, holder.mImageView);
            holder.mDeleteView.setVisibility(View.GONE);
            return view;
        }

        class ViewHolder {
            @BindView(R.id.shop_dynamic_add_list_item_image)
            ImageView mImageView;
            @BindView(R.id.shop_dynamic_add_list_item_image_delete)
            ImageView mDeleteView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
