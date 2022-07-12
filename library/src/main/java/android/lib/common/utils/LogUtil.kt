package android.lib.common.utils

import android.util.Log

/**
 * @author: yangkui
 * @Date: 2022/4/19
 * @Description: log
 */
object LogUtil {
    private var isDebug = false

    // 设置日志模式
    fun setMode(isDebug: Boolean) {
        this.isDebug = isDebug
    }

    // 日志输入
    fun log(message: String) {
        if (!isDebug) return
        Log.e("Nearr", message)
    }
}