package kt.ktRetrofit2.servier

import io.reactivex.Observable
import kt.ktRetrofit2.bean.UserInfo
import kt.ktRetrofit2.consts.Urls
import kt.ktRetrofit2.core.ResWrapper
import kt.ktRetrofit2.core.gsonService
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface UserApi {


    @POST(Urls.LOGIN)
    @FormUrlEncoded
    fun login(@Field("username") username: String, @Field("password") password: String): Observable<ResWrapper<UserInfo>>

}

object UserService : UserApi by gsonService.create(UserApi::class.java)