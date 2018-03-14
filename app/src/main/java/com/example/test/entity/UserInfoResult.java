package com.example.test.entity;


import com.example.test.entity.base.BaseModel;

import java.util.List;

/**
 * Created by guizhen on 2017/5/22
 * 个人信息 返回信息
 */
public class UserInfoResult extends BaseModel {

    /**
     * data : {"avatar":"https://ohe31mpy9.qnssl.com/test/thirdparty/avatar/KQpGZ20170518154345.jpeg","email":"zhengyaxin@smm.cn","user_id":720508,"country_id":9,"user_type":3,"start_time":1484150400,"end_time":1578844799,"country":"Antarctica","company_name":"亚新的漂亮小店子","company_type":"Consultancy","name":"","telephone":"1·1234343","first_name":"亚新","last_name":"丫丫AYahhh","job_title":"特殊听","metals_list":[{"id":"20","name":"Copper"},{"id":"21","name":"Aluminum"},{"id":"25","name":"Nickel"},{"id":"23","name":"Zinc"}],"product_list":[{"id":"1","name":"Prices"},{"id":"2","name":"Publications"},{"id":"3","name":"Database"}]}
     */

    public UserInfo data;

    public static class UserInfo {
        /**
         * avatar : https://ohe31mpy9.qnssl.com/test/thirdparty/avatar/KQpGZ20170518154345.jpeg
         * email : zhengyaxin@smm.cn
         * user_id : 720508
         * country_id : 9
         * user_type : 3
         * start_time : 1484150400
         * end_time : 1578844799
         * country : Antarctica
         * company_name : 亚新的漂亮小店子
         * company_type : Consultancy
         * name :
         * telephone : 1·1234343
         * first_name : 亚新
         * last_name : 丫丫AYahhh
         * job_title : 特殊听
         * metals_list : [{"id":"20","name":"Copper"},{"id":"21","name":"Aluminum"},{"id":"25","name":"Nickel"},{"id":"23","name":"Zinc"}]
         * product_list : [{"id":"1","name":"Prices"},{"id":"2","name":"Publications"},{"id":"3","name":"Database"}]
         */

        public String avatar;
        public String email;
        public int user_id;
        public int country_id;
        public int user_type;
        public long start_time;
        public long end_time;
        public String country;
        public String company_name;
        public String company_type;
        public String name;
        public String telephone;
        public String first_name;
        public String last_name;
        public String job_title;
        public boolean facebook_binded; //是否绑定facebook
        public boolean google_binded;   //是否绑定 google
        public List<IdNameBean> metals_list;
        public List<IdNameBean> product_list;

        public int getUserType() {
            return end_time > System.currentTimeMillis() / 1000 ? user_type : 0;
        }
    }

    public static boolean isValid(UserInfoResult userInfoResult) {
        return userInfoResult != null && userInfoResult.code == 0&& userInfoResult.data != null;
    }
}
