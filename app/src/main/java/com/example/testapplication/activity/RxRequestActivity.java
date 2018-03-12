package com.example.testapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.example.testapplication.R;
import com.example.testapplication.base.BaseActivity;

/**
 * Rx异步请求类
 */
public class RxRequestActivity extends BaseActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_show);
        testOk();
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