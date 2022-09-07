package android.lib.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * @author: yangkui
 * @Date: 2022/5/9
 * @Description:
 */
public class DateTimeUtil {
    public static String formatTime(int hourOfDay, int minute) {
        return String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
    }

    public static String formatCurDate() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return simpleDateFormat.format(new Date());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public static long betweenFromCurDate(String dataTime) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return System.currentTimeMillis() - simpleDateFormat.parse(dataTime).getTime();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public static long minute2Mis(int minute) {
        return minute * 60 * 1000;
    }

    public static String mis2MinuteSecond(long mis) {
        long minutes = (mis / 1000) / 60;
        long seconds = (mis / 1000) % 60;
        return StringUtil.patch2String(minutes) + ":" + StringUtil.patch2String(seconds);
    }

    public static String getTomorrowDateString() {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, 1);//1表示明天,-1表示昨天
        date = calendar.getTime(); //这个时间就是明天
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;
    }

    public static Date string2Date(String dateString, String pattern) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.parse(dateString);
        } catch (Exception ex) {
        }
        return null;
    }

    public static String date2String(Date date, String pattern) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.format(date);
        } catch (Exception ex) {
        }
        return "";
    }

    // 字符串日期转上下午单位日期字符串
    public static String date2UnitString(Date date, String pattern) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.ENGLISH);
            return simpleDateFormat.format(date);
        } catch (Exception ex) {
        }
        return null;
    }
}
