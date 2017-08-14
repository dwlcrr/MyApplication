package com.example.testapplication.protocol;

import com.example.testapplication.net.XgoHttpClient;
import com.squareup.okhttp.Callback;
import java.util.Map;

/**
 * Created by dongwanlin on 2016/12/27.
 */

public class RequestProtocol extends BaseProtocol {

    private static final String BASE_URL = "http://newtest.smm.cn";

    /**
     * Get请求
     */
    public static <T> void getRequest(String path, final Class<T> clazz,Callback responseCallback) {
        getRequest(BASE_URL + path, XgoHttpClient.METHOD_GET,null,clazz,responseCallback);
    }

    /**
     * Post请求
     */
    public <T> void postRequest(String path, Map<String, Object> params, final Class<T> clazz,Callback responseCallback) {
        getRequest(BASE_URL + path, XgoHttpClient.METHOD_POST, params, clazz,responseCallback);
    }

}
