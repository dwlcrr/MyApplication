package com.example.test.entity;

import java.util.List;

/**
 * Created by xiangcheng on 17/9/26.
 */

public class Product {
    public List<Classify> classify;

    static public class Classify {
        public String title;
        public List<Des> des;

        public Classify(String title, List<Des> des) {
            this.title = title;
            this.des = des;
        }

        public static class Des {
            public boolean isSelect;
            public String des;

            public Des(String des) {
                this.des = des;
            }
        }

    }
}
