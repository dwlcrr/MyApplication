package com.example.test.entity.user;

import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by guizhen on 2017/3/28.
 */

public class LoginStatus {
    //0登录成功，1取消登录，2退出登录
    public int code;
    public String token;

    public LoginStatus() {
    }

    public LoginStatus(int code) {
        this.code = code;
    }

    //传了 token，所以 code 为0
    public LoginStatus(@NotNull String token) {
        this.code = 0;
        this.token = token;
    }
}
