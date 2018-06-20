package com.example.test.entity;


import com.example.test.entity.base.BaseEntity;
import java.util.List;

/**
 * Created by dwl on 17/7/11.
 * 行情搜索
 */

public class Quo_search_Bean extends BaseEntity {

    /**
     * message :
     * data : {"value":[{"id":"1","name":"123","type":"spot"}]}
     */

    public String message;
    public DataBean data;

    public static class DataBean {
        public List<ValueBean> value;

        public static class ValueBean {
            /**
             * id : 1
             * name : 123
             * type : spot
             */

            public String id;
            public String name;
            public String type;
            public String spot_type;
        }
    }
}
