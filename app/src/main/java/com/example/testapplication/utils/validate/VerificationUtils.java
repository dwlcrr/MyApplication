package com.example.testapplication.utils.validate;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.testapplication.utils.base.BaseUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用于系统校验各类数据的合法性以及正确性
 */
public class VerificationUtils {

    public static boolean isNotNull(String s) {
        if (s != null && !s.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNotNull(String... s) {
        for (String value : s) {
            if (!isNotNull(value)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmail(String strEmail) {
        if (TextUtils.isEmpty(strEmail)) {
            return false;
        }
        String strPattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern p = Pattern.compile(strPattern);
        Matcher matcher = p.matcher(strEmail);
        return matcher.matches();
    }

    public static boolean isPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        }
        String strPattern = "^\\d{11}$";
        Pattern pattern = Pattern.compile(strPattern);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }


    public static boolean isCaptcha(String code) {

        boolean flag = true;

        if (TextUtils.isEmpty(code)) {
            return false;
        }
        // 验证码不为6位
        if (code.length() != 6) {
            flag = false;
        } else {
            flag = TextUtils.isDigitsOnly(code);
        }
        return flag;
    }

    /**
     * 判断 s,是否以 数字开头
     *
     * @param content
     * @return
     */
    public static boolean indexOfNum(String content) {
        Pattern pattern = Pattern.compile("^(\\d+)(.*)");
        Matcher matcher = pattern.matcher(content);
        return matcher.matches();
    }

    public static boolean isPassword(String pwd) {
        if (TextUtils.isEmpty(pwd)) {
            return false;
        }
        // 密码中不能包含汉字
        int length = pwd.length();
        int byteLength = pwd.getBytes().length;
        if (length != byteLength) {
            return false;
        }

        // 密码小于6位 或者 大于 20位
        if (pwd.length() < 6 || pwd.length() > 20) {
            return false;
        }
        return true;
    }

    public static boolean isNumber(String num) {
        if (TextUtils.isEmpty(num))
            return false;
        return TextUtils.isDigitsOnly(num);
    }

    /**
     * 判断网络是否可用
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 替换字符串
     *
     * @param from String 原始字符串
     */
    public static String replace(String from) {
        if (from == null) {
            return null;
        }
        StringBuffer bf = new StringBuffer("");
        int index = -1;
        for (int i = 0; i < from.length(); i++) {
            if (i < 2 || i > from.length() - 3) {
                bf.append(from.charAt(i));
            } else {
                bf.append("*");
            }
        }
        return bf.toString();
    }

    //判断 字符串是否为空
    public static Boolean isNull(Activity self, String str, String name) {
        if ("".equals(str) || str == null || "null".equals(str)) {
            BaseUtils.showToast(self, name + "不能空");
            return true;
        }
        return false;
    }

    //判断 整数字段是否为空
    public static Boolean isZero(Activity self, int tempInt, String name) {
        if ("".equals(tempInt) || tempInt == 0 || "null".equals(tempInt)) {
            BaseUtils.showToast(self, name + "不能为0");
            return true;
        }
        return false;
    }

    //判断 数字的合法
    public static Boolean isValNum(Activity self, String temp, String name) {
        if ("".equals(temp) || temp == null || "null".equals(temp)) {
            BaseUtils.showToast(self, name + "不能为空");
            return false;
        } else {
            if (temp.length() > 0 && "0".equals(temp.substring(0, 1))) { //如果 数字以0开头
                BaseUtils.showToast(self, name + "不能以0开始");
                return false;
            }
        }
        return true;
    }

    //判断 密码格式
    public static Boolean isPass(Activity self, String pwd) {
        if ("".equals(pwd)
                || (!"".equals(pwd.trim()) && pwd.length() < 6)
                || (!"".equals(pwd.trim()) && pwd.length() > 20)
                || !pwd.matches("[A-Za-z0-9]+")) {
            BaseUtils.showToast(self, "密码格式有误!");
            return false;
        }
        return true;
    }

    /**
     * 获取短信发送的校验码：
     */
    public static String getCodeKey(String mobile, String timespan) {
        return MD5(MD5(mobile) + timespan.substring(5, 10));
    }

    /**
     * 将字符串转成MD5值
     *
     * @param string
     * @return
     */
    public static String MD5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }

    /**
     * 检查是否存在SDCard
     *
     * @return
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /***
     * 获取存储卡路径
     *
     * @return
     */
    public static File getCardPath() {
        return Environment.getExternalStorageDirectory();
    }

    /**
     * 判断邮箱是输入规范
     */
    public static boolean checkEmail(String email) {
        Pattern p = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)" + "|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 用户名是否输入规范
     */
    public static boolean checkAccount(String account) {
        Pattern p = Pattern.compile("([\u4e00-\u9fa5]{2,7})|([A-Za-z0-9 ]{4,14})");
        Matcher m = p.matcher(account);
        return m.matches();
    }

    /**
     * EditText输入监听器
     */
    public static View.OnFocusChangeListener onFocusAutoClearHintListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            EditText textView = (EditText) v;
            String hint;
            if (hasFocus) {
                hint = textView.getHint().toString();
                textView.setTag(hint);
                textView.setHint("");
            } else {
                hint = textView.getTag().toString();
                textView.setHint(hint);
            }
        }
    };
}
