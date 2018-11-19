package kt.ktRetrofit2.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.ttsea.jrxbus2.RxBus2
import io.reactivex.observers.DisposableObserver
import kt.ktRetrofit2.KtApplication.Companion.appContext

/**
 * @Package tg.my.core.retrofit
 * @Title:  IObserver
 * @Description:
 * Copyright (c) 传化公路港物流有限公司版权所有
 * Create DateTime: 2018/11/14
 * @author 田桂森 2018/11/14
 */
interface IObserver<T> {
    var tag: String?
    var canRepeat: Boolean
    var showToast: Boolean

    fun onSuccess(data: T?, code: Int, msg: String?, tag: Any?)


    fun onFailure(data: T?, code: Int, msg: String?, tag: Any?) {
    }

    fun onFinish() {
    }

    fun addTag(observer: DisposableObserver<ResWrapper<T>>) {
        if (!canRepeat && tag != null) {
            ApiTagManager.instance.add(tag!!, observer)
        }
    }

    fun removeTag() {
        if (!canRepeat && tag != null) {
            ApiTagManager.instance.remove(tag!!)
        }
    }
    /**
     * 网络是否链接
     */
    fun isConnected(): Boolean {
        (appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo.also {
            if (null != it && it.isConnected) {
                if (it.state == NetworkInfo.State.CONNECTED) {
                    return true
                }
            }
        }
        return false
    }
    /**
     * 检查登录
     */
    fun checkLogin(t: ResWrapper<T>) {
        if (t.code == ResCode.TOKEN_OVERDUE) {
            RxBus2.getInstance().post(ResCode.TOKEN_OVERDUE, t)
        }
    }
}
