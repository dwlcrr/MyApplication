package com.example.test.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.test.R;
import com.example.test.base.BaseActivity;
import com.example.test.view.myview.other.Titlebar;

/**
 * 自定义view 展示界面
 * @author dongwanlin
 */
public class SimpleViewActivity extends BaseActivity implements OnClickListener {

    @Override
    protected void loadXml(Bundle savedInstanceState) {
        setContentView(R.layout.simple_view_show);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        Titlebar titlebar = (Titlebar)findViewById(R.id.titlebar);
        titlebar.setTitleText("simple view").setLeftText("发布").setRightImage(R.mipmap.download)
                .setOnLeftRightClickListener(new Titlebar.OnLeftRightClickListener() {
            @Override
            public void onTitleLeftClick() {

            }

            @Override
            public void onTitleRightClick() {

            }
        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

}