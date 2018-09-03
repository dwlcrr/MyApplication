package com.example.test.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.test.R;
import com.example.test.base.BaseActivity;
import com.example.test.view.myview.other.Titlebar;

/**
 * 提现
 */
public class WithdrawActivity extends BaseActivity implements OnClickListener {
    /**
     * titlebar
     */
    private Titlebar titlebar;

    @Override
    protected void loadXml(Bundle savedInstanceState) {
        setContentView(R.layout.withdraw);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
        titlebar = (Titlebar) findViewById(R.id.titlebar);
        titlebar.setTitleText("提现").setRightImage(R.mipmap.download);
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

}