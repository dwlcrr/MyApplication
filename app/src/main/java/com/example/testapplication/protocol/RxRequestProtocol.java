package com.example.testapplication.protocol;

import com.example.testapplication.net.XgoHttpClient;
import java.util.Map;
import rx.Observable;

/**
 * Created by dongwanlin on 2016/12/27.
 */

public class RxRequestProtocol extends RxBaseProtocol {

    private static final String BASE_URL = "http://newtest.smm.cn";

    /**
     * Get请求
     */
    public <T> Observable getRequest(String path,final Class<T> clazz) {
        return createObservable(BASE_URL + path, XgoHttpClient.METHOD_GET, null,clazz);
    }

    /**
     * Post请求
     */
    public <T> Observable postRequest(String path, Map<String, Object> params,final Class<T> clazz) {
        return createObservable(BASE_URL + path, XgoHttpClient.METHOD_POST, params,clazz);
    }

    /**
     * Put请求
     */
    public <T> Observable putRequest(String path, Map<String, Object> params,final Class<T> clazz) {
        return createObservable(BASE_URL + path, XgoHttpClient.METHOD_PUT, params,clazz);
    }

    /**
     * Delete请求
     */
    public <T> Observable deleteRequest(final Class<T> clazz) {
        String path = "1";
        return createObservable(BASE_URL + path, XgoHttpClient.METHOD_DELETE, null,clazz);
    }
}
