package android.lib.common.utils;

/**
 * @author: yangkui
 * @Date: 2022/5/13
 * @Description: 算法
 */
public class AlgorithmUtil {
    // 冒泡排序
    public static void bubbleSort(Integer[] arr) {
        if (arr == null) return;
        int n = arr.length;
        for (int i = 0; i < n; ++i) {
            boolean flag = false;
            for (int j = 0; j < n - i - 1; ++j) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    flag = true;
                }
            }
            if (!flag) break;
        }
    }
}
