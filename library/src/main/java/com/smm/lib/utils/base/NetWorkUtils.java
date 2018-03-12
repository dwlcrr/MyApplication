package com.smm.lib.utils.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

public class NetWorkUtils {
	/**
	 * 判断wifi是否连接
	 * 
	 * @return
	 */
	public static boolean isWifiConnected(Context context) {
		WifiManager manager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		return manager.isWifiEnabled();
	}

	/**
	 * 检查网络情况，如果连接成功返回True
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkNetState(Context context) {

		NetworkInfo networkInfo = ((ConnectivityManager) context
				.getSystemService("connectivity")).getActiveNetworkInfo();
		if (networkInfo == null) {
			return false;
		} else {
			if (networkInfo.isAvailable())
				return true;
		}
		return true;
	}

	/**
	 * 检查网络状态，如果网络不通就发送通知
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkNetStateWithToast(Context context) {
		if (!checkNetState(context)) {
			BaseUtils.showToast(context, "网络不给力");
			return false;
		}
		return true;
	}

	/**
	 * 监测网络是否联通（所有网络中只要有一个网络联通就返回True）
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isConnectionAvailable(Context context) {
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// 获取网络连接管理的对象
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) { //
					// 判断当前网络是否已经连接
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 监测Wifi是否连接到网络
	 *
	 * @param context
	 * @return
	 */
	public static boolean isWifiNetworkAvailable(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifi = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		boolean flag = false;
		if ((wifi != null) && (wifi.isAvailable())) {
			if (wifi.isConnected()) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 监测手机网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isMobileNetworkAvailable(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobile = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		boolean flag = false;
		if (mobile.isAvailable()) {
			if (mobile.isConnected()) {
				flag = true;
			}
		}
		return flag;
	}
}
