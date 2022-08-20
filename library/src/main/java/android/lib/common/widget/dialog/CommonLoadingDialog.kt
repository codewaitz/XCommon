package android.lib.common.widget.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.lib.common.R
import android.lib.common.databinding.CommonDialogLoadingBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 * @author: yangkui
 * @Date: 2022/4/13
 * @Description: Common Progress
 */
class CommonLoadingDialog : DialogFragment() {
    private lateinit var viewBinding: CommonDialogLoadingBinding

    companion object {
        fun newInstance(): CommonLoadingDialog {
            return CommonLoadingDialog()
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
        viewBinding = CommonDialogLoadingBinding.inflate(layoutInflater)
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
        Glide.with(requireContext()).asGif().load(R.drawable.gif_loading)
            .diskCacheStrategy(DiskCacheStrategy.NONE).into(viewBinding.commonDialogLoadingImage)
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