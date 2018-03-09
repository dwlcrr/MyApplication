package com.example.testapplication.utils.base;

import android.util.Log;

import com.example.testapplication.BuildConfig;

/**
 * 日志工具类
 * Created by guizhen on 2016/9/22.
 */
public class Logger {

    private static boolean enable = BuildConfig.DEBUG;

    static String APP_TAG = "smm_en";

    public static void debug(Object context, String msg) {
        debug(getObjectTag(context), msg);
    }

    public static void debug(String msg) {
        debug(null, msg);
    }

    public static void debug(String tag, String msg) {
        if (enable) {
            Log.d(APP_TAG + (tag == null ? APP_TAG : "[" + tag + "]"), msg);
        }
    }


    public static void info(Object context, String msg) {
        info(getObjectTag(context), msg);
    }

    public static void info(String msg) {
        info(null, msg);
    }

    public static void info(String tag, String msg) {
        if (enable) {
            Log.i(APP_TAG + (tag == null ? "" : "[" + tag + "]"), msg);
        }
    }

    public static void warn(Object context, String msg) {
        if (enable) {
            Log.w(APP_TAG + (context == null ? "" : "[" + getObjectTag(context) + "]"), msg);
        }
    }

    public static void error(Object context, Throwable t, String msg) {
        error(getObjectTag(context), msg, t);
    }

    public static void error(Object context, String msg) {
        error(getObjectTag(context), msg, null);
    }

    public static void error(Object context, Throwable t) {
        error(getObjectTag(context), null, t);
    }

    public static void error(String tag, String msg, Throwable t) {
        if (enable) {
            Log.e(APP_TAG + (tag == null ? "" : "[" + tag + "]"), msg, t);
        }
    }


    public static void error(Throwable t, String msg) {
        error(null, msg, t);
    }

    public static void error(String tag, String msg) {
        error(tag, msg, null);
    }

    public static void error(String tag, Throwable t) {
        error(tag, null, t);
    }

    public static void error(String msg) {
        error(null, msg, null);
    }

    public static void error(Throwable t) {
        error(null, null, t);
    }


    private static String getObjectTag(Object o) {
        return o.getClass().getSimpleName();
    }
}
