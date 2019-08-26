package com.lb.baselib.customView

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lb.baselib.R
import com.lb.baselib.util.AppUtil
import com.lb.baselib.util.TAutoSizeUtil


/**
 * @author 田桂森 2019/6/14
 */
class LineItemDecoration : RecyclerView.ItemDecoration() {
    private val mPaint: Paint = Paint()
    private var leftSpace: Int = 0
    private var rightSpace: Int = 0

    init {
        mPaint.color = AppUtil.getColor(R.color.eee)
        mPaint.strokeWidth = TAutoSizeUtil.pt2px(2f).toFloat()
    }

    fun setDecorationColor(color: Int): LineItemDecoration {
        mPaint.color = color
        return this
    }

    fun setDecorationSize(sizePT: Int): LineItemDecoration {
        mPaint.strokeWidth = TAutoSizeUtil.pt2px(sizePT.toFloat()).toFloat()
        return this
    }

    fun setSpace(leftSpacePT: Int, rightSpacePT: Int): LineItemDecoration {
        leftSpace = TAutoSizeUtil.pt2px(leftSpacePT.toFloat())
        rightSpace = TAutoSizeUtil.pt2px(leftSpacePT.toFloat())
        return this
    }

    //设置ItemView的内嵌偏移长度（inset）
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        //获取view所在的绝对位置
        val position = parent.getChildAdapterPosition(view)
    }

    // 在子视图上设置绘制范围，并绘制内容
    // 绘制图层在ItemView以下，所以如果绘制区域与ItemView区域相重叠，会被遮挡
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
    }

    //同样是绘制内容，但与onDraw（）的区别是：绘制在图层的最上层
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        // 获取RecyclerView当前屏幕Child view的个数
        val childCount = parent.childCount
        // 获取所有Child view的个数
        for (i in 0..childCount - 1) {
            val child = parent.getChildAt(i)
            //获取view所在的绝对位置
            val position = parent.getChildAdapterPosition(child)
            if (position != childCount - 1) {
                c.drawLine(0f + leftSpace, child.bottom.toFloat(), child.right.toFloat()-rightSpace, child.bottom.toFloat(), mPaint)
            } else {
                //是最后一个item
                c.drawLine(0f, child.bottom.toFloat(), child.right.toFloat(), child.bottom.toFloat(), mPaint)
            }
        }
        // 通过Canvas绘制矩形（分割线）
//            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
    }
}