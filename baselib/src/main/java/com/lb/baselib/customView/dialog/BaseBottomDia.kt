package com.lb.baselib.customView.dialog

import android.graphics.Color
import android.os.Bundle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lb.baselib.R


open class BaseBottomDia : BottomSheetDialogFragment() {

    private var height = 0
    fun getHeight() = height
    fun setHeight(height: Int): BaseBottomDia {
        this.height = height
        return this
    }

    private var mBottomSheetBehavior: BottomSheetBehavior<View>? = null
    private val mBottomSheetBehaviorCallback = object : BottomSheetBehavior.BottomSheetCallback() {

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            //禁止拖拽，
            if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                //设置为收缩状态
                mBottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }

    /**
     * 如果没有调用setHight()方法去设置高度,那么必须要有id为content的布局用来测量高度,参考dia_base_bottom.xml
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dia_base_bottom, null, false)
    }

    override fun onStart() {
        super.onStart()
        val view = view
        if (view != null) {
            view.post {
                val parent = view.parent as View
                val params = parent.layoutParams as CoordinatorLayout.LayoutParams
                val behavior = params.behavior
                mBottomSheetBehavior = behavior as BottomSheetBehavior<View>
                mBottomSheetBehavior!!.setBottomSheetCallback(mBottomSheetBehaviorCallback)
                //设置高度
                if (height == 0) {
                    val contentView = view.findViewById<View>(R.id.content)
                    if (contentView != null) {
                        mBottomSheetBehavior!!.peekHeight = contentView.height
                    }
                } else {
                    mBottomSheetBehavior!!.peekHeight = height
                }
                parent.setBackgroundColor(Color.TRANSPARENT)
            }
        }
    }


}