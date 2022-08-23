package android.lib.common.widget.dialog.loading

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.lib.common.base.BaseLoadingDialog
import android.lib.common.databinding.CommonDialogProgressBinding
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator

/**
 * @author: yangkui
 * @Date: 2022/4/13
 * @Description: Common Progress
 */
class ProgressLoadingDialog : BaseLoadingDialog() {
    private lateinit var viewBinding: CommonDialogProgressBinding
    private lateinit var mAnimation: ObjectAnimator

    override fun onCreateView(): View {
        viewBinding = CommonDialogProgressBinding.inflate(layoutInflater)
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
}