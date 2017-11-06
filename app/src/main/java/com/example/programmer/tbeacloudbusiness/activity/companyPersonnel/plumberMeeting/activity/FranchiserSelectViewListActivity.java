package com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.activity;

import android.content.Context;
import android.os.Bundle;
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
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model.PlumberMeetingViewResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CircleImageView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 显示举办单位
 */

public class FranchiserSelectViewListActivity extends BaseActivity {
    @BindView(R.id.cp_franchiser_list_number)
    TextView mNumberView;
    private ListView mListView;
    private MyAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cp_franchiser_select_list);
        ButterKnife.bind(this);
        String lists = getIntent().getStringExtra("lists");
        Gson gson = new GsonBuilder().serializeNulls().create();
        List<PlumberMeetingViewResponseModel.OrganizeCompany> organizeCompanyList = gson.fromJson(lists, new TypeToken<List<PlumberMeetingViewResponseModel.OrganizeCompany>>() {
        }.getType());
        initTopbar("举办单位");
        mNumberView.setText(organizeCompanyList.size()+"");
        mListView = (ListView) findViewById(R.id.listview);
        mListView.setPadding(30,0,0,0);
        mAdapter = new MyAdapter(mContext, R.layout.activity_person_layout2);
        mListView.setAdapter(mAdapter);
        if (organizeCompanyList != null && organizeCompanyList.size() > 0)
            mAdapter.addAll(organizeCompanyList);
    }


    public class MyAdapter extends ArrayAdapter<PlumberMeetingViewResponseModel.OrganizeCompany> {
        int resourceId;

        public MyAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);
            resourceId = resource;
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
            final PlumberMeetingViewResponseModel.OrganizeCompany obj = getItem(position);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.masterthumbpicture, holder.mHeadView);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.companytypeicon, holder.mJobtitleView);
            holder.mNameView.setText(obj.mastername);
            holder.mCompanyNameView.setText(obj.name);
            holder.mRightView.setVisibility(View.GONE);
            return convertView;
        }


        class ViewHolder {
            @BindView(R.id.person_info_head)
            CircleImageView mHeadView;
            @BindView(R.id.person_info_name)
            TextView mNameView;
            @BindView(R.id.person_info_personjobtitle)
            ImageView mJobtitleView;
            @BindView(R.id.person_info_companyname)
            TextView mCompanyNameView;
            @BindView(R.id.person_info_right)
            ImageView mRightView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

}
