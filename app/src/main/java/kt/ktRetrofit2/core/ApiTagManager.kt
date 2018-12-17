package kt.ktRetrofit2.core

import androidx.core.util.arrayMapOf
import io.reactivex.observers.DisposableObserver

/**
 * 网络请求tag管理
 * 相同tag的请求只会存在一个
 */
class ApiTagManager private constructor() {

    private val maps = arrayMapOf<String, DisposableObserver<*>>()

    companion object {
        val instance = ApiTagManager()
    }

    /**
     * 后入队的会被关闭
     */
    fun add1(tag: String?, observer: DisposableObserver<*>) {
        if (tag==null){
            throw IllegalArgumentException("tag不能为null")
        }
        maps.keys.forEach {
            if (tag == it) {
                observer.dispose()
                return
            }
        }
        maps.put(tag, observer)
    }

    /**
     * 先入队的会被关闭
     */
    fun add2(tag: String?, observer: DisposableObserver<*>) {
        if (tag==null){
            throw IllegalArgumentException("tag不能为null")
        }
        cancel(tag)
        maps.put(tag, observer)
    }

    fun remove(tag: String?) {
        if (tag==null){
           return 
        }
        if (!maps.isEmpty()) {
            maps.remove(tag)
        }
    }

    fun removeAll() {
        if (!maps.isEmpty()) {
            maps.clear()
        }
    }

    fun cancel(tag: String) {
        if (maps.isEmpty()) {
            return
        }
        val m1 = maps.get(tag)
        if (m1 == null) {
            return
        }
        if (!m1.isDisposed()) {
            m1.dispose()
            maps.remove(tag)
        }
    }

    fun cancelAll() {
        if (maps.isEmpty()) {
            return
        }
        val tags = maps.keys
        for (tag in tags) {
            cancel(tag)
        }
    }
}