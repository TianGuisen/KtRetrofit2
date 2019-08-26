package com.lb.baselib.util

import android.content.Context
import com.lb.baselib.customView.LoadView
import com.lb.baselib.retrofit.LVObserver
import com.lb.baselib.retrofit.NormalObserver
import com.lb.baselib.retrofit.ResWrapper
import com.lb.baselib.retrofit.ZipObserver
import io.reactivex.Observable

/**
 * @author 田桂森 2019/6/26
 */

fun <T> Observable<ResWrapper<T>>.normalSub(
        onSuccess: ((ResWrapper<T>) -> Unit)? = null,
        onFailure: ((ResWrapper<T>?) -> Unit)? = null,
        onFinish: (() -> Unit)? = null,
        context: Context? = null,//传入表示显示dialog
        showToast: Boolean = true) {
    val ob = object : NormalObserver<T>(context, showToast) {
        override fun onSuccess(t: ResWrapper<T>) {
            onSuccess?.let { onSuccess(t) }
        }

        override fun onFailure(t: ResWrapper<T>?) {
            onFailure?.let { onFailure(t) }
        }

        override fun onFinish() {
            onFinish?.let { onFinish() }
        }
    }
    subscribe(ob)
}

fun <T> Observable<ResWrapper<T>>.lvSub(onSuccess: (ResWrapper<T>) -> Unit) {
    val ob = object : LVObserver<T>() {
        override fun onSuccess(t: ResWrapper<T>) {
            onSuccess(t)
        }

        override fun onFailure(t: ResWrapper<T>?) {
        }
    }
    subscribe(ob)
}

fun <T> Observable<ResWrapper<T>>.lvSub(onSuccess: ((ResWrapper<T>) -> Unit)? = null,
                                        onFailure: ((ResWrapper<T>?) -> Unit)? = null,
                                        loadView: LoadView? = null,//传入表示显示dialog
                                        page: Int = 1,
                                        showToast: Boolean = true) {
    val ob = object : LVObserver<T>(loadView, page, showToast) {
        override fun onSuccess(t: ResWrapper<T>) {
            onSuccess?.let { onSuccess(t) }
        }

        override fun onFailure(t: ResWrapper<T>?) {
            onFailure?.let { onFailure(t) }
        }
    }
    subscribe(ob)
}

fun Observable<MutableList<Any>>.zipSub(onSuccess: (MutableList<Any>) -> Unit, onFailure: (ResWrapper<Any>?) -> Unit) {
    val ob = object : ZipObserver<Any>() {
        override fun onSuccess(data: MutableList<Any>) {
            onSuccess(data)
        }

        override fun onFailure(t: ResWrapper<Any>?) {
            onFailure(t)
        }

    }
    subscribe(ob)
}