package android.lib.common.widget.dialog

import android.content.Context
import android.content.DialogInterface
import android.lib.common.R
import android.lib.common.base.BaseApplication
import android.lib.common.base.BaseDialog
import android.lib.common.utils.StringUtil
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView

/**
 * @author: yangkui
 * @Date: 2022/4/18
 * @Description: CommonWarnDialog
 */
class CommonWarnDialog : BaseDialog {

    companion object { // 全局部分
        private var staticPositiveTextColor = 0
        private var staticNegativeTextColor = 0

        // 设置文本颜色
        fun staticTextColor(positiveTextColor: Int, negativeTextColor: Int) {
            staticPositiveTextColor = positiveTextColor
            staticNegativeTextColor = negativeTextColor
        }
    }

    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, theme: Int) : super(context!!, theme)

    class Builder(private val context: Context) {
        private var content: String? = null
        private var ok: String? = null
        private var cancel: String? = null
        private var title: String? = null
        private var okColor: Int = BaseApplication.mainColor
        private var cancelColor: Int = 0
        private var positiveButtonClickListener: DialogInterface.OnClickListener? = null
        private var negativeButtonClickListener: DialogInterface.OnClickListener? = null

        fun setContent(text: String): Builder {
            this.content = text
            return this
        }

        fun setPositiveText(text: String): Builder {
            this.ok = text
            return this
        }

        fun setPositiveTextColor(color: Int): Builder {
            this.okColor = color
            return this
        }

        fun setNegativeText(text: String): Builder {
            this.cancel = text
            return this
        }

        fun setNegativeTextColor(color: Int): Builder {
            this.cancelColor = color
            return this
        }

        fun setPositiveButton(listener: DialogInterface.OnClickListener): Builder {
            this.positiveButtonClickListener = listener
            return this
        }

        fun setNegativeButton(listener: DialogInterface.OnClickListener): Builder {
            this.negativeButtonClickListener = listener
            return this
        }

        fun setTitle(text: String): Builder {
            this.title = text
            return this
        }

        fun create(): CommonWarnDialog {
            val layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            //为自定义弹框设置主题
            val customDialog = CommonWarnDialog(context, R.style.CommonDialog)
            var view = layoutInflater.inflate(R.layout.common_dialog_warn, null)
            if (!StringUtil.isEmpty(title)) view =
                layoutInflater.inflate(R.layout.common_dialog_warn_title, null)
            customDialog.addContentView(
                view, LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            )
            //设置弹框文本
            title?.let {
                var textView = view.findViewById(R.id.common_warn_dialog_title) as TextView
                if (StringUtil.isEmpty(it)) {
                    textView.visibility = View.GONE
                } else {
                    textView.text = it
                    textView.visibility = View.VISIBLE
                }
            }
            content?.let {
                (view.findViewById(R.id.common_warn_dialog_content) as TextView).text = it
            }
            ok?.let {
                (view.findViewById(R.id.common_warn_dialog_sure) as TextView).text = it
            }
            cancel?.let {
                (view.findViewById(R.id.common_warn_dialog_cancel) as TextView).text = it
            }
            // 设置颜色
            var color = 0
            if (okColor != 0) {
                color = okColor
            } else {
                if (staticPositiveTextColor != 0) {
                    color = staticPositiveTextColor
                }
            }
            if (color != 0) {
                (view.findViewById(R.id.common_warn_dialog_sure) as TextView).setTextColor(color)
            }
            color = 0
            if (okColor != 0) {
                color = cancelColor
            } else {
                if (staticNegativeTextColor != 0) {
                    color = staticNegativeTextColor
                }
            }
            if (color != 0) {
                (view.findViewById(R.id.common_warn_dialog_cancel) as TextView).setTextColor(
                    color
                )
            }
            //设置按钮监听
            positiveButtonClickListener?.let {
                (view.findViewById(R.id.common_warn_dialog_sure) as TextView).setOnClickListener {
                    positiveButtonClickListener!!.onClick(
                        customDialog,
                        DialogInterface.BUTTON_POSITIVE
                    )
                }
            } ?: run {
                (view.findViewById(R.id.common_warn_dialog_sure) as TextView).visibility = View.GONE
                (view.findViewById(R.id.common_fl_warn_dialog_sure) as FrameLayout).visibility =
                    View.GONE
            }
            negativeButtonClickListener?.let {
                (view.findViewById(R.id.common_warn_dialog_cancel) as TextView).setOnClickListener {
                    negativeButtonClickListener!!.onClick(
                        customDialog,
                        DialogInterface.BUTTON_NEGATIVE
                    )
                }
            } ?: run {
                (view.findViewById(R.id.common_warn_dialog_cancel) as TextView).visibility =
                    View.GONE
                (view.findViewById(R.id.common_fl_warn_dialog_cancel) as FrameLayout).visibility =
                    View.GONE
            }
            customDialog.setContentView(view)
            return customDialog
        }
    }
}
