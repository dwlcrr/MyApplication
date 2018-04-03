package com.example.test.entity;


import com.example.test.entity.base.BaseEntity;
import java.util.List;

/**
 * Created by dwl on 2016/4/2
 * 个人信息 返回信息
 */
public class UnReadSizeResult extends BaseEntity {
    public List<UnReadSize> data;//返回的数据

    public static class UnReadSize {
        public int msg_type;
        public int unread_amount;

        public UnReadSize(int msg_type, int unread_amount) {
            this.msg_type = msg_type;
            this.unread_amount = unread_amount;
        }
    }
}
