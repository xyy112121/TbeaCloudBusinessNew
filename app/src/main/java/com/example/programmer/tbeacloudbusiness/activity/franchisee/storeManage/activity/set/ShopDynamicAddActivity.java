package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity.set;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.utils.UtilAssistants;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 商铺动态 - 发表文章
 */

public class ShopDynamicAddActivity extends BaseActivity implements View.OnClickListener {
    List<LocalMedia> mSelectList = new ArrayList<>();
    GridAdapter mGridAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_dynamic_add);
        initTopbar("发表文章", "预览", this);
        initView();
    }

    private void initView(){
        GridView gridView = (GridView) findViewById(R.id.shop_dynamic_image_gridView);
        mGridAdapter = new GridAdapter();
        gridView.setAdapter(mGridAdapter);
        mGridAdapter.addAll(mSelectList);

    }

    @Override
    public void onClick(View v) {

    }


    private void openImage() {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(ShopDynamicAddActivity.this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(8)// 最大图片选择数量 int
                .compress(true)
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.THIRD_GEAR、Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .selectionMedia(mSelectList)// 是否传入已选图片 List<LocalMedia> list
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    mSelectList = PictureSelector.obtainMultipleResult(data);
                    mGridAdapter.removeAll();
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                   mGridAdapter.addAll(mSelectList);
                    break;
            }
        }
    }

    private class GridAdapter extends BaseAdapter {
        private List<LocalMedia> mList = new ArrayList<>();


        public void addAll(List<LocalMedia> list) {
            this.mList.addAll(list);
            if(mList.size() < 8){
                this.mList.add(mList.size(), null);
            }
            notifyDataSetChanged();
        }

        public void removeAll() {
            this.mList.clear();
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int i) {
            return mList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            View view1 = getLayoutInflater().inflate(R.layout.activity_shop_dynamic_add_image_item, null);
            ImageView imageView = (ImageView) view1.findViewById(R.id.shop_dynamic_add_list_item_image);
            ImageView deleteView = (ImageView) view1.findViewById(R.id.shop_dynamic_add_list_item_image_delete);
            int displayWidth = UtilAssistants.getDisplayWidth(mContext);
            imageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,(displayWidth/4)));
            if (mList.get(i) == null) {
                imageView.setImageResource(R.drawable.icon_take_photos);
                deleteView.setVisibility(View.GONE);
            } else {
                ImageLoader.getInstance().displayImage("file://" + mList.get(i).getCompressPath(), imageView);
            }

            deleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mList.remove(i);
                    mSelectList.remove(i);
                    notifyDataSetChanged();
                }
            });

            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openImage();
                }
            });
            return view1;
        }
    }

}
