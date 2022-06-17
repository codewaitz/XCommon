package android.lib.common.utils

import android.content.Context
import android.lib.common.utils.glide.GlideUtil
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

/**
 * @author: yangkui
 * @Date: 2022/4/14
 * @Description: View帮助
 */
object ViewUtil {
    /**
     * Button状态依赖EditText内容是否为空
     */
    fun btnLinkEtEnable(
        button: Button,
        editTexts: MutableList<EditText>
    ) {
        editTexts.forEach {
            it.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    var enable = true
                    for (editText in editTexts) {
                        if (editText.text.isNullOrEmpty()) {
                            enable = false
                            break
                        }
                    }
                    button.isEnabled = enable
                }

                override fun afterTextChanged(p0: Editable?) {
                }
            })
        }
    }

    // 加载ImageView并且匹配高度
    fun loadImageViewMatchHeight(
        context: Context,
        imageView: ImageView?,
        url: String?,
        rateWH: Float
    ) {
        if (imageView == null) return
        var height = 0
        if (imageView.tag == null) {
            height = try {
                NumberUtil.string2Int(imageView.tag as String)
            } catch (ex: Exception) {
                0
            }
        }
        if (height > 0) {
            GlideUtil.load(
                context,
                url,
                imageView
            )
        } else {
            imageView?.post {
                try {
                    if (imageView.measuredWidth > 0) {
                        var height = NumberUtil.float2int(imageView.measuredWidth / rateWH)
                        imageView.layoutParams.height = height
                        imageView.scaleType = ImageView.ScaleType.CENTER
                        imageView.tag = height
                    }
                    GlideUtil.load(
                        context,
                        url,
                        imageView
                    )
                } catch (ex: Exception) {
                }
            }
        }
    }
}