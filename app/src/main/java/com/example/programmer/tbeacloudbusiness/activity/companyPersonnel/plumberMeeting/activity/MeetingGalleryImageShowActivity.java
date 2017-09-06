package com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.luck.picture.lib.entity.LocalMedia;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 现场图片查看（网络）
 */

public class MeetingGalleryImageShowActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.top_center)
    TextView mTopCenterView;
    @BindView(R.id.viewpager1)
    ViewPager viewPager;
    private List<LocalMedia> images;

    private int currentPage = 0;//当前展示的页码

    PagerAdapter mAdapter;

    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cp_meeting_grllery_show);
        ButterKnife.bind(this);
        //初始化图片资源
        images = (List<LocalMedia>) getIntent().getSerializableExtra("images");
        initTopbar(1 + "/" + images.size(), "删除", this);
        //-----初始化PagerAdapter------
        mAdapter = new MyAdapter();

        viewPager.setAdapter(mAdapter);
        //更改当前tip

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }


            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }


            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                mTopCenterView.setText(position + 1 + "/" + images.size());
            }
        });

    }

    @OnClick(R.id.cp_meeting_gallery_image_show_set)
    public void onViewClicked() {
        Intent intent = new Intent();
        intent.putExtra("images", (Serializable) images);
        intent.putExtra("index", currentPage+1);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        images.remove(currentPage);
        mAdapter.notifyDataSetChanged();
    }

    public class MyAdapter extends PagerAdapter {
        List<ImageView> mImageViews = new ArrayList<>();

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object o) {
//            ImageView imageView = mImageViews.get(position % mImageViews.size());
//            container.removeView(imageView);
//            ImageLoader.getInstance().displayImage("file://" + images.get(position).getPath(), imageView);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView im = new ImageView(mContext);
            im.setScaleType(ImageView.ScaleType.FIT_XY);
            ImageLoader.getInstance().displayImage("file://" + images.get(position).getPath(), im);
            container.addView(im);
            mImageViews.add(im);
            return im;

        }

    }
}
