package com.lb.baselib.retrofit

import com.lb.baselib.AppConfig
import com.lb.baselib.util.defSP
import com.orhanobut.logger.Logger
import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset


/**
 * 参数拦截器
 */
class ParamInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val orgRequest = chain.request().newBuilder()
        if ("POST" == chain.request().method()) run {
            orgRequest.addHeader("Content-Type", "application/json;charset=UTF-8")
        }
        val userToken by defSP("pre_key_userToken", "")
        //            userToken = "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFM1MTIifQ.eyJ1c2VyQ29kZSI6IjA4ZmQ1ZDQ2NzA3ZjQzZTQ5ZmVhZjdjYzU4ZjY5MzY1IiwiaXNzIjoid2x3YkFwaSIsImF1ZCI6IjdhMTRlNzAxNWQwYTQ5YzZhMzA4Yjk4ZTQwMGYyOWQ4In0.r2lYYysTOrzO7hYPfpnJqEE6g_nUbDUNq8OlWxWsj5aV_8giME16_TVdbkQRMRrV26TvApBipLUhWG9s1H4fxw";
        orgRequest.addHeader("Authorization", userToken)
        val build = orgRequest.build()
        return chain.proceed(build)
    }
}

/**
 * 日志拦截器
 */
class LoggerInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val orgRequest = chain.request()
        val response = chain.proceed(orgRequest)
        //统一在请求回来时候打印请求信息和返回数据,避免日志窗口中的日志中断杂乱
        val body: RequestBody? = orgRequest.body()
        val buffer = Buffer()
        body?.writeTo(buffer)
        var charset = Charset.forName("UTF-8")
        val contentType = body?.contentType()
        if (contentType != null) {
            charset = contentType.charset(Charset.forName("UTF-8"))
        }
        var requestString = "code=" + response.code() +
                "|method=" + orgRequest.method() +
                "|url=" + orgRequest.url() +
                "\n" + "|headers:" + orgRequest.headers().toMultimap()
        if (body != null) {
            requestString = requestString + "\n" + "body:" + buffer.readString(charset)
        }
        Logger.t(AppConfig.LOGGER_NET_TAG).d(requestString)

        //返回json
        val responseBody = response.body()
        val source = responseBody!!.source()
        source.request(java.lang.Long.MAX_VALUE)
        val buffer2 = source.buffer()
        val contentType2 = responseBody.contentType()
        val charset2 = contentType2?.charset(Charset.forName("UTF-8"))
        //打印返回json
        //json日志使用鼠标中键进行选中
        Logger.t(AppConfig.LOGGER_NET_TAG).json(buffer2.clone().readString(charset2!!))
        return response
    }

}

