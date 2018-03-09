package com.example.testapplication.utils.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 常用工具类
 *
 * @author dwl
 */
public class BaseUtils {
    /**
     * 自定义Toast
     */
    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 字符串为null，或长度为0
     *
     * @param cs
     * @return
     */
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * 批量设置view的点击事件
     *
     * @param onClickListener
     * @param views
     */
    public static void setViewsClick(View.OnClickListener onClickListener, View... views) {
        for (View view : views) {
            view.setOnClickListener(onClickListener);
        }
    }

    /**
     * 时间转换
     *
     * @return
     */
    public static String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm:ss", Locale.CHINA).format(new Date());
    }

    /**
     * 人性化的时间显示转换
     *
     * @return
     */
    public static String showTime(long time) {
        long t = System.currentTimeMillis() / 1000;
        int difference = (int) (t - time);
        if (difference < 60) {
            return difference + "秒前";
        } else if (difference < 60 * 60) {
            return (difference / 60) + "分钟前";
        } else if (difference < 60 * 60 * 24) {
            return (difference / 3600) + "小时前";
        } else if (difference < 60 * 60 * 24 * 365) {
            return new SimpleDateFormat("MM-dd HH:mm:ss", Locale.CHINA).format(new Date(time * 1000));
        } else {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date(time * 1000));
        }
    }

    //activity 跳转
    public static void goToActivity(Context context, Activity activity) {
        Intent intent = new Intent(context, activity.getClass());
        context.startActivity(intent);
    }
}
