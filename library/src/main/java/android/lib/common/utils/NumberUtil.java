package android.lib.common.utils;

/**
 * @author: yangkui
 * @Date: 2022/5/10
 * @Description:
 */
public class NumberUtil {
    // string to double
    public static double string2Double(String string) {
        try {
            return Double.parseDouble(string);
        } catch (Exception exception) {
        }
        return 0;
    }

    public static int float2int(float f) {
        return (int) f;
    }

    public static float int2float(int i) {
        return (float) i;
    }

    public static int string2Int(String string) {
        return Integer.parseInt(string);
    }

    public static double float2Double(float f) {
        return (double) f;
    }

    public static int double2Int(double f) {
        return (int) f;
    }
}
