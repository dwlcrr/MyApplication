package com.example.testapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.testapplication.R;
import com.example.testapplication.base.BaseActivity;

public class IndexActivity extends BaseActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);
        View view = findView(R.id.btn_view);
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