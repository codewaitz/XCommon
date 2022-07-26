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
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

/**
 * @author: yangkui
 * @Date: 2022/4/19
 * @Description: Toast显示
 */
object XToast {
    private var duration = 1000 // 显示时长
    private var staticSuccessImgRes = 0 // 成功显示图片
    private var staticFailImgRes = 0 // 失败显示图片

    // 设置状态图片
    fun staticImgRes(successImgRes: Int, failImgRes: Int) {
        staticSuccessImgRes = successImgRes
        staticFailImgRes = failImgRes
    }

    // 显示文本信息
    fun showText(text: String?) {
        if (StringUtil.isEmpty(text)) return
        var activity: Activity? = AllActivityManager.getTopActivity() ?: return
        var attachView = activity!!.window.decorView
        var snackBar = Snackbar.make(attachView, "", duration)
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
    fun showSuccess(text: String?) {
        showSuccess(text, 0)
    }

    // 显示成功状态信息
    fun showSuccess(text: String?, imgRes: Int) {
        if (StringUtil.isEmpty(text)) return
        var activity: Activity? = AllActivityManager.getTopActivity() ?: return
        var attachView = activity!!.window.decorView
        var snackBar = Snackbar.make(attachView, "", duration)
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
        // 设置图片
        var imageRes = 0
        if (imgRes != 0) {
            imageRes = imgRes
        } else {
            if (staticSuccessImgRes != 0) {
                imageRes = staticSuccessImgRes
            }
        }
        if (imageRes != 0) {
            view.findViewById<ImageView>(R.id.common_toast_success_image).setImageResource(imageRes)
        }
        view.findViewById<TextView>(R.id.common_toast_success_text).text = text
        snackBarView.removeAllViews()
        snackBarView.addView(view)
        snackBar.show()
    }

    // 显示失败状态信息
    fun showFail(text: String?) {
        showFail(text, 0)
    }

    // 显示失败状态信息
    fun showFail(text: String?, imgRes: Int) {
        if (StringUtil.isEmpty(text)) return
        var activity: Activity? = AllActivityManager.getTopActivity() ?: return
        var attachView = activity!!.window.decorView
        var snackBar = Snackbar.make(attachView, "", duration)
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
        var imageRes = 0
        if (imgRes != 0) {
            imageRes = imgRes
        } else {
            if (staticFailImgRes != 0) {
                imageRes = staticFailImgRes
            }
        }
        if (imageRes != 0) {
            view.findViewById<ImageView>(R.id.common_toast_fail_image).setImageResource(imageRes)
        }
        view.findViewById<TextView>(R.id.common_toast_fail_text).text = text
        snackBarView.removeAllViews()
        snackBarView.addView(view)
        snackBar.show()
    }
}