# KtRetrofit2
Kotlin+Rxjava2+Retrofit2二次封装,使用kotlin语言,有loading,token,防多次重复请求等处理  
代码在baselib里com.lb.baselib.retrofit下
使用时需要根据情况修改一些代码。  
1.ResWrapper.kt，根据后端返回的外层json修改。
```  
{
	"code":0,
	"message":"",
	"data":null,
}
data class ResWrapper<out T>(val code: Int = -1,val message: String,val data: T?)
```
2.ResCode.kt,和后端约定各种code代表的业务。    
3.Interceptor.kt->ParamInterceptor，添加公共请求头和请求参数。  
4.Configs.kt设置baseUrl。关于多baseUrl切换，数量较少的情况下建议创建多个Retrofit，省事。  
5.loading框和recyclerview多状态loadview。  


使用方法  
```
//普通请求使用NormalObserver
guideGson.login("a", "1").compose(ioMain(this)).normalSub({
                Logger.d(it)
		it.data.run { 
                Logger.d(name)    
                }
            })
RxExt.kt中的代码			
fun <T> Observable<ResWrapper<T>>.normalSub(
        onSuccess: ((ResWrapper<T>) -> Unit)? = null,
        onFailure: ((ResWrapper<T>?) -> Unit)? = null,
        onFinish: (() -> Unit)? = null,
        context: Context? = null,//传入表示显示dialog
        showToast: Boolean = true) {
    val ob = object : NormalObserver<T>(context, showToast) {
        override fun onSuccess(t: ResWrapper<T>) {
            onSuccess?.let { onSuccess(t) }
        }

        override fun onFailure(t: ResWrapper<T>?) {
            onFailure?.let { onFailure(t) }
        }

        override fun onFinish() {
            onFinish?.let { onFinish() }
        }
    }
    subscribe(ob)
}
```
```
//Recyclerview界面使用这个
guideGson.list("a", "1").compose(ioMain(this)).lvSub({
               Logger.d(it)
            })
RxExt.kt中的代码
fun <T> Observable<ResWrapper<T>>.lvSub(onSuccess: ((ResWrapper<T>) -> Unit)? = null,
                                        onFailure: ((ResWrapper<T>?) -> Unit)? = null,
                                        loadView: LoadView? = null,//传入表示显示空布局等
                                        page: Int = 1,//只有第一页空数据才会现实空布局
                                        showToast: Boolean = true) {
    val ob = object : LVObserver<T>(loadView, page, showToast) {
        override fun onSuccess(t: ResWrapper<T>) {
            onSuccess?.let { onSuccess(t) }
        }

        override fun onFailure(t: ResWrapper<T>?) {
            onFailure?.let { onFailure(t) }
        }
    }
    subscribe(ob)
}
```
<<<<<<< HEAD

=======
```
//同时进行多次请求且全部返回才进行后续处理
guideGson.testzip("a", "1").compose(ioMain(this)).zipSub({
               Logger.d(it)
            })
RxExt.kt中的代码
fun Observable<MutableList<Any>>.zipSub(onSuccess: (MutableList<Any>) -> Unit, onFailure: (ResWrapper<Any>?) -> Unit) {
    val ob = object : ZipObserver<Any>() {
        override fun onSuccess(data: MutableList<Any>) {
            onSuccess(data)
        }

        override fun onFailure(t: ResWrapper<Any>?) {
            onFailure(t)
        }

    }
    subscribe(ob)
}
```
>>>>>>> e41bc362aeefc6c8a1bff6a9f2ed40565470d5cc
注意：如果使用mvvm架构，vm层就无法拿到v的引用，也就无法自动在请求中显示dialog、recyclerview空列表，需要额外写代码处理。
