package android.lib.common.widget.view

import android.content.Context
import android.graphics.Color
import android.lib.common.R
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

/**
 * @author: yangkui
 * @Date: 2022/4/20
 * @Description: SortView
 */
class SortView : FrameLayout {
    private var textView: TextView? = null
    private var imageView: ImageView? = null
    private var onSortClickListener: OnSortClickListener? = null
    private var status: Int = 0// -1 降序 0 正常 1 升序
    private var textNormalColor: Int = Color.BLACK
    private var textFocusColor: Int = Color.BLACK

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
        var view = LayoutInflater.from(context).inflate(R.layout.common_view_sort, null)
        textView = view.findViewById(R.id.common_tv_view_sort)
        imageView = view.findViewById(R.id.common_iv_view_sort)
        view.setOnClickListener {
            click()
        }
        this.addView(view)
    }

    private fun click() {
        when (status) {
            0 -> {
                status = 1
                imageView?.setImageResource(R.drawable.common_filter_rise)
                textView?.setTextColor(textFocusColor)
            }
            1 -> {
                status = -1
                imageView?.setImageResource(R.drawable.common_filter_drop)
                textView?.setTextColor(textFocusColor)
            }
            -1 -> {
                status = 0
                imageView?.setImageResource(R.drawable.common_filter_normal)
                textView?.setTextColor(textNormalColor)
            }
        }
        onSortClickListener?.onSort(status)
    }

    fun setNormalColor(color: Int) {
        this.textNormalColor = color
        if (status == 0) textView?.setTextColor(color)
    }

    fun setFocusColor(color: Int) {
        this.textFocusColor = color
        if (status != 0) textView?.setTextColor(color)
    }

    // 设置文本
    fun setText(content: String) {
        textView?.text = content
    }

    // 重置状态
    fun resetStatus() {
        status = 0
        imageView?.setImageResource(R.drawable.common_filter_normal)
        textView?.setTextColor(textNormalColor)
    }

    // 设置监听
    fun setOnSortClickListener(onSortClickListener: OnSortClickListener) {
        this.onSortClickListener = onSortClickListener
    }

    interface OnSortClickListener {
        fun onSort(status: Int) // -1 降序 0 正常 1 升序
    }
}