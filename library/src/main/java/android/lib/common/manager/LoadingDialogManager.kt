package android.lib.common.manager

import android.lib.common.base.BaseLoadingDialog
import android.lib.common.widget.dialog.loading.LoadingStyle
import android.lib.common.widget.dialog.loading.NearrLoadingDialog
import android.lib.common.widget.dialog.loading.ProgressLoadingDialog

/**
 * @author: yangkui
 * @Date: 2022/8/22
 * @Description: 加载框管理
 */
object LoadingDialogManager {
    fun createInstance(loadingStyle: LoadingStyle): BaseLoadingDialog? {
        return when (loadingStyle) {
            LoadingStyle.NEARR -> NearrLoadingDialog()
            else -> ProgressLoadingDialog()
        }
    }
}