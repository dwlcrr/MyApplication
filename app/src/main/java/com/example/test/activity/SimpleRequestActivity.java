package com.example.test.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.example.test.R;
import com.example.test.base.BaseActivity;

/**
 * 一般请求类
 */
public class SimpleRequestActivity extends BaseActivity implements OnClickListener {

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