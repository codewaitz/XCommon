package android.lib.common.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * @author: yangkui
 * @Date: 2022/4/20
 * @Description:
 */
abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseVH<T>> {
    protected var context: Context
    private var mLayoutInflater: LayoutInflater? = null
    var onItemClickListener: OnItemClickListener<T>? = null
    var list: MutableList<T>

    constructor(context: Context, list: MutableList<T>) {
        mLayoutInflater = LayoutInflater.from(context)
        this.context = context
        this.list = list
    }

    open fun refresh(list: MutableList<T>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    protected fun inflateView(resource: Int, parent: ViewGroup): View {
        return mLayoutInflater!!.inflate(
            resource,
            parent,
            false
        )
    }

    protected abstract fun createVH(parent: ViewGroup, viewType: Int): BaseVH<T>

    open fun putOnItemClickListener(holder: BaseVH<T>, position: Int) {
        holder.itemView.setOnClickListener {
            var t = list[position]
            if (onItemClickListener != null && t != null) onItemClickListener?.onItemClick(
                position,
                t
            )
        }
    }

    interface OnItemClickListener<T> {
        fun onItemClick(position: Int, t: T)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVH<T> {
        return createVH(parent, viewType)
    }

    override fun onBindViewHolder(holder: BaseVH<T>, position: Int) {
        holder.bindHolder(context, list[position])
        holder.bindHolder(context, list[position], position)
        putOnItemClickListener(holder, position)
    }
}