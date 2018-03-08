package com.example.testapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.testapplication.R;
import com.example.testapplication.base.BaseActivity;
import com.example.testapplication.entity.AppConfigResult;
import com.example.testapplication.net.callback.JsonCallback;
import com.example.testapplication.protocol.BusinessProtocol;
import com.example.testapplication.protocol.ResponseCallback;
import com.example.testapplication.utils.Constant;
import com.example.testapplication.utils.XgoLog;
import com.lzy.okgo.OkGo;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;

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

        OkGo.<JSONObject>get(Constant.UPDATE_URL)
                .execute(new JsonCallback<JSONObject>() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<JSONObject> response) {
                        System.out.println(response.body());
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<JSONObject> response) {
                        response.getException().printStackTrace();
                    }
                });

        BusinessProtocol.getAppConfig(new  ResponseCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                AppConfigResult appConfigResult = mGson.fromJson(response.body().string(),AppConfigResult.class);
                XgoLog.v("ssss::::" + appConfigResult.data.mall_close_time);
            }

        });
    }
}