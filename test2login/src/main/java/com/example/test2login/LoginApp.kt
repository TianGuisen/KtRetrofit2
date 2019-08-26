package com.example.test2login

import android.app.Application
import com.connect.connectManage
import com.lb.baselib.BaseApp

class LoginApp : BaseApp() {

    override fun initModuleApp(application: Application) {
       connectManage.setAccountConnect(AccountConnect())
    }

}
