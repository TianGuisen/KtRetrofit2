package com.lb.baselib


/**
 * @author 田桂森 2019/4/28
 */
object AppConfig {
    val environment: Environment = Environment.DEBUG
//    val environment: Environment = Environment.RELEASE
//    val environment: Environment = Environment.BATE

    //bugly app key
    const val BUGLY_APPID_RELEASE = "abd4661e9d"
    const val BUGLY_APPID_DEBUG = "38861b095e"

    //微信appid
    const val WECHAT_APP_ID = ""
    const val WECHAT_APP_SECRET = ""

    /**
     * Logger日志的tag
     */
    const val LOGGER_TAG = "tian"
    /**
     * Logger网络请求日志的tag
     */
    const val LOGGER_NET_TAG = "retrofit"
}

object HostConfig {
    var apiHost: String
    var H5Host: String

    init {
        when (AppConfig.environment) {
            Environment.DEBUG -> {
                apiHost = "http://app.bilibili.com/"
//                apiHost = "http://192.168.0.150:8888/v2/api/"  //张正鹤本地环境
                H5Host = "https://sintest.storeer.com/"
            }
            Environment.BATE -> {
                apiHost = "http://app.bilibili.com/"
                H5Host = "https://sintest.storeer.com/"
            }
            Environment.RELEASE -> {
                apiHost = "http://app.bilibili.com/"
                H5Host = "https://singlepage.storeer.com/"
            }
        }
    }
}

enum class Environment {
    DEBUG, BATE, RELEASE
}
