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
```
```
//Recyclerview界面使用RVObserver
ProductService.getProductList(pageNum, 10).compose(ioMain(mV))
                .subscribe(object : RVObserver<MutableList<ProductInfo>>(loadView, pageNum) {
                    override fun onSuccess(info: MutableList<ProductInfo>?, code: Int, message: String?, tag: Any?) {
                    }
                })
```
