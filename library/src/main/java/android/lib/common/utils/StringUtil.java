package android.lib.common.utils;

/**
 * @author: yangkui
 * @Date: 2022/5/12
 * @Description:StringUtil
 */
public class StringUtil {
    // 重置空
    public static String resetEmpty(String string, String defaultString) {
        if (isEmpty(string)) {
            return defaultString;
        } else {
            return string.trim();
        }
    }

    // 重置空
    public static String resetEmpty(String string) {
        return resetEmpty(string, "");
    }

    // 判断空
    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    // 格式化价格
    public static String formatPrice(double d) {
        return "$" + format2Decimal(d);
    }

    // 格式化价格
    public static String formatPrice(String string) {
        double d = 0;
        if (!isEmpty(string)) d = Double.parseDouble(string);
        return "$" + format2Decimal(d);
    }

    // 格式化2位小数
    public static String format2Decimal(double d) {
        return String.format("%.2f", d);
    }

    // 格式化1位小数
    public static String format1Decimal(double d) {
        return String.format("%.1f", d);
    }

    // 格式化数量
    public static String formatAmount(int count) {
        return "x " + count;
    }

    // 格式化距离 m->km
    public static String formatDistance(String m) {
        double km = 0;
        try {
            km = Double.parseDouble(m) * 0.0006214;
        } catch (Exception ex) {
        }
        return format1Decimal(km);
    }

    // 补齐两位数字
    public static String patch2String(int num) {
        return patch2String((long) num);
    }

    // 补齐两位数字
    public static String patch2String(long num) {
        String patch = "";
        if (num < 10 && num >= 0) {
            patch = "0";
        }
        return patch + num;
    }

    // 格式化为小写单位
    public static String formatDateUnit(String dateString) {
        return resetEmpty(dateString).replace("AM", "am").replace("PM", "pm");
    }

    // 排序Int,Int
    public static String sortString(String string) {
        try {
            if (isEmpty(string) || string.indexOf(",") == -1) return string;
            String[] strings = string.split(",");
            Integer[] integers = new Integer[strings.length];
            for (int i = 0; i < strings.length; i++) {
                integers[i] = Integer.parseInt(strings[i]);
            }
            AlgorithmUtil.bubbleSort(integers);
            string = "";
            for (int i = 0; i < integers.length; i++) {
                string += "," + integers[i];
            }
            return string.substring(1);
        } catch (Exception exception) {
        }
        return string;
    }
}
