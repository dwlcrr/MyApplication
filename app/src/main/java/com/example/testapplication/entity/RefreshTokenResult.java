package com.example.testapplication.entity;

import com.example.testapplication.entity.base.BaseModel;

/**
 * Created by guizhen on 2016/12/19.
 */

public class RefreshTokenResult extends BaseModel {
    public Token data;

    public class Token {
        public String token;
    }
}
