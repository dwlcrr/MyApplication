package com.example.testapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.testapplication.R;
import com.example.testapplication.base.BaseActivity;

/**
 * 测试请求类
 */
public class TestRequestActivity extends BaseActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_show);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_request1:
                startActivity(new Intent(this,SimpleRequestActivity.class));
                break;
            case R.id.btn_request2:
                startActivity(new Intent(this,RxRequestActivity.class));
                break;
        }
    }

}