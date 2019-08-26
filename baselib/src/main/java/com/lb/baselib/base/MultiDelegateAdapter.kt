package com.lb.baselib.base

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate


/**
 * @author 田桂森 2019/6/14
 * 多type用法
 */
class MultiDelegateAdapter : BaseQuickAdapter<Entity, BaseViewHolder>(null) {
    val layoutType1 = 0
    val layoutType2 = 0

    init {
        //Step.1
        setMultiTypeDelegate(object : MultiTypeDelegate<Entity>() {
            override fun getItemType(entity: Entity): Int {
                //根据你的实体类来判断布局类型
                return entity.type
            }
        })
        //Step.2
        getMultiTypeDelegate()
                .registerItemType(layoutType1, 0)
                .registerItemType(layoutType2, 0)
    }

    protected override fun convert(helper: BaseViewHolder, entity: Entity) {
        //Step.3
        when (helper.getItemViewType()) {
            layoutType1 -> {
            }
            layoutType2 -> {
            }
        }// do something
        // do something
    }
}


data class Entity(var type: Int, var name: String)