package android.lib.common.widget.dialog.loading

import android.lib.common.base.BaseLoadingDialog
import android.lib.common.databinding.CommonDialogLoadingBinding
import android.os.Bundle
import android.view.View
import pl.droidsonroids.gif.GifDrawable

/**
 * @author: yangkui
 * @Date: 2022/4/13
 * @Description: Common Progress
 */
class NearrLoadingDialog : BaseLoadingDialog() {
    private lateinit var viewBinding: CommonDialogLoadingBinding

    override fun onCreateView(): View {
        viewBinding = CommonDialogLoadingBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            val gifDrawable = viewBinding.commonDialogLoadingImage.drawable as GifDrawable
            gifDrawable.setSpeed(5f)
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