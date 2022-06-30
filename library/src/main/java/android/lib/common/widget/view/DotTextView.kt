package android.lib.common.widget.view

import android.content.Context
import android.lib.common.R
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView


/**
 * @author: yangkui
 * @Date: 2022/4/20
 * @Description: DotTextView
 */
class DotTextView : FrameLayout {
    private lateinit var textView: TextView
    private var oneDrawableResId = R.drawable.common_shape_dot_oval_bg
    private var moreDrawableResId = R.drawable.common_shape_dot_rectangle_bg

    constructor(context: Context) : super(context) {
        create()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        create()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        create()
    }

    private fun create() {
        textView = LayoutInflater.from(context).inflate(R.layout.common_view_dot, null) as TextView
        this.addView(textView)
    }

    fun setNum(num: Int) {
        if (num < 0) return
        if (num == 0) {
            visibility = View.GONE
            return
        }
        visibility = View.VISIBLE
        var res = if (num < 100) num.toString() else "99+"
        textView.text = res
        if (num < 10) {
            this.setBackgroundResource(oneDrawableResId)
            var dp18 = resources.getDimension(R.dimen.dp_18).toInt()
            this.layoutParams.width = dp18
            setPadding(0, 0, 0, 0)
        } else {
            this.setBackgroundResource(moreDrawableResId)
            this.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            var dp4 = resources.getDimension(R.dimen.dp_4).toInt()
            setPadding(dp4, 0, dp4, 0)
        }
    }

    // 设置背景
    fun setBackgroundDrawableResId(oneDrawableResId: Int, moreDrawableResId: Int) {
        this.oneDrawableResId = oneDrawableResId
        this.moreDrawableResId = moreDrawableResId
    }

    // 设置单数字背景
    fun setOneBackgroundDrawableResId(drawableResId: Int) {
        this.oneDrawableResId = drawableResId
    }

    // 设置多数字背景
    fun setMoreBackgroundDrawableResId(drawableResId: Int) {
        this.moreDrawableResId = drawableResId
    }
}