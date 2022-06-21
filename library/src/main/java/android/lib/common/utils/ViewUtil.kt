package android.lib.common.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText

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
}