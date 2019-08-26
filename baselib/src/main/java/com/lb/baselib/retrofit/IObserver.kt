package com.lb.baselib.retrofit

import com.jeremyliao.liveeventbus.LiveEventBus
import io.reactivex.observers.DisposableObserver

interface IObserver<T> {
    var tag: String?
    var repeat: Int
    var showToast: Boolean

    fun onFailure(t: ResWrapper<T>?)

    fun onSuccess(t: ResWrapper<T>)

    fun onFinish() {
    }

    fun addTag(observer: DisposableObserver<*>, repeat: Int) {
        if (repeat == 1) {
            ApiTagManager.instance.add1(tag, observer)
        } else if (repeat == 2) {
            ApiTagManager.instance.add2(tag, observer)
        }
    }

    fun removeTag() {
        if (repeat != 0) {
            ApiTagManager.instance.remove(tag)
        }
    }

    /**
     * 检查登录
     */
    fun checkLogin(t: ResWrapper<T>) {
        if (t.code == ResCode.TOKEN_OVERDUE) {
            LiveEventBus.get().with("" + ResCode.TOKEN_OVERDUE).post(null);
        }
    }
}
