package com.example.testapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.testapplication.R;
import com.example.testapplication.base.BaseActivity;

/**
 * 自定义view 展示界面
 * @author dongwanlin
 */
public class SimpleViewActivity extends BaseActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_view_show);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

}