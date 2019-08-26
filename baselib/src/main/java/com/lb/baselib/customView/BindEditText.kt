package com.lb.baselib.customView

import android.content.Context
import android.graphics.Rect
import androidx.appcompat.widget.AppCompatEditText
import android.util.AttributeSet

/**
 * Created by 田桂森 on 2017/7/27.
 * 实现了使用ObservableBoolean监听和控制edittext的焦点。
 * 使用editText无法实现这个功能，这是自定义双向绑定.
 * bind:focus="@={vm.?}"
 */

class BindEditText : AppCompatEditText {

    private var onFocusChangedListener: OnFocusChangedListener? = null

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}


    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        onFocusChangedListener?.onChanged()
    }

    fun setListener(onFocusChangedListener: OnFocusChangedListener) {
        this.onFocusChangedListener = onFocusChangedListener
    }

    interface OnFocusChangedListener {
        fun onChanged()
    }
    
}