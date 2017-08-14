package com.example.testapplication.protocol;

import com.example.testapplication.net.XgoHttpClient;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;

import java.util.Map;

/**
 * Created by dongwanlin on 2016/12/27.
 */

public abstract class BaseProtocol {

    /**
     * get 异步模式请求
     *
     * @param url
     * @param method
     * @param params
     */
    protected static <T> void getRequest(final String url, final String method,
                                         final Map<String, Object> params, final Class<T> clazz, Callback responseCallback) {
        Request request = XgoHttpClient.getClient().getRequest(url, method, params);
        XgoHttpClient.getClient().enqueue(request, responseCallback);
    }

}
