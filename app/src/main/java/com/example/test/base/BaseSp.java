package com.example.test.base;

import android.content.SharedPreferences;

/**
 * Created by guizhen on 2017/5/21.
 */

public abstract class BaseSp {
    protected SharedPreferences sp;

    public BaseSp putInt(String key, Integer value) {
        getSp().edit().putInt(key, value).commit();
        return this;
    }

    public BaseSp putBoolean(String key, boolean value) {
        getSp().edit().putBoolean(key, value).commit();
        return this;
    }

    public BaseSp putString(String key, String value) {
        getSp().edit().putString(key, value).commit();
        return this;
    }

    public BaseSp putFloat(String key, float value) {
        getSp().edit().putFloat(key, value).commit();
        return this;
    }

    public BaseSp putLong(String key, long value) {
        getSp().edit().putLong(key, value).commit();
        return this;
    }

    public int getInt(String key, Integer defaultValue) {
        return getSp().getInt(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return getSp().getBoolean(key, defaultValue);
    }

    public float getFloat(String key, float defaultValue) {
        return getSp().getFloat(key, defaultValue);
    }

    public long getLong(String key, long defaultValue) {
        return getSp().getLong(key, defaultValue);
    }

    public String getString(String key, String defaultValue) {
        return getSp().getString(key, defaultValue);
    }

    public SharedPreferences getSp() {
        if (sp == null) initSp();
        return sp;
    }

    protected abstract void initSp();
}
