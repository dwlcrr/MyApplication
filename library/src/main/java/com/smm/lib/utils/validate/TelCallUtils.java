package com.smm.lib.utils.validate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

/**
 * 打电话  工具类
 */
public class TelCallUtils {

    /**
     * 拨打电话
     *
     * @param tel
     * @param context
     */
    public static void callTel(final String tel, final Context context) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void callTelWithDialog(final String tel, final Context context) {
        new AlertDialog.Builder(context)
                .setMessage("拨打电话：" + tel + "？")
                .setPositiveButton("拨打", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callTel(tel, context);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    /**
     * 简单的判断是否为11位1开头的电话号码
     *
     * @param tel
     * @return
     */
    public static boolean isPhoneNumber(String tel) {
        if (tel.length() == 11 && tel.startsWith("1")) {
            return true;
        }
        return false;
    }
}
