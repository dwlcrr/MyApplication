package com.example.test.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.test.R;
import com.example.test.base.BaseActivity;
import com.example.test.utils.data.UserInfoManager;

/**
 * 个人资料
 */
public class MyDataActivity extends BaseActivity implements OnClickListener {

    @Override
    protected void loadXml(Bundle savedInstanceState) {
        setContentView(R.layout.my_data);
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
    public void onResume() {
        super.onResume();
        UserInfoManager.INS().refresh();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

}