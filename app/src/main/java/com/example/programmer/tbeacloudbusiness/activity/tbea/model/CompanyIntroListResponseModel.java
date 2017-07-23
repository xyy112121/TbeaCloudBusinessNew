package com.example.programmer.tbeacloudbusiness.activity.tbea.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2017/7/22.
 */

public class CompanyIntroListResponseModel extends BaseResponseModel {
    public CompanyIntroList data;

    public class CompanyIntroList {
        public List<CompanyIntro> newslist;

        public CompanyIntroList(){
            newslist = new ArrayList<>();
        }

    }

    public class CompanyIntro {
        public String author;
        public String id;
        public String thumb;
        public String time;
        public String title;
    }
}
