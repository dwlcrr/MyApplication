package com.example.testapplication.protocol;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;

/**
 * 请求回调接口
 * Created by dwl on 2016/12/28.
 * to deliver data to activity
 */
public abstract class ResponseCallback implements Callback {
    @Override
    public void onFailure(Request request, IOException e) {

    }

    @Override
    public void onResponse(Response response) throws IOException {

    }
}
