package android.lib.common.base

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @author: yangkui
 * @Date: 2022/4/20
 * @Description:BaseVH
 */
abstract class BaseVH<B>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bindHolder(context: Context, t: B?)

    open fun bindHolder(context: Context, t: B?, position: Int) {}

    protected fun <V : View> findView(intRes: Int): V {
        return itemView.findViewById(intRes)
    }

    protected fun <V : View> findEmptyView(intRes: Int): V? {
        return itemView.findViewById(intRes)
    }
}