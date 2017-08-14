package com.example.testapplication.base;

import android.app.Activity;
import android.os.Bundle;

import com.example.testapplication.protocol.RxBusinessProtocol;
import com.google.gson.Gson;

public class BaseActivity extends Activity {

    public BaseActivity baseActivity;
    protected RxBusinessProtocol rxBusinessProtocol;
    protected Gson mGson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        baseActivity = this;
        rxBusinessProtocol = new RxBusinessProtocol();
        mGson = new Gson();
    }


}
