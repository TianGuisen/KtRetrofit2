package com.lb.baselib.retrofit

import com.alibaba.fastjson.JSON
import okhttp3.RequestBody

/**
 * @author 田桂森 2019/5/17
 * 用于@Body  json请求
 */
class RequestParam {

    private val map = mutableMapOf<Any, Any>()

    fun add(key: Any, value: Any?): RequestParam {
        value?.let {
            map.put(key, it)
        }
        return this
    }
    
    fun build(): RequestBody {
        val toJSON = JSON.toJSONString(map)
        val body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), toJSON)
        return body
    }
}