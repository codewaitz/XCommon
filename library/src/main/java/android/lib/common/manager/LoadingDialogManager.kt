package android.lib.common.manager

import android.lib.common.base.BaseApplication
import android.lib.common.base.BaseLoadingDialog
import android.lib.common.widget.dialog.loading.LoadingStyle
import android.lib.common.widget.dialog.loading.NearrLoadingDialog
import android.lib.common.widget.dialog.loading.ProgressLoadingDialog
import android.lib.common.widget.dialog.loading.WehandyLoadingDialog
import androidx.fragment.app.FragmentActivity

/**
 * @author: yangkui
 * @Date: 2022/8/22
 * @Description: 加载框管理
 */
object LoadingDialogManager {
    private var loadingDialog: BaseLoadingDialog? = null
    private var activity: FragmentActivity? = null

    fun show(act: FragmentActivity) {
        synchronized(this) {
            dismiss()
            if (loadingDialog == null || activity != act) {
                loadingDialog = createInstance(BaseApplication.loadingStyle)
            }
            loadingDialog?.showDialog(act.supportFragmentManager)
            this.activity = act
        }
    }

    fun dismiss() {
        loadingDialog?.dismissDialog()
    }

    private fun createInstance(loadingStyle: LoadingStyle): BaseLoadingDialog? {
        return when (loadingStyle) {
            LoadingStyle.NEARR -> NearrLoadingDialog()
            LoadingStyle.WEHANDY -> WehandyLoadingDialog()
            else -> ProgressLoadingDialog()
        }
    }
}