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
    override var canRepeat: Boolean = false
    override var showToast: Boolean = true

    /**
     *@loading 是否显示loading
     *@showToast 是否显示错误toast
     *@tag: 请求标记,传入url
     */
    constructor(loading: RotateLoading? = null, showToast:Boolean=true, tag: String? = null) {
        this.loading = loading
        this.tag = tag
        this.showToast=showToast
    }

    /**
     * 设置可以重复请求.默认禁止
     */
    fun setCanRepeat(): NormalObserver<T> {
        canRepeat = true
        return this
    }

    override fun onStart() {
        if (!isConnected()) {
            ToastUtil.normal("网络异常")
            return
        }
        addTag(this)
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

    
}