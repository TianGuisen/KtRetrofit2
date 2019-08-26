package com.example.test2login

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.lb.baselib.base.BaseActivity

@Route(path = "/login/LoginActivity")
class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
    }
}
