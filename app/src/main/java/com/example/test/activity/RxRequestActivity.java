package com.example.test.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.test.R;
import com.example.test.base.BaseActivity;

/**
 * Rx异步请求类
 */
public class RxRequestActivity extends BaseActivity implements OnClickListener {

    @Override
    protected void loadXml(Bundle savedInstanceState) {
        setContentView(R.layout.request_show);
    }

    @Override
    protected void initData() {
        testOk();
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

    private void testOk() {

    }
}