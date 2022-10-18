package android.lib.common.utils

import android.os.Build


/**
 * @author: yangkui
 * @Date: 2022/4/15
 * @Description: 手机
 */
object PhoneUtil {
    // 是否三星手机
    fun isSamsungDevice(): Boolean {
        LogUtil.log(Build.BRAND + " : " + Build.MODEL)
        return Build.BRAND == "samsung"
    }
}