package com.example.test.net.center;

import com.example.test.BuildConfig;
import com.example.test.entity.CheckUpdate;
import com.example.test.net.NetConfig;
import com.smm.lib.net.SmmNet;

import okhttp3.Callback;
import rx.Observable;

/**
 * Created by dwl
 * 17/11/16 广告
 */

public class AppCenter extends BaseRxCenter {
    private static final String appUrl = NetConfig.BASEURL + "/appcenter";

    /**
     * 检查更新
     */
    public static Observable<CheckUpdate> checkUpdate() {

        return request(
                SmmNet.ins().post(appUrl + "/check_update")
                        .addBodyParams("device_type", "android")
                        .addBodyParams("app_version", BuildConfig.VERSION_NAME)
                , CheckUpdate.class);
    }

    public static void check(Callback callback){
        SmmNet.ins().post(appUrl + "/check_update")
                .addBodyParams("device_type", "android")
                .addBodyParams("app_version", BuildConfig.VERSION_NAME)
                .execute(callback);
    }
}
