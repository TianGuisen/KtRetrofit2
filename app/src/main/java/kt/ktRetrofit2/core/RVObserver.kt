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
 * recyclerview的网络请回调处理
 */
abstract class RVObserver<T> : DisposableObserver<ResWrapper<T>>, IObserver<T> {
    var loadView: LoadView? = null
    override var tag: String? = null
    override var canRepeat: Boolean = false
    override var showToast: Boolean = true
    var page: Int = 0
    var isFirstLoad = true

    /**
     *@tag: 请求标记,传入url,
     *@loading RecyclerView的LoadView
     *@page:页码
     */
    constructor(loadView: LoadView, page: Int, showToast:Boolean=true, tag: String? = null) {
        this.loadView = loadView
        this.page = page
        this.tag = tag
        this.showToast=showToast
    }

    /**
     * 设置可以重复请求.默认禁止
     */
    fun setCanRepeat(): RVObserver<T> {
        canRepeat = true
        return this
    }

    override fun onStart() {
        if (!isConnected()) {
            ToastUtil.normal("网络异常")
            return
        }
        addTag(this)
    }

    final override fun onComplete() {
        removeTag()
        onFinish()
    }

    final override fun onError(e: Throwable) {
        Logger.t(AppConfigs.LOGGER_NET_TAG).e(e.message!!,e)
        removeTag()
        val apiError = if (e is HttpException) { //连接成功但后台返回错误状态码
            if (page == 1) {
                loadView?.showError()
            }
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
            if (page == 1) {
                loadView?.showNoNetwork()
            }
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
    }

    final override fun onNext(t: ResWrapper<T>) {
        checkLogin(t)
        if (t.success) {
            showLoadView(t.data)
            onSuccess(t.data, t.code, t.message, tag)
        } else {
            loadView?.showError()
            onFailure(t.data, t.code, t.message, tag)
        }
    }

    private fun showLoadView(data: T?) {
        if (data is List<*>) {
            if (data.isEmpty()) {
                if (page == 1) {
                    loadView?.showEmpty()
                } else {
                    ToastUtil.normal("没有更多数据!")
                }
            } else {
                loadView?.showContent()
            }
        }
    }
  
}