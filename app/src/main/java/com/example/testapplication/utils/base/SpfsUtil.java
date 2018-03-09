package com.example.testapplication.utils.base;

import android.content.Context;
import android.content.SharedPreferences;

public class SpfsUtil {

	private static SharedPreferences sharedPreferences;
	public static final String PREFERENCES_NAME = "ym_prefs";
	private static Context context;
	
	public static void init(Context c) {
		context = c;
		sharedPreferences = c.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
	}
	//设置 device token
	public static void setDeviceToken(String deviceToken) {
		sharedPreferences.edit().putString("deviceToken", deviceToken).commit();
	}

	public static String getDeviceToken() {
		return sharedPreferences.getString("deviceToken", null);
	}

	public static void setToken(String token) {
		sharedPreferences.edit().putString("access_token", token).commit();
	}

	public static String getToken() {
		return sharedPreferences.getString("access_token", "");
	}



	//清除用户个人信息
	public static void clearUserInfoSpf() {
		setToken("");
	}

	public static SharedPreferences getSharedPreferences(String filename) {
		return context.getSharedPreferences(filename, Context.MODE_PRIVATE);
	}

	public static SharedPreferences getYmSharedPreferences() {
		return sharedPreferences;
	}

	public static void clear() {

	}
}
