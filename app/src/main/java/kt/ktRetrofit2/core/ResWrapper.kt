package kt.ktRetrofit2.core

import java.io.Serializable


class ResWrapper<T> : Serializable {
    var code: Int = -1
    var message: String? = ""
    var data: T? = null
    var success: Boolean =false
}
