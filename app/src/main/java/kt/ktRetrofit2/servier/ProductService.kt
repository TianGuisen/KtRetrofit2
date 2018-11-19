package com.bennyhuo.github.network.services

import io.reactivex.Observable
import kt.ktRetrofit2.bean.ProductInfo
import kt.ktRetrofit2.consts.Urls
import kt.ktRetrofit2.core.ResWrapper
import kt.ktRetrofit2.core.gsonService
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface ProductApi {

    @POST(Urls.PRODUCT_LIST)
    @FormUrlEncoded
    fun getProductList(@Field("pageNum")pageNum :Int, @Field("pageSize")pageSize :Int): Observable<ResWrapper<MutableList<ProductInfo>>>
}

object ProductService : ProductApi by gsonService.create(ProductApi::class.java)