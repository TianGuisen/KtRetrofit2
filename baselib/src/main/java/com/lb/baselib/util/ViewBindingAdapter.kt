package com.lb.baselib.util

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.lb.baselib.customView.BindEditText


/**
 * 可见控制
 */
@BindingAdapter("setVisible")
fun setVisible(view: View, visible: Boolean) {
    if (visible) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("focusAttrChanged")
fun setChangeListener(aView: BindEditText, focusAttrChange: InverseBindingListener) {
    aView.setListener(object : BindEditText.OnFocusChangedListener {
        override fun onChanged() {
            focusAttrChange.onChange()
        }
    })
}

@InverseBindingAdapter(attribute = "focus", event = "focusAttrChanged")
fun isFocus(view: BindEditText): Boolean {
    return view.hasFocus()
}

/**
 * 焦点控制和监听
 * 传入Boolean就是控制
 * 传入ObservableBoolean是控制和监听
 */
@BindingAdapter("focus")
fun setFocus(view: BindEditText, focus: Boolean) {
    if (focus) {
        view.isFocusableInTouchMode = true
        view.requestFocus()
    } else {
        view.clearFocus()
    }
}

/**
 * glide加载网络图片
 */
@BindingAdapter("imageUrl")
fun imageUri(imageView: ImageView, imageUrl: String) {
    Glide.with(imageView.context).load(imageUrl).into(imageView)
}

/**
 * glide加载本地图片
 */
@BindingAdapter("imageUrl")
fun imageUrl(imageView: ImageView, res: Int) {
    Glide.with(imageView.context).load(res).into(imageView)
}

///**
// * 焦点改变监听
// */
//@BindingAdapter("focusChange")
//fun onFocusChangeReply(view: View, onFocusChangeReply: Consumer<Boolean>) {
//    view.onFocusChangeListener = View.OnFocusChangeListener { mV, hasFocus ->
//        onFocusChangeReply.accept(hasFocus)
//    }
//}
//
///**
// * onTouch监听
// */
//@BindingAdapter("touch")
//fun onTouchReply(view: View, onTouchReply: Function<MotionEvent, Boolean>) {
//    view.setOnTouchListener { mV, event ->
//        onTouchReply.apply(event)
//    }
//}
//
///**
// * 刷新控件,可以换成任意其他的
// */
//@BindingAdapter("load")
//fun onLoadReply(smartRefreshLayout: SmartRefreshLayout, onRefreshReply: Consumer<Int>) {
//    smartRefreshLayout.setOnRefreshListener {
//        onRefreshReply.accept(0)
//    }
//    smartRefreshLayout.setOnLoadMoreListener {
//        onRefreshReply.accept(1)
//    }
//}


//@BindingAdapter("checkedChange")
//fun onRadioGroupCheckedChangeReply(radioGroup: RadioGroup, onCheckedReply: Consumer<Int>) {
//    radioGroup.setOnCheckedChangeListener { group, checkedId -> onCheckedReply.accept(checkedId) }
//}