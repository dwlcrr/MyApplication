package com.example.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.test.R;
import com.example.test.base.BaseActivity;

public class IndexActivity extends BaseActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);
        findView(R.id.btn_request).setOnClickListener(this);
        findView(R.id.btn_view).setOnClickListener(this);
        findView(R.id.btn_update).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_request:
                break;
            case R.id.btn_view:
                break;
            case R.id.btn_update:
                startActivity(new Intent(this,UpdateActivity.class));
                break;
        }
    }

}