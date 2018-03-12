package com.smm.lib.utils.validate;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 一些常用工具
 * 
 * @author zj
 *
 */
@SuppressLint("SimpleDateFormat")
public class TransformUtils {
	
	/** 一秒钟 单位：毫秒 */
	public static final long ONE_SECOND = 1000;
	/** 一分钟 单位：毫秒 */
	public static final long ONE_MINUTE = 60 * ONE_SECOND;
	/** 一小时 单位：毫秒 */
	public static final long ONE_HOUR = 60 * ONE_MINUTE;
	/** 一天 单位：毫秒 */
	public static final long ONE_DAY = 24 * ONE_HOUR;

	public static final long ONE_WEEK = 7 * ONE_DAY;

	public static final String[] WEEK_STRINGS1 = new String[] { "一", "二", "三",
			"四", "五", "六", "日" };
	public static final String[] WEEK_STRINGS2 = new String[] { "周一", "周二",
			"周三", "周四", "周五", "周六", "周日" };

	public enum TransferFormat {
		FORMAT_YYYY_MM_dd("yyyy-MM-dd"), FORMAT_YYYY_MM_dd_HH_mm(
				"yyyy-MM-dd HH:mm"), FORMAT_YYYY_MM_dd_HH_mm_ss(
				"yyyy-MM-dd HH:mm:ss"), FORMAT_HH_mm("HH:mm");
		private String transferFormat;

		private TransferFormat(String transferFormat) {
			this.transferFormat = transferFormat;

		}

		public String getTransferFormat() {
			return transferFormat;
		}
	}

	// 修改应用程序的时区
	static {
		System.setProperty("user.timezone", "Asia/Shanghai");
		TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
		TimeZone.setDefault(tz);
	}

	/**
	 * 将时间转换为指定的格式，格式由TransferFormat枚举设置
	 * 
	 * @param time
	 *            单位为秒
	 * @param transferFormat
	 *            TODO
	 * @return
	 */
	public static String parseTime(int time, TransferFormat transferFormat) {
		SimpleDateFormat format = new SimpleDateFormat(
				transferFormat.getTransferFormat(), Locale.CHINA);
		String times = format.format(new Date(time * ONE_SECOND));
		return times;
	}

	/**
	 * 将指定格式转换为时间（单位为 秒）
	 * 
	 * @param timeStr
	 * @param transferFormat
	 * @return
	 */
	public static int parseTime(String timeStr, TransferFormat transferFormat) {
		SimpleDateFormat format = new SimpleDateFormat(
				transferFormat.getTransferFormat(), Locale.CHINA);
		int time = 0;
		try {
			Date date = format.parse(timeStr);
			time = (int) (date.getTime() / ONE_SECOND);
		} catch (ParseException e) {
		}
		return time;
	}
	/** * 获取指定日期是星期几
	  * 参数为null时表示获取当前日期是星期几
	  * @param date
	  * @return
	*/
	public static String getWeekOfDate(String time,int flag) {      
		
		SimpleDateFormat sdf = null;
		if(flag == 1){
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}else if(flag == 2){
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		}else{
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};        
	    Calendar calendar = Calendar.getInstance();      
	    if(date != null){        
	         calendar.setTime(date);      
	    }        
	    int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;      
	    if (w < 0){        
	        w = 0;      
	    }      
	    return weekOfDays[w];    
	}
	/**
	 * 获取当前时间是周几
	 * @return
	 */
	private static int getDayOfWeek(long time) {
		// 初始化当前时间
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);

		int dayOfWeek = 0;
		if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
			dayOfWeek = 7;
		} else {
			dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayOfWeek;
	}

	public static String toExpressDate(long time, long now) {
		StringBuffer sBuffer = new StringBuffer();

		// 获取当前时间为本周几
		int dayOfWeek = getDayOfWeek(now);

		// 获取本周一零点时间
		Calendar tempCalendar = Calendar.getInstance();
		tempCalendar.setTimeInMillis(now);
		tempCalendar.add(Calendar.DATE, 1 - dayOfWeek);
		tempCalendar.set(Calendar.HOUR_OF_DAY, 0);
		tempCalendar.set(Calendar.MINUTE, 0);
		tempCalendar.set(Calendar.SECOND, 0);
		tempCalendar.set(Calendar.MILLISECOND, 0);

		// 本周一零点时间
		long thisWeek1 = tempCalendar.getTimeInMillis();
		// 下周一零点时间（本周日24点时间）
		long nextWeek1 = thisWeek1 + ONE_WEEK;
		// 下下周一零点时间（下周日24点时间）
		long nextWeek7 = nextWeek1 + ONE_WEEK;

		// 获取具体 time 时间
		// -----------------------------------------
		Calendar timeCC = Calendar.getInstance();
		timeCC.setTimeInMillis(time);
		int month = timeCC.get(Calendar.MONTH) + 1;
		int date = timeCC.get(Calendar.DATE);
		int timeDayOfWeek = getDayOfWeek(timeCC.getTimeInMillis());

		sBuffer.append(month < 10 ? "0" + month : month).append("月");
		sBuffer.append(date).append("日");
		sBuffer.append("(");

		// 计算是本周、下周、其他
		if (time >= thisWeek1 && time < nextWeek1) {
			sBuffer.append("本周");
			sBuffer.append(WEEK_STRINGS1[timeDayOfWeek - 1]);
		} else if (time >= nextWeek1 && time < nextWeek7) {
			sBuffer.append("下周");
			sBuffer.append(WEEK_STRINGS1[timeDayOfWeek - 1]);
		} else {
			sBuffer.append(WEEK_STRINGS2[timeDayOfWeek - 1]);
		}
		sBuffer.append(")");

		return sBuffer.toString();
	}
	
	
	public static String toNewWeek(long time, long now) {
		
		// 获取当前时间为本周几
		int dayOfWeek = getDayOfWeek(now);

		// 获取本周一零点时间
		Calendar tempCalendar = Calendar.getInstance();
		tempCalendar.setTimeInMillis(now);
		tempCalendar.add(Calendar.DATE, 1 - dayOfWeek);
		tempCalendar.set(Calendar.HOUR_OF_DAY, 0);
		tempCalendar.set(Calendar.MINUTE, 0);
		tempCalendar.set(Calendar.SECOND, 0);
		tempCalendar.set(Calendar.MILLISECOND, 0);

		// 本周一零点时间
		long thisWeek1 = tempCalendar.getTimeInMillis();
		// 下周一零点时间（本周日24点时间）
		long nextWeek1 = thisWeek1 + ONE_WEEK;
		// 下下周一零点时间（下周日24点时间）
		long nextWeek7 = nextWeek1 + ONE_WEEK;

		// 获取具体 time 时间
		// -----------------------------------------
		Calendar timeCC = Calendar.getInstance();
		timeCC.setTimeInMillis(time);
		int timeDayOfWeek = getDayOfWeek(timeCC.getTimeInMillis());


		// 计算是本周、下周、其他
		StringBuffer sBuffer = new StringBuffer();
		if (time >= thisWeek1 && time < nextWeek1) {
			sBuffer.append("本周");
			sBuffer.append(WEEK_STRINGS1[timeDayOfWeek - 1]);
		} else if (time >= nextWeek1 && time < nextWeek7) {
			sBuffer.append("下周");
			sBuffer.append(WEEK_STRINGS1[timeDayOfWeek - 1]);
		} else {
			sBuffer.append(WEEK_STRINGS2[timeDayOfWeek - 1]);
		}

		return sBuffer.toString();
	}

	/**
	 * 以 秒 的形式返回时间
	 * 
	 * @return
	 */
	public static int getCurrentDate() {
		Calendar calendar = Calendar.getInstance();
		return (int) (calendar.getTimeInMillis() / 1000);
	}
	
	
	public static String  getHourMinuteOfDay(){
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("aHH:mm");
		return format.format(calendar.getTime());
	}
	

	/**
	 * 从sytemCurrentTime到endTime的之间间隔
	 * 
	 * @param endTime
	 *            （单位 秒）
	 * @param systemCurrentTime
	 *            （单位 秒）
	 * @return 如果时间超过一小时，返回 **小时，如果时间在一个小时之内，返回 **分钟
	 */
	public static String dateLeft(int endTime, int systemCurrentTime) {

		int diff = endTime - systemCurrentTime;
		try {
			long hour = diff / 3600; // 计算差多少小时
			long min = diff % 3600 / 60;// 计算差多少分钟

			StringBuffer sBuffer = new StringBuffer();
			if (hour == 0) {
				sBuffer.append(min).append("分钟");
			} else {
				sBuffer.append(hour).append("小时");
			}
			return sBuffer.toString();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 如果 (startTim - endTime)的绝对值小于interval，则返回True，否则返回False。3个参数单位保持一致
	 * 
	 * @param startTime
	 * @param endTime
	 * @param interval
	 * @return
	 */
	public static boolean compareTime(long startTime, long endTime,
			long interval) {
		return Math.abs(endTime - startTime) < interval;
	}
	
	public static String formatDoubleDecimal(double num) {
		
		String result = null;		
		if( num % 1.0 == 0 ) {
			result = String.valueOf(  (long)num );
		} else{
			result = String.valueOf( num );
		}
		return result;
	}
	
	// 将字符串转为时间戳
	public static String getLongTime(String user_time) {
		String re_time = null;
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d;
		try {

			d = sdf.parse(user_time);
			long l = d.getTime();
			String str = String.valueOf(l);
			re_time = str.substring(0, 10);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return re_time;
	}
	// 将字符串转为时间戳 各种匹配
	public static String getAllLongTime(String user_time,int flag) {
		String re_time = null;
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = null;
		if(flag == 1){
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}else if(flag == 2){
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		}else{
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		Date d;
		try {

			d = sdf.parse(user_time);
			long l = d.getTime();
			String str = String.valueOf(l);
			re_time = str.substring(0, 10);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return re_time;
	}
	
	/**
	 *  将时间戳转为字符串
	 * @param cc_time
	 * @param flag 0 代表 全部状态  1 代表年月日 2 代表年月日时分 
	 * @return
	 */
	public static String getStrTime(String cc_time,int flag) {
		String re_StrTime = null;
		SimpleDateFormat sdf = null;
		if(flag == 1){
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}else if(flag == 2){
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		}else{
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
	
		return re_StrTime;

	}
	/**
	 *  将时间戳转为完整的 时间格式 字符串
	 * @param cc_time
	 * @param flag  0 代表 全部状态  1 代表年月日 2 代表年月日时分 
	 * @return
	 */
	public static String getAllStrTime(String cc_time,int flag) {
		String re_StrTime = null;
		
		SimpleDateFormat sdf = null;
		if(flag == 1){
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}else if(flag == 2){
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		}else{
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
	
		return re_StrTime;

	}
}
