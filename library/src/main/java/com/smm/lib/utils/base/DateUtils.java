package com.smm.lib.utils.base;

import android.text.format.DateFormat;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期工具栏
 * Created by zhangdi on 16/5/4.
 */
public class DateUtils {
    private static int[] month_day = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    //  获取当前时间  格式  年-月-日
    public static String getNowDate() {
        return DateFormat.format("yyyy-MM-dd", Calendar.getInstance().getTime()).toString();
    }

    public static String getAllAeraTime(long time) {
        Calendar calendar = Calendar.getInstance();
        String nowData = DateFormat.format("MM-dd", calendar.getTime()).toString();
        int year = calendar.get(Calendar.YEAR);
        calendar.setTimeInMillis(time * 1000);
        int year2 = calendar.get(Calendar.YEAR);

        if (year != year2) {
            return DateFormat.format("MM-dd yyyy", calendar.getTime()).toString();
        }
        String data = DateFormat.format("MM-dd hh:mm a", calendar.getTime()).toString();
        if (data.startsWith(nowData)) {
            return data.substring(6) + " " + TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT);
        }
        return data.substring(0, 6);
    }

    /**
     * 时间规则
     * 0~1分钟     Less than 1minute
     * 1~60分钟    1~60minutes ago
     * 1~24小时     1~24hours ago
     * 超过24小时的     月日年    Oct 13 2017
     * 数字是 1 的时候，单位要变成单数形式（不加s）
     *
     * @param time (秒)
     * @return
     */
    public static String getTimePeriod(long time) {
        long diff;
        long nowTime = System.currentTimeMillis();
        if (time - nowTime / 1000 > 0) {
            diff = time - nowTime / 1000;
        } else {
            diff = nowTime / 1000 - time;
        }
        StringBuffer sBuffer = new StringBuffer();
        long day = (diff / 3600) / 24;
        long hour = diff / 3600;
        long min = diff / 60;
        Log.i("news time:","nowTime / 1000: "+nowTime / 1000 +" time: "+ time + " diff: "+diff+" min: "+min);
        if (day > 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("MMM.dd", Locale.ENGLISH);
            sBuffer.append(sdf.format(calendar.getTime()));
        } else {
            if (hour == 0) {
                if (min < 1) {
                    sBuffer.append("Less than 1min");
                } else if (min == 1) {
                    sBuffer.append(min + "min ago");
                } else {
                    sBuffer.append(min + "mins ago");
                }
            } else if (hour == 1) {
                sBuffer.append(hour + "hr ago");
            } else if(hour > 1){
                sBuffer.append(hour + "hrs ago");
            }
        }
        return sBuffer.toString();
    }

    public static String getEnglishMonth(int month) {
        switch (month) {
            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "Aug";
            case 9:
                return "Sept";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dec";
        }
        return "";
    }

    /**
     * 获取英文时间格式
     *
     * @param time yyyy-mm-dd
     * @return
     */
    public static String getEnglishTimeShow(String time) {
        String newTime;
        try {
            String t[] = time.split("-");
            newTime = getEnglishMonth(Integer.parseInt(t[1])) + "." + t[2] + ", " + t[0];
        } catch (Exception e) {
            return time;
        }
        return newTime;
    }
    public static String getEnglishTimeShownoYear(String time) {
        String newTime;
        try {
            String t[] = time.split("-");
            newTime = getEnglishMonth(Integer.parseInt(t[1])) + "." + t[2];
        } catch (Exception e) {
            return time;
        }
        return newTime;
    }
    public static String getEnglishTimeShow2(String time) {
        String newTime;
        try {
            String t[] = time.split("-");
            newTime = getEnglishMonth(Integer.parseInt(t[1])) + "." + t[2] + ", " + t[0];
        } catch (Exception e) {
            return time;
        }
        return newTime;
    }
    /**
     * 将时间戳转为字符串
     *
     * @param lcc_time 毫秒
     * @param flag     0 代表 全部状态  1 代表年月日 2 代表年月日时分
     * @return
     */
    public static String timestampToStr(Long lcc_time, int flag) {
        String re_StrTime = null;
        SimpleDateFormat sdf = null;
        if (flag == 1) {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        } else if (flag == 2) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        } else if (flag == 3) {
            sdf = new SimpleDateFormat("MM.dd");
        } else if (flag == 4) {
            sdf = new SimpleDateFormat("HH:mm");
        } else if (flag == 5) {
            sdf = new SimpleDateFormat("MM月dd日 HH:mm:ss");
        } else if (flag == 6) {
            sdf = new SimpleDateFormat("MM月dd日");
        } else {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        re_StrTime = sdf.format(new Date(lcc_time));
        return re_StrTime;

    }

    //  获取指定 多少个月  多少年  以前的时间
    public static String getTimeBefore(int year, int month) {
        Calendar cl = Calendar.getInstance();
        for (int i = 0; i < year; i++) {
            cl = getBeforeYear(cl);
        }
        for (int i = 0; i < month; i++) {
            cl = getBeforeMouth(cl);
        }
        return DateFormat.format("yyyy-MM-dd", cl.getTime()).toString();
    }

    //   获取上一个月
    private static Calendar getBeforeMouth(Calendar cl) {
        int month = cl.get(Calendar.MONTH);
        cl.set(Calendar.MONTH, month - 1);
        return cl;
    }

    //   获取上一年
    public static Calendar getBeforeYear(Calendar cl) {
        int year = cl.get(Calendar.YEAR);
        cl.set(Calendar.YEAR, year - 1);
        return cl;
    }


    /**
     * 将 int  类型的String字符串 转换为int输出
     *
     * @param s int类型的字符串 长度大于10 为不正常   字符串中空格跳过
     * @return 正常字符串放回值为对应的int    不正常的返回0
     */
    public static int getIntFromString(String s) {
        int numAll = 0;
        int numIndex = 0;
        byte by[] = s.getBytes();
        int size = by.length;
        if (size > 10) {
            return 0;
        }
        for (int i = 0; i < size; i++) {
            if (by[i] == ' ') {
                continue;
            }
            if (by[i] >= '0' && by[i] <= '9') {
                numIndex = by[i] - '0';
                numAll = numAll * 10 + numIndex;
            } else {
                return 0;
            }
        }
        return numAll;
    }


    //   获取指定日期在当年中是第几天
    public static int getDaynumForDate(int year, int month, int day) {
        int num = day;
        for (int i = 1; i < month; i++) {
            num += month_day[i - 1];
        }
        if (year % 4 == 0 && year % 100 != 0 && month > 2) {
            num++;
        }
        return num;
    }

    //  获取一年中有多少天
    public static int getDateForYear(int year) {
        if (year % 4 == 0 && year % 100 != 0) {
            return 366;
        }
        return 365;
    }

    public static int getDateSubDateNoAbs2(String startDate, String endDate) {

        String[] d1 = startDate.split("-");
        String[] d2 = endDate.split("-");
        if (d1.length > 2 && d2.length > 2) {
            int k = 1;
            int startYear = getIntFromString(d1[0]);
            int startMonth = getIntFromString(d1[1]);
            int startDay = getIntFromString(d1[2]);
            int endYear = getIntFromString(d2[0]);
            int endMonth = getIntFromString(d2[1]);
            int endDay = getIntFromString(d2[2]);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(startYear, startMonth, startDay));
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(new Date(endYear, endMonth, endDay));
            if (calendar.getTimeInMillis() > calendar2.getTimeInMillis()) {
                startYear = endYear;
                startMonth = endMonth;
                startDay = endDay;
                endYear = getIntFromString(d1[0]);
                endMonth = getIntFromString(d1[1]);
                endDay = getIntFromString(d1[2]);
                k = -1;
            }
            int num = 0;
            for (int i = startYear; i < endYear; i++) {
                num += getDateForYear(i);
            }
            return k * (getDaynumForDate(endYear, endMonth, endDay) + num - getDaynumForDate(startYear, startMonth, startDay));
        }
        return -10086;
    }


    public static String formatDate(String pattern, Date date) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static final String pattern_yyyyMMdd = "yyyy-MM-dd";
}
