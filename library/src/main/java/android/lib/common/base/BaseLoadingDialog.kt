package android.lib.common.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

/**
 * @author: yangkui
 * @Date: 2022/4/13
 * @Description: loading
 */
abstract class BaseLoadingDialog : DialogFragment() {
    override fun onStart() {
        super.onStart()
        //去掉DialogFragment外部的背景色
        dialog?.window?.apply {
            attributes = attributes.apply {
                //======================这里设置背景阴影透明度===============
                //=================================================
                dimAmount = 0.0f
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCanceledOnTouchOutside(false)
            window?.apply {
                //去掉DialogFragment内部的背景色
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                //去掉Padding
                decorView.setPadding(0, 0, 0, 0)
            }
        }
        return onCreateView()
    }

    //显示
    open fun showDialog(fragmentManager: FragmentManager) {
        try {
            if (isVisible) {
                dismiss()
            }
            show(fragmentManager, "")
        } catch (ex: Exception) {
        }
    }

    //隐藏
    open fun dismissDialog() {
        try {
            dismiss()
        } catch (ex: Exception) {
        }
    }

    open abstract fun onCreateView(): View
}