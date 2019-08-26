package com.lb.baselib.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.lb.baselib.R

class TitleBar : FrameLayout {
    lateinit var view: View
    private val tv_title: TextView by lazy { view.findViewById<TextView>(R.id.tv_title) }
    private val iv_back: ImageView by lazy { view.findViewById<ImageView>(R.id.iv_back) }
    private val iv_right: ImageView by lazy { view.findViewById<ImageView>(R.id.iv_right) }
    private val tv_right: TextView by lazy { view.findViewById<TextView>(R.id.tv_right) }

    constructor(context: Context) : super(context, null) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        view = LayoutInflater.from(context).inflate(R.layout.view_title2, this)
    }

    fun setTitle(string: String): TextView {
        tv_title.text = string
        return tv_title
    }

    fun setTitleColor(color: Int) {
        tv_title.setTextColor(color)
    }

    /**
     * 左图标
     *
     * @param id
     */
    fun setLeftImageSrc(id: Int): TitleBar {
        iv_back.visibility = View.VISIBLE
        iv_back.setImageResource(id)
        return this
    }

    fun setRightImageSrc(id: Int): TitleBar {
        iv_right.visibility = View.VISIBLE
        iv_right.setImageResource(id)
        return this
    }

    fun setRightText(text: String): TitleBar {
        tv_right.visibility = View.VISIBLE
        tv_right.text = text
        return this
    }

    fun setOnLeftImageListener(listener: View.OnClickListener) {
        iv_back.setOnClickListener(listener)
    }

    fun setOnLeftImageListener(listener: () -> Unit) {
        iv_back.setOnClickListener { listener() }
    }

    fun setOnRightImageListener(listener: View.OnClickListener) {
        iv_right.setOnClickListener(listener)
    }

    fun setRightClick(listener: View.OnClickListener) {
        tv_right.setOnClickListener(listener)
    }

    fun setRightClick(text: String? = null, @DrawableRes res: Int? = null, listener: () -> Unit) {
        if (text != null) {
            tv_right.visibility = View.VISIBLE
            tv_right.text = text
            tv_right.setOnClickListener { listener() }
        }
        if (res != null) {
            iv_right.visibility = View.VISIBLE
            iv_right.setImageResource(id)
            iv_right.setOnClickListener { listener() }
        }
    }

    fun hideBack() {
        iv_back.visibility = View.INVISIBLE
    }
}
