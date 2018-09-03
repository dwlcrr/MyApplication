package com.example.test.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.R;
import com.example.test.base.BaseActivity;
import com.example.test.entity.user.LoginStatus;
import com.example.test.net.api.AppApi;
import com.example.test.net.api.UserApi;
import com.example.test.net.callback.DialogCallback;
import com.example.test.utils.base.SpfsUtil;
import com.example.test.utils.data.UserInfoManager;
import com.example.test.utils.other.Md5Utils;
import com.example.test.utils.rx.RxUtils;
import com.smm.lib.okgo.model.Response;
import com.smm.lib.updateApp.utils.ToastUtils;
import com.smm.lib.utils.base.StrUtil;
import com.smm.lib.view.dialog.SimpleHUD;
import com.smm.lib.view.other.ClearEditText;

import rx.Subscription;

/**
 * 注册
 */
public class RegisterActivity extends BaseActivity implements OnClickListener {
    private ClearEditText et_name;
    private EditText et_password;
    private Button btn_login;
    private TextView tv_forget_pwd;

    @Override
    protected void loadXml(Bundle savedInstanceState) {
        setContentView(R.layout.register);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        et_name = (ClearEditText)findViewById(R.id.et_name);
        et_password = (EditText)findViewById(R.id.et_password);
        tv_forget_pwd = (TextView)findViewById(R.id.tv_forget_pwd);
        btn_login = (Button)findViewById(R.id.btn_login);
    }

    @Override
    protected void setListener() {
        tv_forget_pwd.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    private void login() {
        String loginName = et_name.getText().toString();
        String pwd = et_password.getText().toString();
        if (TextUtils.isEmpty(loginName)) {
            Toast.makeText(baseActivity, "请输入账号", Toast.LENGTH_LONG).show();
            et_name.findFocus();
        } else if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(baseActivity, "请输入密码", Toast.LENGTH_LONG).show();
            et_password.findFocus();
        } else {
            SimpleHUD.showLoadingMessage(this, "正在登录,请稍后", true);
            invokeLogin(loginName, pwd);
        }
    }

    private void invokeLogin(final String loginName, final String pwd) {
        AppApi.login(this, loginName, Md5Utils.md5_32(pwd),new DialogCallback<LoginStatus>(this) {
            @Override
            public void onSuccess(Response<LoginStatus> response) {
                LoginStatus status = response.body();
                if (status.code == 0) {
                    if (status.token != null && StrUtil.isNotEmpty(status.token)) {
                        handleLoginSuccess(status.token);
                    }
                } else {
                    ToastUtils.showToast(baseActivity, "");
                }
            }

            @Override
            public void onError(Response<LoginStatus> response) {

            }
        });
    }

    public interface LoginSuccessListener {
        void success();

        void fail();
    }

    private void handleLoginSuccess(String token) {
        if (StrUtil.isNotEmpty(token)) {
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
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_forget_pwd:
                startActivity(new Intent(this,UpdatePassActivity.class));
                break;
        }
    }

}