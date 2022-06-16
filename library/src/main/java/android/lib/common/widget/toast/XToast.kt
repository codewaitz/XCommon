package android.lib.common.widget.toast

import android.app.Activity
import android.graphics.Color
import android.lib.common.R
import android.lib.common.manager.AllActivityManager
import android.lib.common.utils.StringUtil
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

/**
 * @author: yangkui
 * @Date: 2022/4/19
 * @Description: Toast显示
 */
object XToast {
    // 显示文本信息
    fun showText(text: String) {
        if (StringUtil.isEmpty(text)) return
        var activity: Activity? = AllActivityManager.getTopActivity() ?: return
        var attachView = activity!!.window.decorView
        var snackBar = Snackbar.make(attachView, "", Snackbar.LENGTH_SHORT)
        var snackBarView = snackBar.view as Snackbar.SnackbarLayout
        // 设置布局参数
        var lps = snackBarView.layoutParams as FrameLayout.LayoutParams
        lps.width = ViewGroup.LayoutParams.WRAP_CONTENT
        lps.gravity = Gravity.CENTER
        snackBarView.setBackgroundColor(Color.TRANSPARENT)
        snackBarView.setPadding(0, 0, 0, 0)
        // 设置View
        var view = LayoutInflater.from(attachView.context).inflate(R.layout.common_toast_text, null)
        view.findViewById<TextView>(R.id.common_toast_text).text = text
        snackBarView.removeAllViews()
        snackBarView.addView(view)
        snackBar.show()
    }

    // 显示成功状态信息
    fun showSuccess(text: String) {
        if (StringUtil.isEmpty(text)) return
        var activity: Activity? = AllActivityManager.getTopActivity() ?: return
        var attachView = activity!!.window.decorView
        var snackBar = Snackbar.make(attachView, "", Snackbar.LENGTH_SHORT)
        var snackBarView = snackBar.view as Snackbar.SnackbarLayout
        // 设置布局参数
        var lps = snackBarView.layoutParams as FrameLayout.LayoutParams
        lps.width = ViewGroup.LayoutParams.WRAP_CONTENT
        lps.gravity = Gravity.CENTER
        snackBarView.setBackgroundColor(Color.TRANSPARENT)
        snackBarView.setPadding(0, 0, 0, 0)
        // 设置View
        var view =
            LayoutInflater.from(attachView.context).inflate(R.layout.common_toast_success, null)
        view.findViewById<TextView>(R.id.common_toast_success_text).text = text
        snackBarView.removeAllViews()
        snackBarView.addView(view)
        snackBar.show()
    }

    // 显示失败状态信息
    fun showFail(text: String) {
        if (StringUtil.isEmpty(text)) return
        var activity: Activity? = AllActivityManager.getTopActivity() ?: return
        var attachView = activity!!.window.decorView
        var snackBar = Snackbar.make(attachView, "", Snackbar.LENGTH_SHORT)
        var snackBarView = snackBar.view as Snackbar.SnackbarLayout
        // 设置布局参数
        var lps = snackBarView.layoutParams as FrameLayout.LayoutParams
        lps.width = ViewGroup.LayoutParams.WRAP_CONTENT
        lps.gravity = Gravity.CENTER
        snackBarView.setBackgroundColor(Color.TRANSPARENT)
        snackBarView.setPadding(0, 0, 0, 0)
        // 设置View
        var view =
            LayoutInflater.from(attachView.context).inflate(R.layout.common_toast_fail, null)
        view.findViewById<TextView>(R.id.common_toast_fail_text).text = text
        snackBarView.removeAllViews()
        snackBarView.addView(view)
        snackBar.show()
    }
}