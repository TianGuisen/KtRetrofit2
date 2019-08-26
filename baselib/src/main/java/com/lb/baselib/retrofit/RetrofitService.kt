package com.lb.baselib.retrofit

import com.lb.baselib.HostConfig.apiHost
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

val stringService: Retrofit by lazy {
    val retrofitParams = RetrofitParams()
    retrofitParams.interceptors.add(ParamInterceptor())
    retrofitParams.interceptors.add(LoggerInterceptor())
    retrofitParams.converterFactory = ScalarsConverterFactory.create()
    createRetrofit(retrofitParams)
}

val gsonService: Retrofit by lazy {
    val retrofitParams = RetrofitParams()
    retrofitParams.interceptors.add(ParamInterceptor())
    retrofitParams.interceptors.add(LoggerInterceptor())
    retrofitParams.converterFactory = GsonConverterFactory.create()
    createRetrofit(retrofitParams)
}

private fun createRetrofit(params: RetrofitParams): Retrofit {
    val builder = OkHttpClient.Builder()
    for (interceptor in params.interceptors) {
        builder.addInterceptor(interceptor)
    }
//    builder.addNetworkInterceptor { chain ->
//        val request = chain.request()
//        val originalResponse = chain.proceed(request)
//        val maxAge = 0 // 在线缓存,单位:秒
//        originalResponse.newBuilder().removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
//            .removeHeader("Cache-Control").header("Cache-Control", "public, max-age=$maxAge").build()
//    }
//    builder.addInterceptor { chain ->
//        var request = chain.request()
//        if (!AppUtil.isConnected()) {
//            val maxStale = 7 * 24 * 60 * 60 // 离线时缓存保存1周
//            val tempCacheControl = CacheControl.Builder().onlyIfCached().maxStale(maxStale, TimeUnit.SECONDS).build()
//            request = request.newBuilder().cacheControl(tempCacheControl).build()
//        }
//        chain.proceed(request)
//    }.cache(Cache(appContext.cacheDir, (10 * 1024 * 1024).toLong()))

    builder.retryOnConnectionFailure(true)
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
    val retrofit = Retrofit.Builder()
        .baseUrl(params.baseUrl)
        .addConverterFactory(params.converterFactory)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(builder.build())
        .build()
    return retrofit
}

private class RetrofitParams {
    val interceptors = mutableListOf<Interceptor>()
    var baseUrl = apiHost
    lateinit var converterFactory: Converter.Factory
}