package com.example.test.activity.login;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.test.R;
import com.example.test.base.BaseActivity;
import com.example.test.net.api.UserApi;
import com.example.test.utils.base.SpfsUtil;
import com.example.test.utils.data.UserInfoManager;
import com.example.test.utils.rx.RxUtils;
import com.smm.lib.utils.base.StrUtil;

import rx.Subscription;

/**
 * 登录
 */
public class LoginActivity extends BaseActivity implements OnClickListener {

    @Override
    protected void loadXml(Bundle savedInstanceState) {
        setContentView(R.layout.login);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {
        login();
    }

    private void login() {
        if ( StrUtil.isNotEmpty("token")) {
            handleLoginSuccess("token");
        }
    }

    public interface LoginSuccessListener {
        void success();

        void fail();
    }

    private void handleLoginSuccess(String token) {
        loginSuccess(token, new LoginSuccessListener() {
            @Override
            public void success() {
                SpfsUtil.save(SpfsUtil.TOKEN, token);
                //发送设备token 维持推送消息
                setResult(RESULT_OK);
                showToast("登录成功");
            }

            @Override
            public void fail() {
                showToast("请重试");
            }
        });
    }

    private void loginSuccess(String token, LoginSuccessListener listener) {
        SpfsUtil.save(SpfsUtil.TOKEN, token);
        Subscription s = UserApi
                .getUserInfo()
                .compose(RxUtils.subscribeInMain())
                .subscribe(userInfoResult -> {
                            if (userInfoResult != null && userInfoResult.code == 0 && userInfoResult.data != null) {
                                UserInfoManager.INS().set(userInfoResult);
                                listener.success();
                            } else {
                                listener.fail();
                            }
                        }
                        , error -> {
                            SpfsUtil.clearUserInfoSpf();
                            listener.fail();
                        });
        rx.add(s);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_request1:

                break;
            case R.id.btn_request2:

                break;
        }
    }

}