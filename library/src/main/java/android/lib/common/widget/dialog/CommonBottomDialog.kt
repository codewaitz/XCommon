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
import android.widget.RelativeLayout
import android.widget.TextView
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView

/**
 * @author: yangkui
 * @Date: 2022/4/27
 * @Description:BaseBottomDialog
 */
abstract class CommonBottomDialog : BaseDialog {
    private var title: String? = ""
    private var tvTitle: TextView? = null
    private var rlLoading: RelativeLayout? = null
    private var givLoading: GifImageView? = null
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
        prepareCreate()
        setContentView(R.layout.common_bottom_dialog)
        rlLoading = findViewById(R.id.rl_loading)
        givLoading = findViewById(R.id.giv_loading)
        findViewById<ImageView>(R.id.common_bottom_dialog_close).setOnClickListener {
            dismiss()
        }
        tvTitle = findViewById(R.id.common_bottom_dialog_title)
        setTitleText()
        findViewById<LinearLayout>(R.id.common_bottom_dialog_content).addView(
            LayoutInflater.from(context).inflate(
                getLayoutRes(),
                null
            )
        )
        setTitle(title)
        onCreate()
    }

    private fun setTitleText() {
        if (StringUtil.isEmpty(title)) {
            tvTitle?.visibility = View.GONE
        } else {
            tvTitle?.text = title
            tvTitle?.visibility = View.VISIBLE
        }
    }

    protected fun setTitle(title: String?) {
        this.title = title
        setTitleText()
    }

    override fun show() {
        super.show()
        val layoutParams = window!!.attributes
        layoutParams.gravity = Gravity.BOTTOM
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        window!!.attributes = layoutParams
    }

    protected fun showLoading() {
        if (!isShowing) return
        try {
            rlLoading?.visibility = View.VISIBLE
            val gifDrawable = givLoading?.drawable as GifDrawable
            gifDrawable.setSpeed(5f)
            gifDrawable.loopCount = 0
        } catch (ex: Exception) {
        }
    }

    protected fun hideLoading() {
        if (!isShowing) return
        rlLoading?.visibility = View.GONE
    }

    protected abstract fun getLayoutRes(): Int

    protected open fun prepareCreate() {}

    protected abstract fun onCreate()
}