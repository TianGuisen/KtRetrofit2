package tg.my.core.adapter

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View
import kt.ktRetrofit2.BR.item

abstract class BaseAdapter<E : Any, VB : ViewDataBinding> : RecyclerView.Adapter<BindViewHolder<VB>> {
    internal lateinit var mDatas: MutableList<E>
    internal var itemClickLisener: OnItemClickLisener<E>? = null
    internal var childClickLisener: OnChildClickLisener<E>? = null
    internal var itemLongClickLisener: OnItemLongClickLisener<E>? = null

    internal constructor() {}

    internal constructor(datas: MutableList<E>) {
        mDatas = datas
    }

    fun setChildClickLisener(function: (E, View, Int) -> Unit) {
        this.childClickLisener = object : OnChildClickLisener<E> {
            override fun childClick(bean: E, view: View, position: Int) {
                function(bean, view, position)
            }
        }
    }

    fun setOnItemClickLisener(itemClickLisener: OnItemClickLisener<E>) {
        this.itemClickLisener = itemClickLisener
    }

    fun setOnItemClickLisener(function: (E, View, Int) -> Unit) {
        this.itemClickLisener = object : OnItemClickLisener<E> {
            override fun itemClick(bean: E, view: View, position: Int) {
                function(bean, view, position)
            }
        }
    }

    fun setItemLongClickLisener(function: (E, View, Int) -> Unit) {
        this.itemLongClickLisener = object : OnItemLongClickLisener<E> {
            override fun itemLongClick(bean: E, view: View, position: Int) {
                function(bean, view, position)
            }
        }
    }

    interface OnItemClickLisener<E> {
        fun itemClick(bean: E, view: View, position: Int)
    }

    interface OnChildClickLisener<E> {
        fun childClick(bean: E, view: View, position: Int)
    }

    interface OnItemLongClickLisener<E> {
        fun itemLongClick(bean: E, view: View, position: Int)
    }

    override fun onBindViewHolder(holder: BindViewHolder<VB>, position: Int) {
        val bean = mDatas[position]
        val itemView = holder.binding.getRoot()
        if (itemClickLisener != null) {
            itemView.setOnClickListener {
                itemClickLisener!!.itemClick(bean, holder.binding.getRoot(), position)
            }
        }
        decorator(bean, holder, position)
        holder.binding.setVariable(item, mDatas[position])
        holder.binding.executePendingBindings()
    }

    open fun decorator(bean: E, holder: BindViewHolder<VB>, position: Int) {

    }

    open fun remove(position: Int) {
        mDatas.removeAt(position)
        notifyItemRemoved(position)
    }

    open fun clear() {
        mDatas.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mDatas.size
    }


//    inner class BindViewHolder(itemView: View, val binding: VB) : RecyclerView.ViewHolder(itemView)
}
