package com.example.test.utils.base;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具栏
 * Created by zhangdi on 16/5/4.
 */
public class DateTimeUtils {

    private static int[] month_day = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    //  获取当前时间  格式  年-月-日
    public static String getNowDate() {
        return DateFormat.format("yyyy-MM-dd", Calendar.getInstance().getTime()).toString();
    }

    //   获取上一年今天的时间
    public static String getBeforeYearDate() {
        return DateFormat.format("yyyy-MM-dd", getBeforeYear(Calendar.getInstance()).getTime()).toString();
    }

    //   获取当前时间  格式   年-月
    public static String getNowDate2() {
        return DateFormat.format("yyyy-MM", Calendar.getInstance().getTime()).toString();
    }

    //   获取当前时间  格式   月-日
    public static String getNowDate3() {
        return DateFormat.format("MM-dd", Calendar.getInstance().getTime()).toString();
    }

    /**
     * 获取到应当访问的时间  用于  详情-- 铜  吕
     * flag   是否是上一天的数据
     *
     * @return
     */
    public static String getTrueTimeDate(boolean flag) {
        Calendar calendar = Calendar.getInstance();
        if (flag) {
            getBeforeDay(calendar);
        }
//    //    Log.i("---------" ,"--------设置过的时间" +DateFormat.format("yyyy-MM-dd HH-mm",calendar.getTime()).toString());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        if (hour < 11 || (hour == 11 && minute < 30)) {
            getBeforeDay(calendar);
        }
//    //    Log.i("---------" ,"--------第一次更改的时间" +DateFormat.format("yyyy-MM-dd HH-mm",calendar.getTime()).toString());
        int week = getNowWeek(calendar);
        for (int i = week; i > 5; i--) {
            getBeforeDay(calendar);
        }
//    //    Log.i("---------" ,"--------最总时间 " +DateFormat.format("yyyy-MM-dd HH-mm",calendar.getTime()).toString());
        return DateFormat.format("yyyy-MM-dd", calendar.getTime()).toString();
    }

    /**
     * 获取当天是星期几  返回值  1 对应 星期一      7对应星期天
     *
     * @return
     */
    public static int getNowWeek(Calendar calendar) {

        int day = calendar.get(calendar.DAY_OF_WEEK);
        day = day - 1;
        day = day == 0 ? 7 : day;
        return day;
    }

    /**
     * 获取当前时间的前一天时间
     *
     * @param cl
     * @return
     */
    private static Calendar getBeforeDay(Calendar cl) {
        //使用roll方法进行向前回滚
        //cl.roll(Calendar.DATE, -1);
        //使用set方法直接进行设置
        int day = cl.get(Calendar.DATE);
        cl.set(Calendar.DATE, day - 1);
        return cl;
    }

    //   将time 与当前时间比较   获得准确的字符串   用于 资讯时间显示
    public static String getShowTime(long time) {

        long now = System.currentTimeMillis() / 1000 - time;
        StringBuffer sb = new StringBuffer("");
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        calendar.setTimeInMillis(time * 1000);
        float now2 = now;
        int hour = (int) (now2 / 60f / 60f % 24);
        int minute = (int) (now2 / 60f % 60);
        int miao = (int) (now2 % 60);
        if(day == calendar.get(Calendar.DATE)){
            if (now < 60) {
                if (miao <= 0) {
                    miao = 1;
                }
                sb.append(miao).append("秒前");
            } else if (now < 60 * 60) {
                sb.append(minute).append("分钟前");
            } else{
                sb.append(hour).append("小时前");
            }
        }else{
            int month = calendar.get(Calendar.MONTH) + 1;
            String s = month + "";
            if (month < 10) {
                s = "0" + s;
            }
            int day2 = calendar.get(Calendar.DATE);
            String d = day2 + "";
            if (day2 < 10) {
                d = "0" + d;
            }
            sb.append(s).append("-")
                    .append(d);
        }
//        else {
//            int h = calendar.get(Calendar.HOUR_OF_DAY);
//            int m = calendar.get(Calendar.MINUTE);
//            String h1 = h + "";
//            String m1 = m + "";
//            if (h < 10) {
//                h1 = "0" + h1;
//            }
//            if (m < 10) {
//                m1 = "0" + m1 + "";
//            }
//            sb.append(h1).append(":").append(m1);
//        }

        return sb.toString();
    }

    public static String getShowTimeTWo(long time, long nowTime) {
        long now = nowTime - time;
//        long now = 20000;
        StringBuffer sb = new StringBuffer("");
        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new GregorianCalendar(2015,01,01).getTime());
//        DateFormat.format("yyyy-MM-dd", Calendar.getInstance().getTime()).toString();
        calendar.setTimeInMillis(time * 1000);
        /**
         * append(calendar.get(Calendar.YEAR)).append("  ")
         .append(calendar.get(Calendar.MONTH)).append("  ")
         .append(calendar.get(Calendar.DATE)).append("  ")
         .append(calendar.get(Calendar.HOUR_OF_DAY)).append("  ")
         .append(calendar.get(Calendar.MINUTE)).append("  ")
         .append(calendar.get(Calendar.SECOND)).append("  ").
         */
//        sb.append(now);
        float now2 = now;
        int hour = (int) (now2 / 60f / 60f % 24);
        int minute = (int) (now2 / 60f % 60);
        int miao = (int) (now2 % 60);
//        sb.append(day).append(" hour:")
//                .append(hour).append("  minute:")
//                .append(minute).append("--");
        if (now < 60) {
            if (miao <= 0) {
                miao = 1;
            }
            sb.append(miao).append("秒前");
        } else if (now < 60 * 60) {
            sb.append(minute).append("分钟前");
        } else if (now < 5 * 60 * 60) {
            sb.append(hour).append("小时前")
            ;
        } else if (now > 24 * 60 * 60) {
            int month = calendar.get(Calendar.MONTH) + 1;
            String s = month + "";
            if (month < 10) {
                s = "0" + s;
            }
            int day2 = calendar.get(Calendar.DATE);
            String d = day2 + "";
            if (day2 < 10) {
                d = "0" + d;
            }
            sb.append(s).append("-")
                    .append(d);
        } else {
            int h = calendar.get(Calendar.HOUR_OF_DAY);
            int m = calendar.get(Calendar.MINUTE);
            String h1 = h + "";
            String m1 = m + "";
            if (h < 10) {
                h1 = "0" + h1;
            }
            if (m < 10) {
                m1 = "0" + m1 + "";
            }
            sb.append(h1).append(":").append(m1);
        }
        return sb.toString();
    }

    //   给定一个时间   获取此时间对应的 小时:分钟
    public static String getHourAndMinute(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time * 1000);
        StringBuffer sb = new StringBuffer("");
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int m = calendar.get(Calendar.MINUTE);
        String h1 = h + "";
        String m1 = m + "";
        if (h < 10) {
            h1 = "0" + h1;
        }
        if (m < 10) {
            m1 = "0" + m1 + "";
        }
        sb.append(h1).append(":").append(m1);
        return sb.toString();
    }

    //   获取4个月前的时间
    public static String getFourMonth() {
        Calendar cl = Calendar.getInstance();
        cl = getBeforeMouth(cl);
//        cl = getBeforeDay(cl);
//        cl = getBeforeDay(cl);
//        cl = getBeforeDay(cl);
        return DateFormat.format("yyyy-MM-dd", cl.getTime()).toString();
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

    //  获取指定 多少个月  多少年  以前的时间
    public static String getTimeBefore2(int year, int month) {
        Calendar cl = Calendar.getInstance();
        for (int i = 0; i < year; i++) {
            cl = getBeforeYear(cl);
        }
        for (int i = 0; i < month; i++) {
            cl = getBeforeMouth(cl);
        }
        return DateFormat.format("yyyy-MM", cl.getTime()).toString();
    }


    public static String getTimeBeforeDay(int num) {
        Calendar cl = Calendar.getInstance();
        for (int i = 0; i < num; i++) {
            cl = getBeforeDay(cl);
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

    //  9.30  11.30       1.30 300    9  1

    /**
     * 判断  实时更新 连接断开后是否需要重新连接
     *
     * @return true 重新连接     false  不重新连接
     */
    public static boolean isNeedReconnection() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        if (hour < 1 || hour > 21) {
            return true;
        }
        if (hour == 10 || (hour == 9 && minute > 30) || (hour == 11 && minute < 30)) {
            return true;
        }
        if (hour == 14 || hour == 15 || (hour == 13 && minute > 30)) {
            return true;
        }
        return true;
    }

    //  获取制定月的天数
    public static int getThisMonthHaveDayNum(int year, int month) {
        int num = month_day[month - 1];
        if (year % 4 == 0 && year % 100 != 0 && month == 2) {
            num++;
        }
        return num;
    }

    //  获取日期中月份的最后一天
    public static String getTheMonthEndDaty(String a) {
        String b[] = a.split("-");
        if (b.length > 2) {
            return a.substring(0, a.length() - 2) + getThisMonthHaveDayNum(getIntFromString(b[0]), getIntFromString(b[1]));
        }
        return a;
    }

    //  获取今天是星期几
    public static int getWeekOfNow() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    //  获取指定哪天在当年中是星期几  返回: 1-->星期一      7-->星期天
    public static int getWeekOfDay(int year, int month, int day) {
//        LogUtils.showLog("===========",year+" y ",month + " m",day+" d");
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day - 1);
//        calendar.setTime(new GregorianCalendar(year,month - 1,day).getTime());
//        LogUtils.showLog("===========",new GregorianCalendar(year,month,day).getTime()+" gettime");
//        calendar.setTime(new Date(year,month,day));
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static int getDayNumInMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取指定哪天在当年中是星期几
     *
     * @param s yyyy-MM-dd   格式不对则出错  空格跳过
     * @return 1-->星期一      7-->星期天   字符串格式不对 返回 0
     */
    public static int getWeekOfDay(String s) {
        if (s.contains("-")) {
            String[] c = s.split("-");
            if (c.length == 3) {
                return getWeekOfDay(getIntFromString(c[0]), getIntFromString(c[1]), getIntFromString(c[2]));
            }
        }
        return 0;
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

    public static String getFoundShowTime(String time) {
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(getLongtime(time));
        Calendar cl = Calendar.getInstance();
        String date = DateFormat.format("yyyy-MM-dd", ca.getTime()).toString();
        String date2 = DateFormat.format("yyyy-MM-dd", cl.getTime()).toString();
        if (date.equals(date2)) {
            date = "今天 ";
        }
        return date + DateFormat.format("HH:mm", ca.getTime()).toString();
    }

    public static String getFoundShowTime(long time) {
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(time / 1000000);
        Calendar cl = Calendar.getInstance();
        String date = DateFormat.format("yyyy-MM-dd", ca.getTime()).toString();
        String date2 = DateFormat.format("yyyy-MM-dd", cl.getTime()).toString();
        if (date.equals(date2)) {
            date = "今天";
        }
        return date + DateFormat.format(" HH:mm", ca.getTime()).toString();
    }

    //  将毫秒 字符串时间转换成 long类型
    public static long getLongtime(String time) {
        long numAll = 0;
        long numIndex = 0;
        byte by[] = time.getBytes();
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
        return numAll / 1000000;
    }

    public static String getThisDate(long time) {
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(time / 1000000);
        return DateFormat.format("yyyy-MM-dd", ca.getTime()).toString();
    }

    //  获取前一天日期   日期格式   yyyy-MM-dd
    public static String getBeforeDay(String date) {
        String s[] = date.split("-");
        String newDate = date;
        if (s.length == 3) {
            int num = getIntFromString(s[2]) - 1;
            int month = getIntFromString(s[1]);
            int year = getIntFromString(s[0]);
            if (num == 0) {
                month--;
                if (month == 0) {
                    month = 12;
                    year--;
                }
                num = month_day[month];
                if (month == 2) {
                    if (year % 4 == 0 && year % 100 != 0) {
                        num = 29;
                    }
                }
            }
            StringBuffer sb = new StringBuffer(year);
            sb.append("-").append(month).append("-").append(num);
            newDate = sb.toString();
        }
        return newDate;
    }

    //  获取后一天日期   日期格式   yyyy-MM-dd
    public static String getNextDay(String date) {
        String s[] = date.split("-");
        String newDate = date;
        if (s.length == 3) {
            int num = getIntFromString(s[2]) + 1;
            int month = getIntFromString(s[1]);
            int year = getIntFromString(s[0]);
            if (num > month_day[month - 1]) {
                if (month == 2 && year % 4 == 0 && year % 100 != 0) {
                    if (num > 29) {
                        num = 1;
                        month++;
                    }
                } else {
                    num = 1;
                    month++;
                    if (month > 12) {
                        month = 1;
                        year++;
                    }
                }
            }
            StringBuffer sb = new StringBuffer("");
            sb.append(year).append("-");
            if (month < 10) {
                sb.append("0");
            }
            sb.append(month).append("-");
            if (num < 10) {
                sb.append("0");
            }
            sb.append(num);
            newDate = sb.toString();
        }
        return newDate;
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

    /**
     * 获取两天之差
     *
     * @param startDate 开始时间   格式为 yyyy-MM-dd
     * @param endDate   结束时间   格式为 yyyy-MM-dd
     * @return 返回两天之差的绝对值   时间格式不对返回-10086
     */
    public static int getDateSubDate(String startDate, String endDate) {
        int num = getDateSubDateNoAbs(startDate, endDate);
        if (num != -10086) {
            num = Math.abs(num);
        }
        return num;
    }

    /**
     * 获取两天之差
     *
     * @param startDate 开始时间   格式为 yyyy-MM-dd
     * @param endDate   结束时间   格式为 yyyy-MM-dd
     * @return 返回两天之差   时间格式不对返回-10086
     */
    public static int getDateSubDateNoAbs(String startDate, String endDate) {
        String[] d1 = startDate.split("-");
        String[] d2 = endDate.split("-");
        if (d1.length > 2 && d2.length > 2) {
            int startYear = getIntFromString(d1[0]);
            int startMonth = getIntFromString(d1[1]);
            int startDay = getIntFromString(d1[2]);
            int endYear = getIntFromString(d2[0]);
            int endMonth = getIntFromString(d2[1]);
            int endDay = getIntFromString(d2[2]);
//            if(startYear == endYear){
//                return Math.abs(getDaynumForDate(endYear,endMonth,endDay) - getDaynumForDate(startYear,startMonth,startDay));
//            }
            int num = 1;
            for (int i = startYear; i < endYear; i++) {
                num += getDateForYear(i);
            }
            return getDaynumForDate(endYear, endMonth, endDay) + num - getDaynumForDate(startYear, startMonth, startDay);
        }
        return -10086;
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

    //  获取两天之差  时间格式  yyyy-MM-dd ~ yyyy-MM-dd
    public static int getDateSubDate(String date) {
        String d[] = date.split("~");
        if (d.length > 1) {
            return getDateSubDate(d[0].trim(), d[1].trim());
        }
        return -1;
    }


    /**
     * 计算差多少天
     *
     * @param dateAfter  靠后的日期
     * @param dateBefore 靠前的日期
     *                   NOTE：如果 dateAfter<dateBefore,返回0
     * @return
     */
    public static int compare(long dateAfter, long dateBefore) {
        if (dateAfter < dateBefore) return 0;
        Calendar calendarAfter = Calendar.getInstance();
        calendarAfter.setTimeInMillis(dateAfter);
        Calendar calendarBefore = Calendar.getInstance();
        calendarAfter.setTimeInMillis(dateBefore);
        int count = 0;
        while (calendarAfter.compareTo(calendarBefore) > 0) {
            calendarBefore.add(Calendar.DATE, 1);
            count++;
        }
        return count;
    }

    /**
     * @param chatTime 纳秒
     * @return
     */
    public static String getLiveChatTime(long chatTime) {
        return new SimpleDateFormat("HH:mm").format(chatTime / 1000000);
    }

    public static String getBossMsgListTime(long chatTime) {
        chatTime = chatTime / 1000000;
        if (chatTime > System.currentTimeMillis() - 24 * 60 * 60 * 1000) {
            return new SimpleDateFormat("HH:mm").format(chatTime);
        } else {
            return new SimpleDateFormat("MM-dd").format(chatTime);
        }
    }

    public static String getLiveHistoryTime(long indexTime, long chatTime) {
        String timeStr;
        chatTime = chatTime / 1000000;
        if (chatTime > indexTime) {
            timeStr = new SimpleDateFormat("HH:mm").format(chatTime);
        } else {
            timeStr = new SimpleDateFormat("yyyy-MM-dd").format(chatTime);
        }
        return timeStr;
    }

    /**
     * @param chatTime 纳秒
     * @return
     */
    public static String getChatTime(long chatTime) {
        String timeStr;
        chatTime = chatTime / 1000000;
       /* long nowTime = System.currentTimeMillis();
        long threeDayAgoTime = nowTime - 24 * 60 * 60 * 1000;
        //3天以内的显示月日时分秒   3天以外的显示年月日时分秒
        if (chatTime > threeDayAgoTime) {
            timeStr = new SimpleDateFormat("MM-dd HH:mm:ss").format(chatTime);
        } else {
            timeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(chatTime);
        }*/
        if (chatTime > System.currentTimeMillis() - 24 * 60 * 60 * 1000) {
            timeStr = new SimpleDateFormat("HH:mm").format(chatTime);
        } else {
            timeStr = new SimpleDateFormat("MM-dd HH:mm").format(chatTime);
        }
        return timeStr;
    }

    /**
     * 获取当天0点的时间戳
     *
     * @param timeMilli 时间戳
     * @return 毫秒
     */
    public static long getZeroTimestamp(long timeMilli) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeMilli);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 获取今天0点的时间戳
     *
     * @return 毫秒
     */
    public static long getZeroTimestamp() {
        return getZeroTimestamp(System.currentTimeMillis());
    }

    public static String formatDate(String pattern, Date date) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static final String pattern_yyyyMMdd = "yyyy-MM-dd";
}
