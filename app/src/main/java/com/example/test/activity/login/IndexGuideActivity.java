package com.example.test.activity.login;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.example.test.R;
import com.example.test.base.BaseActivity;

/**
 * 引导页
 */
public class IndexGuideActivity extends BaseActivity implements OnClickListener {

    @Override
    protected void loadXml(Bundle savedInstanceState) {
        setContentView(R.layout.index_guide);
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
        switch (v.getId()){
            case R.id.btn_request1:

                break;
            case R.id.btn_request2:

                break;
        }
    }

}