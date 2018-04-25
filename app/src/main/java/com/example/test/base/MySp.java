package com.example.test.base;

import android.content.Context;
import com.example.test.utils.data.UserInfoManager;
import com.smm.lib.utils.base.StrUtil;

/**
 * Created by guizhen on 2017/5/21.
 */

public class MySp extends BaseSp {
    private static final String SP_NAME = "smm_en_common";

    //key
    public static final String KEY_TOKEN = "smm_token";
    public static final String KEY_MONETARY_UNIT = "monetary_unit";
    public static final String KEY_UUID = "smm_uuid";
    public static final String KEY_UPDATE_DIALOG_VERSION = "update_dialog_version";

    //value 常量
    public static String token = "";
    public static String uuid = "";
    private static MySp ins;

    public static MySp ins() {
        if (ins == null) {
            ins = new MySp();
        }
        return ins;
    }

    @Override
    protected void initSp() {
        sp = MyApplication.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public BaseSp putString(String key, String value) {
        if (KEY_TOKEN.equals(key)) token = value;
        return super.putString(key, value);
    }


    public void logout() {
        sp.edit().remove(KEY_TOKEN).commit();
        token = "";
        UserInfoManager.INS().clear();
    }

    public static boolean isLogin() {
        return StrUtil.isNotEmpty(token);
    }

}
