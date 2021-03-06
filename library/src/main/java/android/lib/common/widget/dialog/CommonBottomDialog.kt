package android.lib.common.widget.dialog

import android.content.Context
import android.lib.common.R
import android.lib.common.base.BaseDialog
import android.lib.common.utils.StringUtil
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

/**
 * @author: yangkui
 * @Date: 2022/4/27
 * @Description:BaseBottomDialog
 */
abstract class CommonBottomDialog : BaseDialog {
    private var title: String? = ""
    var inflater: LayoutInflater

    constructor(context: Context) : this(context, null)
    constructor(context: Context, title: String?) : super(context, R.style.BottomDialog) {
        this.title = title
        inflater = LayoutInflater.from(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCanceledOnTouchOutside(true)
        window?.setGravity(Gravity.BOTTOM)
        setContentView(R.layout.common_bottom_dialog)
        findViewById<ImageView>(R.id.common_bottom_dialog_close).setOnClickListener {
            dismiss()
        }
        var textView = findViewById<TextView>(R.id.common_bottom_dialog_title)
        if (StringUtil.isEmpty(title)) {
            textView.visibility = View.GONE
        } else {
            textView.text = title
            textView.visibility = View.VISIBLE
        }
        findViewById<LinearLayout>(R.id.common_bottom_dialog_content).addView(
            LayoutInflater.from(context).inflate(
                getLayoutRes(),
                null
            )
        )
        onCreate()
    }

    abstract fun getLayoutRes(): Int

    abstract fun onCreate()

    override fun show() {
        super.show()
        val layoutParams = window!!.attributes
        layoutParams.gravity = Gravity.BOTTOM
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        window!!.attributes = layoutParams
    }
}