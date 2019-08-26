package com.lb.baselib.retrofit

import android.content.Context
import android.os.Looper
import com.lb.baselib.AppConfig
import com.lb.baselib.util.ProgressDiaUtil
import com.lb.baselib.util.ToastUtil
import com.lb.baselib.util.yes
import com.orhanobut.logger.Logger
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 网络请回调处理
 */
abstract class NormalObserver<T> : DisposableObserver<ResWrapper<T>>, IObserver<T> {
    private var context: Context? = null
    override var tag: String? = null
    override var showToast: Boolean = true
    override var repeat = 0

    /**
     *@loading 是否显示loading
     *@showToast 是否显示错误toast
     *@tag: 请求标记,传入url
     *@repeat 重复请求策略,默认0,1和2时必须要传入tag
     *        0:允许重复请求,场景:viewpager多个页面的请求一样但是参数不同,可能短时间内请求多个页面数据
     *        1:关闭后入队的请求,比较常用:比如按钮的防重复时间是500,但是点击按钮后后台处理时间长达1000,在后500时间内按钮是可点击的但是请求是无意义的
     *        2:关闭先入队的请求,场景很少:频繁调用接口并只以最后一次的数据为准,出现这种情况通常设计不合理
     */
    constructor(context: Context? = null, showToast: Boolean = true, tag: String? = null, repeat: Int = 0) {
        this.tag = tag
        this.context = context
        this.showToast = showToast
        this.repeat = repeat
    }

    constructor(showToast: Boolean = true) {
        this.showToast = showToast
    }

    override fun onStart() {
        addTag(this, repeat)
        if (context != null) {
            ProgressDiaUtil.show(context!!)
        }
    }

    final override fun onComplete() {
        removeTag()
        if (context != null) {
            ProgressDiaUtil.dismiss()
        }
        onFinish()
    }

    final override fun onError(e: Throwable) {
        e.message?.let { Logger.t(AppConfig.LOGGER_NET_TAG).e(it, e) }
        removeTag()
        val apiError = if (e is HttpException) { //连接成功但后台返回错误状态码
            when (e.code()) {
                ApiErrorType.INTERNAL_SERVER_ERROR.code ->
                    ApiErrorType.INTERNAL_SERVER_ERROR
                ApiErrorType.BAD_GATEWAY.code ->
                    ApiErrorType.BAD_GATEWAY
                ApiErrorType.NOT_FOUND.code ->
                    ApiErrorType.NOT_FOUND
                else -> ApiErrorType.UNEXPECTED_ERROR
            }
        } else {
            when (e) {//发送网络问题或其它未知问题
                is UnknownHostException -> ApiErrorType.UNKNOWN_HOST
                is ConnectException -> ApiErrorType.NETWORK_NOT_CONNECT
                is SocketTimeoutException -> ApiErrorType.CONNECTION_TIMEOUT
                else -> ApiErrorType.UNEXPECTED_ERROR
            }
        }

        showToast.yes {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                ToastUtil.normal(apiError.message)
            }
        }
        onFailure(null)
    }


    final override fun onNext(t: ResWrapper<T>) {
        checkLogin(t)
        if (t.code == ResCode.RESPONSE_SUCCESS) {
            onSuccess(t)
        } else {
            showToast.yes {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    ToastUtil.normal(t.message)
                }
            }
            onFailure(t)
        }
    }
}