package tg.my.core.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by 田桂森 on 2017/4/14.
 * 单type的adapter
 */
open class SigleBaseAdapter<E : Any, VB : ViewDataBinding> : BaseAdapter<E, VB> {

    private var layoutId: Int = 0

//    private constructor(datas: MutableList<E>) : super(datas) {}

    constructor(datas: MutableList<E>, layoutId: Int) : super(datas) {
        this.layoutId = layoutId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindViewHolder<VB> {
        val binding = DataBindingUtil.inflate<VB>(LayoutInflater.from(parent.context), layoutId, parent, false)
        return BindViewHolder(binding.root, binding)
    }

    fun add(data: E) {
        mDatas.add(data)
        notifyDataSetChanged()
    }

    fun add(position: Int, data: E) {
        mDatas.add(position, data)
        notifyDataSetChanged()
    }

    fun set(data: List<E>) {
        mDatas.clear()
        addAll(data)
    }

    fun addAll(data: List<E>) {
        mDatas.addAll(data)
        notifyDataSetChanged()
    }
}
