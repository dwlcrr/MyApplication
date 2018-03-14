package com.example.test.entity;

import java.io.Serializable;

/**
 * Created by huliang on 2016/2/29.
 */
public class BaseEntity  implements Serializable {
    private static final long serialVersionUID = 1000L;
    public int code;//状态
    public String msg;//说明
    @Override
    public String toString() {
        return super.toString();
    }
}
