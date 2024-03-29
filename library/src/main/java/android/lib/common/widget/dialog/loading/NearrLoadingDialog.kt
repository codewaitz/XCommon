package android.lib.common.widget.dialog.loading

import android.lib.common.base.BaseLoadingDialog
import android.lib.common.databinding.CommonDialogLoadingNearrBinding
import android.os.Bundle
import android.view.View
import pl.droidsonroids.gif.GifDrawable

/**
 * @author: yangkui
 * @Date: 2022/4/13
 * @Description: loading
 */
class NearrLoadingDialog : BaseLoadingDialog() {
    private lateinit var viewBinding: CommonDialogLoadingNearrBinding

    override fun onCreateView(): View {
        viewBinding = CommonDialogLoadingNearrBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            val gifDrawable = viewBinding.commonDialogLoadingImage.drawable as GifDrawable
            gifDrawable.setSpeed(5f)
            gifDrawable.loopCount = 0
        } catch (ex: Exception) {
        }
    }

    override fun dismissDialog() {
        try {
            (viewBinding.commonDialogLoadingImage.drawable as GifDrawable).stop()
        } catch (ex: Exception) {
        }
        super.dismissDialog()
    }
}