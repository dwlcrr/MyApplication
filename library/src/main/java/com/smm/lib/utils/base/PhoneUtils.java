package com.smm.lib.utils.base;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by zhangdi on 17/8/17.
 */

public class PhoneUtils {

    private static float density = 1.0f;
    private static Display defaultDisplay;
    private static int screenWidth = 0;
    private static int screenHeight = 0;
    private static String IMEI = "";

    public PhoneUtils (Context context){
        WindowManager systemService = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metric = new DisplayMetrics();
        defaultDisplay = systemService.getDefaultDisplay();
        defaultDisplay.getMetrics(metric);
        screenWidth = metric.widthPixels;
        screenHeight = metric.heightPixels;
        density = metric.density;
        Log.e("PhoneUtil", "screenWidth:" + screenWidth + " screenHeight:"
                + screenHeight + "  density:" + density);
    }

    public static float getDensity() {
        return density;
    }

    /**
     * 获取屏幕的宽度
     *
     * @return
     */
    public int getScreenWidth() {
        return screenWidth;
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static int getScreenHeight() {
        return screenHeight;
    }

    /**
     * 获取手机的IMEI，如果不能拿到IMEI，则使用ANDROID_ID
     *
     * @param cxt
     * @return
     */
    public static String getDeviceInfo(Context cxt) {
        if (TextUtils.isEmpty(IMEI)) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) cxt
                        .getSystemService(Context.TELEPHONY_SERVICE);
                /*
				 * 唯一的设备ID： Return null if device ID is not available.
				 */
                IMEI = telephonyManager.getDeviceId();
                if (IMEI == null) {
                    IMEI = Settings.Secure.getString(cxt.getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                }
            } catch (Exception e) {
                IMEI = "";
            }
        }
        return IMEI;

    }

    /**
     * 返回设备的名字（如MI3）
     *
     * @return
     */
    public static String getModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 返回屏幕高度的比例（0---1.0）
     *
     * @param percent
     * @return
     */
    public static int percentOfScreenHeight(float percent) {
        if ((percent <= 0) || (percent > 1.0f)) {
            percent = 1.0f;
        }
        return (int) (getScreenHeight() * percent);
    }

    /**
     * 返回屏幕宽度的比例（0---1.0）
     *
     * @param percent
     * @return
     */
    public int percentOfScreenWidth(float percent) {
        if ((percent <= 0) || (percent > 1.0f)) {
            percent = 1.0f;
        }
        return (int) (getScreenWidth() * percent);
    }

    /**
     * 手机号 加 *号
     * @param phone
     * @return
     */
    public static String phoneAddStar(String phone) {
        if(phone.length()<11){
            return phone;
        }
        StringBuffer phoneBuffer = new StringBuffer();
        for (int i = 0; i < phone.length(); i++) {
            if(i>=3&&i<=6){
                phoneBuffer.append("*");
            }else {
                phoneBuffer.append(phone.charAt(i));
            }
        }
        return phoneBuffer.toString();
    }
    //  关闭软键盘
    public static void hideInputWindow(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE); //得到InputMethodManager的实例
        if (imm.isActive()) {//如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);//关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }
    }
}
