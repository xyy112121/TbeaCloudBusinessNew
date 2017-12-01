package com.example.programmer.tbeacloudbusiness.activity.check.tbws.activity;

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
import com.example.programmer.tbeacloudbusiness.activity.check.tbws.action.SubscribeAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.model.info.MyPictureListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.publicUse.activity.PictureShowActivity;
import com.example.programmer.tbeacloudbusiness.activity.user.model.PicturelistBean;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;

import com.example.programmer.tbeacloudbusiness.utils.UtilAssistants;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的云布线图
 */

public class UploadPictureListActivity extends BaseActivity implements View.OnClickListener {
    GridAdapter mGridAdapter;
    @BindView(R.id.gridView)
    GridView mGridView;
    @BindView(R.id.cp_meeting_gallery_image)
    ImageView mGalleryImageView;
    private String mFlag;

    public List<PicturelistBean> picturelist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_picture_list);
        ButterKnife.bind(this);
        initTopbar("全部图片");
        initView();
    }

    private void initView() {
        mGridAdapter = new GridAdapter(mContext, R.layout.activity_my_picture_item);
        mGridView.setAdapter(mGridAdapter);

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("number", mGridAdapter.getCount() + "");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        mGridAdapter.clear();
        getData();
    }

    //获取信息
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
                            MyPictureListResponseModel model = (MyPictureListResponseModel) msg.obj;
                            if (model.isSuccess() && model.data != null) {
                                if (model.data.picturelist != null) {
                                    picturelist = model.data.picturelist;
                                    mGridAdapter.addAll(model.data.picturelist);
                                    mGalleryImageView.setVisibility(View.GONE);
                                    mGridView.setVisibility(View.VISIBLE);
                                } else {
                                    mGalleryImageView.setVisibility(View.VISIBLE);
                                    mGridView.setVisibility(View.GONE);
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
                        SubscribeAction action = new SubscribeAction();
                        String checkResultId = getIntent().getStringExtra("checkResultId");
                        MyPictureListResponseModel model = action.getPicList(checkResultId);
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
//        Intent intent = new Intent(mContext, MeetingGalleryUploadActivity.class);
//        intent.putExtra("meetingid", getIntent().getStringExtra("meetingid"));
//        startActivity(intent);

    }


    public class GridAdapter extends ArrayAdapter<PicturelistBean> {
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
            final PicturelistBean obj = getItem(postion);
            ImageLoader.getInstance().displayImage(obj.thumbpicture, holder.mImageView);
            holder.mDeleteView.setVisibility(View.GONE);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PictureShowActivity.class);
                    intent.putExtra("images", (Serializable) picturelist);
                    intent.putExtra("index", postion);
                    startActivity(intent);
                }
            });

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
