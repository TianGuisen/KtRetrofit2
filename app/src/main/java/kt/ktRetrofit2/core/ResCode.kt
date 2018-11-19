package kt.ktRetrofit2.core

import android.support.annotation.StringRes

object ResCode {
    //和后台约定的业务错误
    /**
     * token过期,需要登录
     */
    const val TOKEN_OVERDUE = 10
    
}
enum class ApiErrorType(val code: Int, @param: StringRes val message: String) {
    //非业务错误
    INTERNAL_SERVER_ERROR(500, "服务器错误"),
    BAD_GATEWAY(502, "服务器错误"),
    NOT_FOUND(404, "未找到"),
    CONNECTION_TIMEOUT(408, "连接超时"),
    NETWORK_NOT_CONNECT(499, "网络异常"),
    UNEXPECTED_ERROR(700,"未知错误");
}