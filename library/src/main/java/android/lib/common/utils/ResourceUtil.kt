package android.lib.common.utils

import android.lib.common.base.BaseApplication
import android.os.Build
import androidx.annotation.ColorInt


/**
 * @author: yangkui
 * @Date: 2022/4/14
 * @Description: 资源
 */
object ResourceUtil {
    @ColorInt
    fun getColor(resId: Int): Int {
        return if (Build.VERSION.SDK_INT >= 23) {
            BaseApplication.instance.getColor(resId)
        } else {
            BaseApplication.instance.resources.getColor(resId)
        }
    }

    fun getDimension(resId: Int): Float {
        return BaseApplication.instance.resources.getDimension(resId)
    }

    fun getString(resId: Int): String {
        return BaseApplication.instance.resources.getString(resId)
    }
}