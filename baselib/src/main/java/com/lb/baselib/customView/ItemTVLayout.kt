package com.lb.baselib.customView

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.lb.baselib.R
import com.lb.baselib.util.AppUtil
import com.lb.baselib.util.TAutoSizeUtil

/**
 * @author 田桂森 2018/5/16
 */
class ItemTVLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {
    private var mIvLeft: ImageView? = null
    private var mView: View? = null
    private var mIvRight: ImageView? = null
    private var mTvLeft: TextView? = null
    private var mTvRight: TextView? = null
    private var line: View? = null

    var leftText: String
        get() = mTvLeft!!.text.toString()
        set(text) {
            mTvLeft!!.text = text
        }

    var rightText: String
        get() = mTvRight!!.text.toString()
        set(text) {
            mTvRight!!.text = text
        }

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        mView = View.inflate(context, R.layout.item_tv_layout, null)
        mIvLeft = mView!!.findViewById<View>(R.id.iv_left) as ImageView
        mTvLeft = mView!!.findViewById<View>(R.id.tv_left) as TextView
        mIvRight = mView!!.findViewById<View>(R.id.iv_right) as ImageView
        mTvRight = mView!!.findViewById<View>(R.id.tv_right) as TextView
        line = mView!!.findViewById(R.id.line) as View

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemTVLayout)

        val leftText = typedArray.getText(R.styleable.ItemTVLayout_left_text)
        val rightText = typedArray.getText(R.styleable.ItemTVLayout_right_text)

        val leftTextSize = TAutoSizeUtil.px2dp(typedArray.getDimension(R.styleable.ItemTVLayout_left_text_size, mTvLeft!!.textSize).toDouble()) - 0.5f
        val rightTextSize = TAutoSizeUtil.px2dp(typedArray.getDimension(R.styleable.ItemTVLayout_right_text_size, mTvRight!!.textSize).toDouble()) - 0.5f

        val leftTextColor = typedArray.getColor(R.styleable.ItemTVLayout_left_text_color, LEFT_COLOR)
        val rightTextColor = typedArray.getColor(R.styleable.ItemTVLayout_right_text_color, RIGHT_COLOR)

        val lefImg = typedArray.getResourceId(R.styleable.ItemTVLayout_left_img, -1)
        val righImg = typedArray.getResourceId(R.styleable.ItemTVLayout_right_img, -1)
        val showLine = typedArray.getBoolean(R.styleable.ItemTVLayout_show_line, true)
        typedArray.recycle()

        if (lefImg != -1) {
            mIvLeft!!.setImageResource(lefImg)
            mIvLeft!!.visibility = View.VISIBLE
        }
        if (righImg != -1) {
            val layoutParams = mTvRight!!.layoutParams as LinearLayout.LayoutParams
            layoutParams.rightMargin = 30
            mTvRight!!.layoutParams = layoutParams
            mIvRight!!.setImageResource(righImg)
            mIvRight!!.visibility = View.VISIBLE
        }

        mTvLeft!!.text = leftText
        mTvLeft!!.textSize = leftTextSize.toFloat()
        mTvLeft!!.setTextColor(leftTextColor)

        mTvRight!!.text = rightText
        mTvRight!!.textSize = rightTextSize.toFloat()
        mTvRight!!.setTextColor(rightTextColor)

        line!!.visibility = if (showLine) View.VISIBLE else View.INVISIBLE
        addView(mView)
    }

    fun setLeftTextColor(color: Int) {
        mTvLeft!!.setTextColor(AppUtil.getColor(color))
    }

    fun setRightTextColor(color: Int) {
        mTvRight!!.setTextColor(AppUtil.getColor(color))
    }

    companion object {
        private val LEFT_COLOR = AppUtil.getColor(R.color.g66)
        private val RIGHT_COLOR = AppUtil.getColor(R.color.b333)
        private val TEXT_SIZE = 15f
    }
}
