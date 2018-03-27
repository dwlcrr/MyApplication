package com.example.test.utils.base;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.test.base.MyApplication;
import com.example.test.entity.user.LoginStatus;
import com.example.test.utils.data.UserInfoManager;
import com.example.test.utils.rx.RxBus;
import com.smm.lib.utils.base.StrUtil;

/**
 * SharedPreferences 工具类ß
 */
public class SpfsUtil {

    private static SharedPreferences sharedPreferences;
    public static final String PREFERENCES_NAME = "test_prefs";
    public static final String TOKEN = "token";
    public static final String VIP_STATUS = "vip_status";
    public static final String GUIDE = "guide"; //引导页

    public static final String USERNAME = "username";
    public static final String PWD = "pwd";
    public static final String NICKNAME = "nickname";
    public static String USERTOKEN = "";


    public static void init() {
        sharedPreferences = MyApplication.ins().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static void save(String title, String content) {
        sharedPreferences.edit().putString(title, content).commit();
        if (title.equals("token")) {
            USERTOKEN = content;
        }
    }

    public static void save(String title, int content) {
        sharedPreferences.edit().putInt(title, content).commit();
    }

    public static String get(String title) {
        return sharedPreferences.getString(title, "");
    }

    public static String get(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public static boolean getBoolean(String title) {
        return sharedPreferences.getBoolean(title, false);
    }

    public static boolean getBoolean(String title, boolean defaultValue) {
        return sharedPreferences.getBoolean(title, defaultValue);
    }

    public static void saveBoolean(String title, boolean b) {
        sharedPreferences.edit().putBoolean(title, b).commit();
    }

    public static int getInt(String title) {
        return sharedPreferences.getInt(title, 0);
    }

    public static int getInt(String title, int defaultNum) {
        return sharedPreferences.getInt(title, defaultNum);
    }

    public static long getLong(String key) {
        return getLong(key, 0);
    }

    public static void saveLong(String key, long value) {
        sharedPreferences.edit().putLong(key, value).commit();
    }

    public static long getLong(String key, long defaultLong) {
        return sharedPreferences.getLong(key, defaultLong);
    }

    public static void saveInt(String key, int value) {
        sharedPreferences.edit().putInt(key, value).commit();
    }

    //清除用户个人信息
    public static void clearUserInfoSpf() {
//        sharedPreferences.edit().clear().commit();
        sharedPreferences.edit().remove(TOKEN).remove(USERNAME).remove(PWD).remove(NICKNAME).remove(VIP_STATUS).remove("zixunvip").commit();
//        SpfsUtil.save(SpfsUtil.GUIDE, 1);
//        sharedPreferences.edit().putBoolean(ONCE_QUOTATION, true).commit();
//        sharedPreferences.edit().putBoolean(ONCE_QUOTATION_DETAILS, true).commit();
//        sharedPreferences.edit().putBoolean(ONCE_MALL, true).commit();
        USERTOKEN = "";
        UserInfoManager.INS().clear();
        RxBus.getDefault().post(new LoginStatus(2));
    }

    public static boolean isLogin() {
        return StrUtil.isNotEmpty(USERTOKEN);
    }

    public static SharedPreferences getSP() {
        if (sharedPreferences == null) init();
        return sharedPreferences;

    }
}
