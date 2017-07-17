package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by programmer on 2017/7/17.
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

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openImage();
            }
        });

    }

    @Override
    public void onClick(View v) {

    }


    private void openImage() {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(ShopDynamicAddActivity.this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
//                .maxSelectNum(9)// 最大图片选择数量 int
//                .minSelectNum()// 最小选择数量 int
//                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
//                .previewImage(true)// 是否可预览图片 true or false
                .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.THIRD_GEAR、Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
//                .isCamera(true)// 是否显示拍照按钮 true or false
                .selectionMedia(mSelectList)// 是否传入已选图片 List<LocalMedia> list
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    private class GridAdapter extends BaseAdapter {
        private List<LocalMedia> mList = new ArrayList<>();


        public void addAll(List<LocalMedia> list) {
            this.mList = list;
            //this.mList.add(mList.size(), null);
            notifyDataSetChanged();
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
            View view1 = getLayoutInflater().inflate(R.layout.fragment_home_top_item, null);
            ImageView imageView = (ImageView) view1.findViewById(R.id.home_top_item_image);
            TextView textView = (TextView) view1.findViewById(R.id.home_top_item_name);
            if (mList.get(i) == null) {
                imageView.setImageResource(R.drawable.icon_audio);
            } else {
                ImageLoader.getInstance().displayImage("file://" + mList.get(i).getCompressPath(), imageView);
            }

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
