package com.example.testapplication.entity;


import com.example.testapplication.entity.base.BaseModel;

/**
 * Created by guizhen on 2018/2/28.
 */

public class CheckUpdate extends BaseModel {

    /**
     * data : {"version":"4.0.1","update":true,"force":false,"title":"掌上有色","desc":"1.资讯快递全面升级，建议更新;\n2.\u201c有色问答\u201d迁移到掌上有色-资讯，简称\u201c问答\u201d;\n3.\u201c我的\u201d优化，可直接查看问答收益、提问、回答等内容;\n4.问答消息合并到消息中心","link":"http://imgqn.smm.cn/androidapk/4.0.1/sAgyr20180211164411.apk"}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * version : 4.0.1
         * update : true
         * force : false
         * title : 掌上有色
         * desc : 1.资讯快递全面升级，建议更新;
         2.“有色问答”迁移到掌上有色-资讯，简称“问答”;
         3.“我的”优化，可直接查看问答收益、提问、回答等内容;
         4.问答消息合并到消息中心
         * link : http://imgqn.smm.cn/androidapk/4.0.1/sAgyr20180211164411.apk
         */

        public String version;
        public boolean update;
        public boolean force;
        public String title;
        public String desc;
        public String link;
    }
}
