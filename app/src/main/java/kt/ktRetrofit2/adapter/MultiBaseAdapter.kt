package tg.my.core.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.ViewGroup


/**
 * Created by 田桂森 on 2017/4/14.
 * 多种type的adapter
 */

open class MultiBaseAdapter internal constructor(itemTypeLayoutMap: Map<Int, Int>) : BaseAdapter<Any, ViewDataBinding>() {
    /**
     * key:viewType,value:xml
     */
    internal var mItemTypeLayoutMap = mutableMapOf<Int, Int>()
    /**
     * 每个position对应一个viewType
     */
    internal var mResLayout = mutableListOf<Int>()

    init {
        mDatas = mutableListOf()
        if (!itemTypeLayoutMap.isEmpty()) {
            mItemTypeLayoutMap.putAll(itemTypeLayoutMap)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindViewHolder<ViewDataBinding> {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), mItemTypeLayoutMap.getValue(viewType), parent, false)
        onCreateViewHolderDecorate(binding, viewType)
        return BindViewHolder(binding.root, binding)
    }

    open fun onCreateViewHolderDecorate(view: ViewDataBinding, viewType: Int) {

    }

    override fun getItemViewType(position: Int): Int {
        return mResLayout[position]
    }

    fun addViewTypeLayoutMap(viewType: Int, layoutRes: Int) {
        mItemTypeLayoutMap[viewType] = layoutRes
    }

    fun set(viewType: Int, datas: MutableList<out Any>) {
        this.mDatas.clear()
        mResLayout.clear()
        addAll(viewType, datas)
    }

    fun add(viewType: Int, data: Any) {
        mDatas.add(data)
        mResLayout.add(viewType)
        notifyItemInserted(0)
    }

    fun add(position: Int, viewType: Int, data: Any) {
        mDatas.add(position, data)
        mResLayout.add(position, viewType)
        notifyItemInserted(position)
    }

    fun addAll(viewType: Int, datas: MutableList<out Any>) {
        this.mDatas.addAll(datas)
        for (i in datas.indices) {
            mResLayout.add(viewType)
        }
        notifyDataSetChanged()
    }

    fun addAll(position: Int, viewType: Int, datas: MutableList<out Any>) {
        this.mDatas.addAll(position, datas)
        for (i in datas.indices) {
            mResLayout.add(position + i, viewType)
        }
        notifyItemRangeChanged(position, datas.size - position)
    }


    override fun remove(position: Int) {
        mResLayout.removeAt(position)
        super.remove(position)
    }

    override fun clear() {
        mResLayout.clear()
        super.clear()
    }
}
