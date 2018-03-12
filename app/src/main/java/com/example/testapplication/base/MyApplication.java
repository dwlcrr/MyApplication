package com.example.testapplication.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.example.testapplication.net.NetConfig;
import com.example.testapplication.utils.other.UUIDUtils;
import com.smm.lib.BuildConfig;
import com.smm.lib.net.SmmNet;
import com.smm.lib.utils.base.DisplayUtils;
import com.smm.lib.utils.base.Logger;
import com.smm.lib.utils.base.StrUtil;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wyouflf on 15/10/28.
 */
public class MyApplication extends Application {

    private static MyApplication ins;
    public static int TOUCH_MIN = 5;
    public static int DEFAULT_DEGREE_FONT_SIZE = 0;

    public static MyApplication ins() {
        return ins;
    }

    public static Context getContext() {
        return ins().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ins = this;

        MyApplication.DEFAULT_DEGREE_FONT_SIZE = DisplayUtils.dip2px(this, 10);
        MyApplication.TOUCH_MIN = (int) (DisplayUtils.getScreenHeight(this) * 0.04);
        MyApplication.TOUCH_MIN = MyApplication.TOUCH_MIN > 5 ? MyApplication.TOUCH_MIN : 5;
        //添加网络请求公共 headers
        SmmNet.ins().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder requestBuilder = chain.request().newBuilder()
                        .addHeader(NetConfig.SMM_DEVICE, NetConfig.SMM_DEVICE_VALUE)
                        .addHeader(NetConfig.SMM_VERSION, NetConfig.SMM_VERSION_VALUE)
                        .addHeader(NetConfig.SMM_DEVICE_INFO, NetConfig.SMM_DEVICE_INFO_VALUE)
                        .addHeader(NetConfig.SMM_SOURCE, NetConfig.SMM_SOURCE_VALUE);
                if (StrUtil.isNotEmpty(MySp.token)) {
                    requestBuilder.addHeader("smm-token", MySp.token);
                }
                return chain.proceed(requestBuilder.build());
            }
        });
        //初始化 token
        MySp.token = MySp.ins().getString(MySp.KEY_TOKEN, "");
        //初始化umeng share
        PlatformConfig.setTwitter("cigXbBPTp6lQ4sQjGdm6IT8Gq", "Yr0Y0JO07FDS72Ldz4ESPBKpgE1bHdwv1c7FwXUbUpqPDHWRLk");
        Config.MORE_TITLE = "More";
        UMShareAPI.get(this);
        //bugly 初始化
        CrashReport.initCrashReport(getApplicationContext(), "d3c9965132", BuildConfig.DEBUG);
        CrashReport.setAppChannel(getApplicationContext(), BuildConfig.FLAVOR);
        //初始化 uuid
        String uuid = MySp.ins().getString(MySp.KEY_UUID, "");
        if (StrUtil.isEmpty(uuid)) {
            String newUUID = UUIDUtils.generateUUID(this).toString();
            if (StrUtil.isNotEmpty(newUUID)) {
                MySp.ins().putString(MySp.KEY_UUID, uuid);
                uuid = newUUID;
            }
        }
        MySp.uuid = uuid;
        Logger.info("smm_uuid", uuid);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
