package com.example.testapplication.protocol;

import com.example.testapplication.entity.AppConfigResult;
import com.squareup.okhttp.Callback;

/**
 * Created by dongwanlin on 2016/12/27.
 * 具体的业务网络请求
 */

public class BusinessProtocol extends RequestProtocol {

    public static void getAppConfig(Callback responseCallback) {

        String path = "/appcenter/appconfig";
        getRequest(path,AppConfigResult.class,responseCallback);
    }
}
