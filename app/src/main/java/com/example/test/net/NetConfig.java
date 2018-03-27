package com.example.test.net;


import com.example.test.BuildConfig;

/**
 * Created by guizhen on 2017/5/15.
 */

public class NetConfig {

    public static String baseUrl;
    private static final String TEST_SMM_URL = "https://testplatform.smm.cn";
    private static final String TEST_METAL_URL = "https://testplatform.metal.com";
    private static final String TEST_LIVE_WS = "wss://testquotelivewsss.smm.cn/metalquotelivecenter/quotelivecenter/ws";

    private static final String SMM_URL = "http://platform.smm.cn";
    private static final String METAL_URL = "https://platform.metal.com";
    private static final String LIVE_WS = "wss://quotelivewss.metal.com/quotelivecenter/ws";

    public static boolean online = BuildConfig.online;

    public static String BASEURL = "";
    public static String ENURL = "";
    public static String USERURL = "";
    public static String LIVEURL = "";
    public static String LIVEWS = "";

    static {
        baseUrl = online ? "http://platform.smm.cn":"http://testplatform.smm.cn";
        BASEURL = online ? SMM_URL : TEST_SMM_URL;
        ENURL = online ? METAL_URL : TEST_SMM_URL;
        USERURL = online ? METAL_URL : TEST_METAL_URL;
        LIVEURL = online ? METAL_URL : TEST_SMM_URL + "/metalquotelivecenter";
        LIVEWS = online ? LIVE_WS : TEST_LIVE_WS;
    }

    public static final String SMM_DEVICE = "smm-device";
    public static final String SMM_DEVICE_VALUE = "android";
    public static final String SMM_VERSION = "smm-version";
    public static final String SMM_DEVICE_INFO = "smm-device-info";
    public static final String SMM_SOURCE = "smm-source";
    public static final String SMM_SOURCE_VALUE = "english";
    public static final String SMM_DEVICE_INFO_VALUE = android.os.Build.MODEL == null ? "" : android.os.Build.MODEL;
    public static final String SMM_VERSION_VALUE = BuildConfig.VERSION_NAME;

}
