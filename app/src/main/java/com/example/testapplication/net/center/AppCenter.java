package com.example.testapplication.net.center;


import com.example.testapplication.entity.CheckUpdate;
import com.example.testapplication.net.NetConfig;
import com.loveplusplus.update.BuildConfig;
import com.loveplusplus.update.net.SmmNet;
import rx.Observable;

/**
 * Created by dwl
 * 17/11/16 广告
 */

public class AppCenter extends BaseCenter {
    private static final String appUrl = NetConfig.BASEURL + "/appcenter";

    /**
     * 检查更新
     */
    public static Observable<CheckUpdate> checkUpdate() {
        return request(
                SmmNet.ins().post(appUrl + "/check_update")
                        .addBodyParams("device_type", "android-en")
                        .addBodyParams("app_version", BuildConfig.VERSION_NAME)
                , CheckUpdate.class);
    }
}
