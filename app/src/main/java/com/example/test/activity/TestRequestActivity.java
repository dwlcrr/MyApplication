package com.example.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.test.R;
import com.example.test.activity.login.LoginActivity;
import com.example.test.base.BaseActivity;

/**
 * 测试请求类
 */
public class TestRequestActivity extends BaseActivity implements OnClickListener {

    @Override
    protected void loadXml(Bundle savedInstanceState) {
        setContentView(R.layout.request_show);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_request1:
                startActivity(new Intent(this,SimpleRequestActivity.class));
                break;
            case R.id.btn_request2:
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
    }

}