package kt.ktRetrofit2

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.trello.rxlifecycle2.components.RxActivity
import kt.ktRetrofit2.bean.UserInfo
import kt.ktRetrofit2.consts.Urls
import kt.ktRetrofit2.core.NormalObserver
import kt.ktRetrofit2.core.RotateLoading
import kt.ktRetrofit2.core.ioMain
import kt.ktRetrofit2.servier.UserService

class MainActivity : RxActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val loading = findViewById<RotateLoading>(R.id.loading)
        findViewById<View>(R.id.bt1).setOnClickListener { v ->
            UserService.login("tian", "1").compose(ioMain(this))
                    .subscribe(object : NormalObserver<UserInfo>(loading, tag = Urls.LOGIN) {
                        override fun onSuccess(data: UserInfo?, code: Int, msg: String?, tag: Any?) {

                        }
                    })
        }
        findViewById<View>(R.id.bt2).setOnClickListener { v ->
            MainActivity@this.startActivity(Intent(MainActivity@this,MainActivity2::class.java))
        }
    }
}