package com.example.test.entity;
/**
 * Created by huliang on 2016/2/29.
 * login返回信息
 */
public class AppConfigResult extends BaseEntity {

    public AppConfig data;//返回的数据

    public class AppConfig {
        public String websocketlink;
        public String phonenumber;  //商城热线
        public String mall_open_time;
        public String mall_close_time;
        public String servicephonenumber;   //客服热线
    }
}
