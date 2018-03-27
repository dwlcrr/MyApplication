package com.example.test.utils.data;

import android.widget.Toast;
import com.example.test.base.MyApplication;
import com.example.test.base.MySp;
import com.example.test.entity.user.UserInfoResult;
import com.example.test.net.api.UserApi;
import com.smm.lib.utils.base.StrUtil;
import rx.Observable;

/**
 * Created by guizhen on 2016/11/2.
 */

public class UserInfoManager extends DataManager<UserInfoResult> {

    private static UserInfoManager ins;

    public static UserInfoManager INS() {
        if (ins == null) ins = new UserInfoManager();
        return ins;
    }

    public void clear() {
        set(null);
    }

    @Override
    public void set(UserInfoResult data) {
        if (data != null && (data.data == null || data.data.user_id <= 0)) return;
        super.set(data);
    }

    @Override
    protected Observable getData() {
        return UserApi.getUserInfo()
                .map(userInfoResult -> {
                    if (userInfoResult != null) {
                        switch (userInfoResult.code) {
                            case 0:
                                return userInfoResult;
                            case 10003:
                                MySp.ins().logout();
                                Toast.makeText(MyApplication.getContext(), userInfoResult.msg, Toast.LENGTH_SHORT).show();
                                break;
                            case 10021:
                                refreshToken();
                                break;
                            case 10028:
                                MySp.ins().logout();
                                Toast.makeText(MyApplication.getContext(), userInfoResult.msg, Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                    return null;
                })
                .filter(userInfoResult -> userInfoResult != null && userInfoResult.code == 0);
    }

    public void refreshToken() {
        UserApi.refreshToken()
                .subscribe(
                        refreshTokenResult -> {
                            if (refreshTokenResult != null
                                    && refreshTokenResult.code == 0
                                    && refreshTokenResult.data != null
                                    && StrUtil.isNotEmpty(refreshTokenResult.data.token)) {
                                MySp.ins().putString(MySp.KEY_TOKEN, refreshTokenResult.data.token);
                                refresh();
                            }
                        },
                        error -> {
                        });
    }

    public Integer getUserId() {
        UserInfoResult userInfoResult = get();
        if (userInfoResult != null) {
            return userInfoResult.data.user_id;
        }
        return -1;
    }

    public Integer getUserLevel() {
        UserInfoResult userInfoResult = get();
        return userInfoResult == null ? 0 : userInfoResult.data.getUserType();
    }

}
