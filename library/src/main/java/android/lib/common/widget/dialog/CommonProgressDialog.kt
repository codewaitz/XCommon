package android.lib.common.widget.dialog

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.lib.common.databinding.CommonDialogProgressBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.LinearInterpolator
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

/**
 * @author: yangkui
 * @Date: 2022/4/13
 * @Description: Common Progress
 */
class CommonProgressDialog : DialogFragment() {
    private lateinit var viewBinding: CommonDialogProgressBinding
    private lateinit var mAnimation: ObjectAnimator

    companion object {
        fun newInstance(): CommonProgressDialog {
            return CommonProgressDialog()
        }
    }

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
        viewBinding = CommonDialogProgressBinding.inflate(layoutInflater)
        dialog?.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCanceledOnTouchOutside(false)
            window?.apply {
                //去掉DialogFragment内部的背景色
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                //去掉Padding
                decorView.setPadding(0, 0, 0, 0)
            }
        }
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rotate() // run loading
    }

    private fun rotate() {
        mAnimation =
            ObjectAnimator.ofFloat(viewBinding.commonDialogProgressImage, "rotation", 360f).apply {
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ValueAnimator.RESTART
                interpolator = LinearInterpolator()
                duration = 800
            }
        mAnimation.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mAnimation.cancel()
    }

    //显示
    fun showDialog(fragmentManager: FragmentManager) {
        if (isVisible) {
            dismiss()
        }
        show(fragmentManager, "")
    }

    //隐藏
    fun dismissDialog() {
        if (isAdded) {
            dismiss()
        }
    }

}