package com.example.test.utils.base;

import com.example.test.net.NetConfig;

/**
 * 常量类
 */
public class FinalConstant {

    public final static int PAGE_SIZE = 10;
    public final static int[] MESSAGE_TYPE = {1, 2, 3, 4};
    public static final String DOWNLOAD_URL = "http://a.app.qq.com/o/simple.jsp?pkgname=com.smm";

    // 提示语
    public static final String NO_RESPONSE = "暂无信息";
    public static final String NO_SEARCH = "暂无相关搜索结果";

    public static final String PIC_VERCODE_URL =
            NetConfig.online ? "https://captcha.smm.cn/showimage?key=" : "https://testcaptcha.smm.cn/showimage?key=";

    //客服热线
    public static String BOSS_SHARE_URL = NetConfig.online ? "https://boss.smm.cn/posts/" : "https://testboss.smm.cn/posts/";
    /**
     * 观察者消息跳转
     */
    public static String MESS_BROADCAST = "mess_broadcast";

}
