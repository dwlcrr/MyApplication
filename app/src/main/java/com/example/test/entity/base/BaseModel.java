package com.example.test.entity.base;

import com.google.gson.Gson;

/**
 * Created by guizhen on 2017/5/15.
 */

public class BaseModel {
    public int code;//状态
    public String msg;//说明

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
