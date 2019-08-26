package com.lb.baselib.customView.dialog


import android.util.SparseArray
import android.view.View
import android.widget.TextView

/**
 * @author 田桂森 2019/5/14
 */

@Suppress("UNCHECKED_CAST")
class ViewHolder private constructor(private val convertView: View) {
    private val views: SparseArray<View> = SparseArray()

    fun <T : View> getView(viewId: Int): T {
        var view: View? = views.get(viewId)
        if (view == null) {
            view = convertView.findViewById(viewId)
            views.put(viewId, view)
        }
        return view as T
    }

    companion object {

        fun create(view: View): ViewHolder {
            return ViewHolder(view)
        }
    }
}

fun ViewHolder.setText(viewId: Int, textId: Int) {
    val textView = getView<TextView>(viewId)
    textView.setText(textId)
}

fun ViewHolder.setText(viewId: Int, text: CharSequence) {
    val textView = getView<TextView>(viewId)
    textView.text = text
}

fun ViewHolder.setTextColor(viewId: Int, colorId: Int) {
    val textView = getView<TextView>(viewId)
    textView.setTextColor(colorId)
}

fun ViewHolder.setOnClickListener(viewId: Int, clickListener: View.OnClickListener?) {
    val view = getView<View>(viewId)
    view.setOnClickListener(clickListener)
}

fun ViewHolder.setBackgroundResource(viewId: Int, resId: Int) {
    val view = getView<View>(viewId)
    view.setBackgroundResource(resId)
}

fun ViewHolder.setBackgroundColor(viewId: Int, colorId: Int) {
    val view = getView<View>(viewId)
    view.setBackgroundColor(colorId)
}