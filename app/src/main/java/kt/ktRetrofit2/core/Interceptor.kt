package kt.ktRetrofit2.core

import com.alibaba.fastjson.util.IOUtils.UTF8
import com.orhanobut.logger.Logger
import kt.ktRetrofit2.consts.AppConfigs
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.nio.charset.UnsupportedCharsetException

/**
 * 参数烂机器
 */
class ParamInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val newOrgRequest = chain.request().newBuilder()
                //添加公共请求头
//                .addHeader("token", AccountBiz.token)
                .build()
        return chain.proceed(newOrgRequest)
    }
}

/**
 * 日志拦截器
 */
class LoggerInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        val orgRequest = chain!!.request()
        val response = chain.proceed(orgRequest)
        val body = orgRequest.body()
        val sb = StringBuilder()
        if (orgRequest.method() == "POST" && body is FormBody) {
            val body1 = body
            for (i in 0 until body1.size()) {
                sb.append(body1.encodedName(i) + "=" + body1.encodedValue(i) + ",")
            }
            sb.delete(sb.length - 1, sb.length)
            //打印post请求的信息
            Logger.t(AppConfigs.LOGGER_NET_TAG).d("code=" + response.code() + "|method=" + orgRequest.method() + "|url=" + orgRequest.url()
                    + "\n" + "headers:" + orgRequest.headers().toMultimap()
                    + "\n" + "post请求体:{" + sb.toString() + "}")
        } else {
            //打印get请求的信息
            Logger.t(AppConfigs.LOGGER_NET_TAG).d("code=" + response.code() + "|method=" + orgRequest.method() + "|url=" + orgRequest.url()
                    + "\n" + "headers:" + orgRequest.headers().toMultimap())
        }
        //返回json
        val responseBody = response.body()
        val contentLength = responseBody!!.contentLength()
            val source = responseBody.source()
            source.request(java.lang.Long.MAX_VALUE)
            val buffer = source.buffer()
            var charset = UTF8
            val contentType = responseBody.contentType()
            if (contentType != null) {
                try {
                    charset = contentType.charset(UTF8)
                } catch (e: UnsupportedCharsetException) {
                    return response
                }

            }
            if (contentLength != 0L) {
                //打印返回json
                //json日志使用鼠标中键进行选中
                Logger.t(AppConfigs.LOGGER_NET_TAG).json(buffer.clone().readString(charset))
            }
        return response
    }

}

