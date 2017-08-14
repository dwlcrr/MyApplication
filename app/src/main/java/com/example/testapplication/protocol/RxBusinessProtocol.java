package com.example.testapplication.protocol;

import com.example.testapplication.entity.AppConfigResult;

import rx.Observable;

/**
 * Created by dongwanlin on 2016/12/27.
 * 具体的业务网络请求
 */

public class RxBusinessProtocol extends RxRequestProtocol {

    public Observable<AppConfigResult> getAppConfig() {

        String path = "/appcenter/appconfig";
        return getRequest(path, AppConfigResult.class);
    }
}
