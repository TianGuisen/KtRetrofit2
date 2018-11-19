package kt.ktRetrofit2.core

import kt.ktRetrofit2.consts.Urls.BASE_URL
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
    builder.connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.MINUTES)
            .retryOnConnectionFailure(false)
            .writeTimeout(30, TimeUnit.MINUTES)
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
    var baseUrl = BASE_URL
    lateinit var converterFactory: Converter.Factory
}