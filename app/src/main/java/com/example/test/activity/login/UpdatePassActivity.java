package com.example.test.activity.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.test.R;
import com.example.test.base.BaseActivity;
import com.example.test.entity.user.LoginStatus;
import com.example.test.net.api.AppApi;
import com.example.test.net.callback.DialogCallback;
import com.smm.lib.okgo.model.Response;
import com.smm.lib.updateApp.utils.ToastUtils;

/**
 * 更改密码
 */
public class UpdatePassActivity extends BaseActivity implements OnClickListener {

    private EditText old_password,new_pass,confirm_pass;
    private Button btn_update;
    @Override
    protected void loadXml(Bundle savedInstanceState) {
        setContentView(R.layout.update_pass);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        old_password = (EditText)findViewById(R.id.old_password);
        new_pass = (EditText)findViewById(R.id.new_pass);
        confirm_pass = (EditText)findViewById(R.id.confirm_pass);
        btn_update = (Button)findViewById(R.id.btn_update);
    }

    @Override
    protected void setListener() {
        btn_update.setOnClickListener(this);
    }


    private void update() {
        String oldPass = old_password.getText().toString();
        String newPass = new_pass.getText().toString();
        String confirm = confirm_pass.getText().toString();
        if(TextUtils.isEmpty(oldPass)||TextUtils.isEmpty(newPass)||TextUtils.isEmpty(confirm)){
            ToastUtils.showToast(this,"密码不能为空");
            return;
        }
        if(oldPass.equals(newPass)){
            ToastUtils.showToast(this,"旧密码和新密码不能相同");
            return;
        }
        if(!confirm.equals(newPass)){
            ToastUtils.showToast(this,"确认密码有误");
            return;
        }
        updatePass(oldPass,newPass);
    }

    private void updatePass(String oldPass,String newPass) {
        AppApi.updatePass(this, oldPass, newPass,new DialogCallback<LoginStatus>(this) {
            @Override
            public void onSuccess(Response<LoginStatus> response) {
                LoginStatus status = response.body();
                if (status.code == 0) {

                } else {
                    ToastUtils.showToast(UpdatePassActivity.this, "");
                }
            }

            @Override
            public void onError(Response<LoginStatus> response) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_update:
                update();
                break;
        }
    }

}