# KtRetrofit2
Rxjava2+Retrofit2二次封装,使用kotlin语言,有loading,token,防多次重复请求等处理  
详情请看:https://www.jianshu.com/p/4f1d4ec4b71d

使用方法  
```
//普通请求使用NormalObserver
UserService.login("tian", "1").compose(ioMain(this))
                .subscribe(object : NormalObserver<UserInfo>(loading,tag = Urls.LOGIN) {
                    override fun onSuccess(info: UserInfo?, code: Int, message: String?, tag: Any?) {
                    }
                })
  /**
     *@loading 是否显示loading
     *@showToast 是否显示错误toast
     *@tag: 请求标记,传入url
     *@repeat 重复请求策略,默认0,1和2时必须要传入tag
     *        0:允许重复请求,场景:viewpager多个页面的请求一样但是参数不同,可能短时间内请求多个页面数据
     *        1:关闭后入队的请求,比较常用:比如按钮的防重复时间是500,但是点击按钮后后台处理时间长达1000,在后500时间内按钮是可点击的但是请求是无意义的
     *        2:关闭先入队的请求,场景很少:频繁调用接口并只以最后一次的数据为准,出现这种情况通常设计不合理
     */
    constructor(loading: RotateLoading? = null, showToast: Boolean = true, tag: String? = null, repeat: Int = 0) {
        this.loading = loading
        this.tag = tag
        this.showToast = showToast
        this.repeat=repeat
    }
```
```
//Recyclerview界面使用RVObserver
ProductService.getProductList(pageNum, 10).compose(ioMain(mV))
                .subscribe(object : RVObserver<MutableList<ProductInfo>>(loadView, pageNum) {
                    override fun onSuccess(info: MutableList<ProductInfo>?, code: Int, message: String?, tag: Any?) {
                    }
                })
                    /**
     *@loading RecyclerView的LoadView
     *@page:页码
     *@tag: 请求标记,传入url,
     *@repeat 重复请求策略,默认0,1和2时必须要传入tag
     *        0:允许重复请求,场景:viewpager多个页面的请求一样但是参数不同,可能短时间内请求多个页面数据
     *        1:关闭后入队的请求,比较常用:比如按钮的防重复时间是500,但是点击按钮后后台处理时间长达1000,在后500时间内按钮是可点击的但是请求是无意义的
     *        2:关闭先入队的请求,场景很少:频繁调用接口并只以最后一次的数据为准,出现这种情况通常设计不合理
     */
    constructor(loadView: LoadView, page: Int, showToast: Boolean = true, tag: String? = null, repeat: Int = 0) {
        this.loadView = loadView
        this.page = page
        this.tag = tag
        this.showToast = showToast
        this.repeat=repeat
    }
```
