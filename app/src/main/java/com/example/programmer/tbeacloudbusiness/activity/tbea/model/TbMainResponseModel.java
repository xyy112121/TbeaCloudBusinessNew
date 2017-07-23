package com.example.programmer.tbeacloudbusiness.activity.tbea.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * Created by DELL on 2017/7/22.
 */

public class TbMainResponseModel extends BaseResponseModel {
    public TbMainModel data;

    public class TbMainModel {
        public List<Advpicture> advpicturelist;
        public List<Message> messagelist;
        public List<Product> productlist;
    }

    public class Advpicture {
        public String id;
        public String picture;
        public String title;
    }

    public class Message {
        public String id;
        public String title;
    }

    public class Product {
        public String id;
        public String name;
        public String specification;
        public String thumbpicture;
    }

}
