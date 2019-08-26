package com.lb.baselib.util

import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

/**
 * textview添加中划线
 */
fun TextView.addStrike() {
    val sp = SpannableString(this.getText().toString())
    sp.setSpan(RelativeSizeSpan(0.5f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)  //0.5f表示
    this.setText(sp)
}

fun EditText.isEmpty(): Boolean {
    return this.text.isEmpty()
}

val EditText.string get() = this.text.toString()


val TextView.string get() = this.text.toString()


fun EditText.addTextChangedListener(action: TextWatcherImp.() -> Unit) {
    val apply = TextWatcherImp().apply(action)
    val textWatcherImp = TextWatcherImp()
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            textWatcherImp.afterTextChanged?.invoke(s)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            textWatcherImp.beforeTextChanged?.invoke(s, start, count, after)
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            textWatcherImp.onTextChanged?.invoke(s, start, before, count)
        }
    })
}

class TextWatcherImp {
    var afterTextChanged: ((s: Editable?) -> Unit)? = null
    var beforeTextChanged: ((s: CharSequence?, start: Int, count: Int, after: Int) -> Unit)? = null
    var onTextChanged: ((s: CharSequence?, start: Int, before: Int, count: Int) -> Unit)? = null
}

/**
 * 双击间隔时间
 */
const val INTERVAL = 400

inline fun View.setDoubleClickLis(crossinline function: (View) -> Unit): View {
    var temTime: Long = 0
    this.setOnClickListener {
        if (System.currentTimeMillis() - temTime > INTERVAL) {
            temTime = System.currentTimeMillis()
            function(it)
        }
    }
    return this
}

inline fun doubleClick(crossinline function: (View) -> Unit): View.OnClickListener {
    var temTime: Long = 0
    return object : View.OnClickListener {
        override fun onClick(v: View) {
            if (System.currentTimeMillis() - temTime > INTERVAL) {
                temTime = System.currentTimeMillis()
                function(v)
            }
        }
    }
}


inline fun refreshLis(crossinline refreshLayout: (RefreshLayout) -> Unit): OnRefreshListener {
    return object : OnRefreshListener {
        override fun onRefresh(refreshLayout: RefreshLayout) {
            refreshLayout(refreshLayout)
        }
    }
}

inline fun loadMoreLis(crossinline refreshLayout: (RefreshLayout) -> Unit): OnLoadMoreListener {
    return object : OnLoadMoreListener {
        override fun onLoadMore(refreshLayout: RefreshLayout) {
            refreshLayout(refreshLayout)
        }
    }
}





