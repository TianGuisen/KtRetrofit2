package com.lb.baselib.retrofit

object ResCode {
    //和后台约定的业务错误
    /**
     * token过期,需要登录
     */
    const val TOKEN_OVERDUE = 10
    //失败
    const val RESPONSE_ERROR = -1
    //成功
    const val RESPONSE_SUCCESS = 0
}

enum class ApiErrorType(val code: Int, val message: String) {
    //非业务错误
    INTERNAL_SERVER_ERROR(500, "服务器错误"),
    BAD_GATEWAY(502, "服务器错误"),
    GATEWAY_TIMEOUT(504, "网络连接异常"),
    NOT_FOUND(404, "HTTP 404 Not Found"),
    CONNECTION_TIMEOUT(408, "连接超时"),
    UNKNOWN_HOST(498, "未知域名"),
    NETWORK_NOT_CONNECT(499, "网络连接异常"),
    UNEXPECTED_ERROR(700, "未知错误");
}