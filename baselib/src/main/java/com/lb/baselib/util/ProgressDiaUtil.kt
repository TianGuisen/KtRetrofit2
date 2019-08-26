package com.lb.baselib.util

import android.content.Context
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.widget.TextView
import com.lb.baselib.R


/**
 * @author 田桂森 2019/5/14
 */
/**
 * 耗时对话框工具类
 */
object ProgressDiaUtil {
    private var progressDia: AlertDialog? = null

    fun show(context: Context, tip: String? = "加载中...") {
        progressDia = AlertDialog.Builder(context, R.style.CustomProgressDialog).create()
        val loadView = LayoutInflater.from(context).inflate(R.layout.dia_custom_progress, null)
        progressDia?.setView(loadView, 0, 0, 0, 0)
        progressDia?.setCanceledOnTouchOutside(false)
        val tvTip = loadView.findViewById<TextView>(R.id.tvTip)
        tvTip.setText(tip)
        progressDia?.show()
    }

    fun show(context: Context) {
        progressDia = AlertDialog.Builder(context, R.style.CustomProgressDialog).create()
        val loadView = LayoutInflater.from(context).inflate(R.layout.dia_custom_progress, null)
        progressDia?.setView(loadView, 0, 0, 0, 0)
        progressDia?.setCanceledOnTouchOutside(false)
        val tvTip = loadView.findViewById<TextView>(R.id.tvTip)
        tvTip.setText("加载中...")
        progressDia?.show()
    }

    /**
     * 隐藏耗时对话框
     */
    fun dismiss() {
        if (progressDia != null && progressDia?.isShowing()!!) {
            progressDia?.dismiss()
        }
    }
}