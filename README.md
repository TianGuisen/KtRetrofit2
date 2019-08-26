# KtRetrofit2
Kotlin+Rxjava2+Retrofit2二次封装,使用kotlin语言,有loading,token,防多次重复请求等处理  
代码在baselib里com.lb.baselib.retrofit下
使用时需要根据情况修改一些代码。
1.ResWrapper，根据后端返回的外层json修改。
```  
{
	"code":0,
	"message":"",
	"data":null,
}
data class ResWrapper<out T>(val code: Int = -1,val message: String,val data: T?)
```


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
注意：如果使用mvvm架构，vm层就无法拿到v的引用，也就无法自动在请求中显示dialog、recyclerview空列表，需要额外写代码处理。
