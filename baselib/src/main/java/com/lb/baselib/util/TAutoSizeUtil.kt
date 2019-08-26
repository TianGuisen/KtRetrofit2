package com.lb.baselib.util

import android.util.TypedValue
import com.lb.baselib.ToolApplication.appContext

/**
 * @author 田桂森 2019/6/17
 */
class TAutoSizeUtil private constructor() {

    init {
        throw IllegalStateException("you can't instantiate me!")
    }

    companion object {

        fun dp2px(value: Float): Int {
            return (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, appContext.resources.displayMetrics) + 0.5f).toInt()
        }

        fun sp2px(value: Float): Int {
            return (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, appContext.resources.displayMetrics) + 0.5f).toInt()
        }

        fun pt2px(value: Float): Int {
            return (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, value, appContext.resources.displayMetrics) + 0.5f).toInt()
        }

        fun in2px(value: Float): Int {
            return (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_IN, value, appContext.resources.displayMetrics) + 0.5f).toInt()
        }

        fun mm2px(value: Float): Int {
            return (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, value, appContext.resources.displayMetrics) + 0.5f).toInt()
        }

        fun px2dp(px: Double): Double {
            val density = appContext.getResources().getDisplayMetrics().density
            return (px / density + 0.5)
        }
    }
}