package com.example.test.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import com.example.test.net.NetConfig;
import com.example.test.utils.other.UUIDUtils;
import com.meituan.android.walle.WalleChannelReader;
import com.smm.lib.BuildConfig;
import com.smm.lib.net.SmmNet;
import com.smm.lib.okgo.OkGo;
import com.smm.lib.okgo.model.HttpHeaders;
import com.smm.lib.utils.base.DisplayUtils;
import com.smm.lib.utils.base.Logger;
import com.smm.lib.utils.base.StrUtil;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dwl on 15/10/28.
 */
public class MyApplication extends Application {

    public static final String UMENG_APPKEY = "573140f967e58ec3f7003821";
    private static MyApplication ins;
    public static int TOUCH_MIN = 5;
    public static int DEFAULT_DEGREE_FONT_SIZE = 0;
    //mainactivity 是否正在运行
    public boolean isMainRunning;

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
        initOkGo();
        MyApplication.DEFAULT_DEGREE_FONT_SIZE = DisplayUtils.dip2px(this, 10);
        MyApplication.TOUCH_MIN = (int) (DisplayUtils.getScreenHeight(this) * 0.04);
        MyApplication.TOUCH_MIN = MyApplication.TOUCH_MIN > 5 ? MyApplication.TOUCH_MIN : 5;

        //初始化 token
        MySp.token = MySp.ins().getString(MySp.KEY_TOKEN, "");
        //获取 channel
        String channel = WalleChannelReader.getChannel(this, "dev");
        Logger.debug("渠道channel===" + channel);
        //Umeng配置
        //微信    wx12342956d1cab4f9,a5ae111de7d9ea137e88a5e02c07c94d
        UMShareAPI.init(this, UMENG_APPKEY);
        PlatformConfig.setWeixin("wxb2f9aa35fa849254", "3639d0acb870a59390ed8a77aca475c8");
        PlatformConfig.setQQZone("101027766", "ddd75dfc3384ec7bd5eb88a67cab3f6a");
        PlatformConfig.setSinaWeibo("2884735682", "19396b9b99cd3c39e82d634640e1dd2b", "http://sns.whalecloud.com");
        UMShareAPI.get(this);
        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(this, UMENG_APPKEY, channel));
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

    private void initOkGo() {

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

        HttpHeaders headers = new HttpHeaders();
        headers.put(NetConfig.SMM_DEVICE, NetConfig.SMM_DEVICE_VALUE); //header不支持中文，不允许有特殊字符
        headers.put(NetConfig.SMM_VERSION, NetConfig.SMM_VERSION_VALUE);
        headers.put(NetConfig.SMM_DEVICE_INFO, NetConfig.SMM_DEVICE_INFO_VALUE);
        headers.put(NetConfig.SMM_SOURCE, NetConfig.SMM_SOURCE_VALUE);
        if (StrUtil.isNotEmpty(MySp.token)) {
            headers.put("smm-token", MySp.token);
        }
        OkGo.getInstance().init(this)
                .addCommonHeaders(headers);
    }

}
