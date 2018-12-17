package kt.ktRetrofit2.core

import com.orhanobut.logger.Logger
import io.reactivex.observers.DisposableObserver
import kt.ktRetrofit2.consts.AppConfigs
import kt.ktRetrofit2.util.ToastUtil
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 网络请回调处理
 */
abstract class NormalObserver<T> : DisposableObserver<ResWrapper<T>>, IObserver<T> {
    var loading: RotateLoading? = null
    override var tag: String? = null
    override var showToast: Boolean = true
    override var repeat=0

    /**
     *@loading 是否显示loading
     *@showToast 是否显示错误toast
     *@tag: 请求标记,传入url
     *@repeat 重复请求策略,默认0,1和2时必须要传入tag
     *        0:允许重复请求,场景:viewpager多个页面的请求一样但是参数不同,可能短时间内请求多个页面数据
     *        1:关闭后入队的请求,比较常用:比如按钮的防重复时间是500,但是点击按钮后后台处理时间长达1000,在后500时间内按钮是可点击的但是请求是无意义的
     *        2:关闭先入队的请求,场景很少:频繁调用接口并只以最后一次的数据为准,出现这种情况通常设计不合理
     */
    constructor(loading: RotateLoading? = null, showToast: Boolean = true, tag: String? = null, repeat: Int = 0) {
        this.loading = loading
        this.tag = tag
        this.showToast = showToast
        this.repeat=repeat
    }

    override fun onStart() {
        if (!isConnected()) {
            if (showToast) {
                ToastUtil.normal("网络异常")
            }
            return
        }
        addTag(this,repeat)
        loading?.start()
    }

    final override fun onComplete() {
        removeTag()
        loading?.stop()
        onFinish()
    }

    final override fun onError(e: Throwable) {
        Logger.t(AppConfigs.LOGGER_NET_TAG).e(e.message!!,e)
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
                is UnknownHostException -> ApiErrorType.NETWORK_NOT_CONNECT
                is ConnectException -> ApiErrorType.NETWORK_NOT_CONNECT
                is SocketTimeoutException -> ApiErrorType.CONNECTION_TIMEOUT
                else -> ApiErrorType.UNEXPECTED_ERROR
            }
        }
        if (showToast) {
            ToastUtil.normal(apiError.message)
        }
        onFailure(null, apiError.code, apiError.name, tag)
        loading?.stop()
    }

    final override fun onNext(t: ResWrapper<T>) {
        checkLogin(t)
        if (t.success) {
            onSuccess(t.data, t.code, t.message, tag)
        } else {
            onFailure(t.data, t.code, t.message, tag)
        }
    }

    abstract fun onSuccess(data: T?, code: Int, msg: String?, tag: Any?)
    
}