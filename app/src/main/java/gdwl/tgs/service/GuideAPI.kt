package gdwl.tgs.service

import com.lb.baselib.retrofit.ResWrapper
import com.lb.baselib.retrofit.gsonService
import gdwl.tgs.bean.RecommendInfo
import io.reactivex.Observable
import retrofit2.http.GET

val guideGson = gsonService.create(GuideAPI::class.java)

interface GuideAPI {

    @GET("staff/lifeCenter/ad/listQuickConfig")
    fun quickConfig(): Observable<ResWrapper<MutableList<Any>>>

    /**
     * 首页推荐banner
     */
    @GET("x/banner?plat=4&build=411007&channel=bilih5")
    fun getBannerInfo(): Observable<ResWrapper<List<RecommendInfo.BannerInfo>>>

    /**
     * 首页推荐数据
     */
    @GET("x/show/old?platform=android&device=&build=412001")
    fun getRecommendedInfo(): Observable<RecommendInfo>
    
}
