package com.example.testapplication.net.center;

import com.example.testapplication.entity.RefreshTokenResult;
import com.example.testapplication.entity.UserInfoResult;
import com.example.testapplication.net.NetConfig;
import com.loveplusplus.update.net.SmmNet;

import rx.Observable;

/**
 * https://github.com/smmit/design/blob/master/api_docs/user_center接口文档.md
 * Created by guizhen on 2017/5/19.
 */

public class UserCenter extends BaseCenter {
    private static final String enUserUrl = NetConfig.USERURL + "/usercenter/english/v1";
    public static final String EMAIL_CODE_TYPE_REGISTERACCOUNT = "register_account";
    public static final String EMAIL_CODE_TYPE_RESETPWD = "reset_account";

    /**
     * 获取自己的用户信息（英文站）
     *
     * @return
     */
    public static Observable<UserInfoResult> getUserInfo() {
        return request(
                SmmNet.ins().get(enUserUrl + "/get_english_user_info"),
                UserInfoResult.class);
    }

    /**
     * 刷新 token
     *
     * @return
     */
    public static Observable<RefreshTokenResult> refreshToken() {
        return request(
                SmmNet.ins().get(enUserUrl + "/refresh_token"),
                RefreshTokenResult.class);
    }
}
