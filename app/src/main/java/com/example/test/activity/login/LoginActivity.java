package com.example.test.activity.login;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.example.test.R;
import com.example.test.base.BaseActivity;

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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_request1:

                break;
            case R.id.btn_request2:

                break;
        }
    }

}