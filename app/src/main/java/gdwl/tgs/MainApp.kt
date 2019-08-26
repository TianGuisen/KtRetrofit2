package gdwl.tgs

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.lb.baselib.BaseApp
import com.lb.baselib.ToolApplication
import me.jessyan.autosize.utils.LogUtils.isDebug

/**
 * @author 田桂森 2019/7/5
 */
class MainApp : BaseApp() {
    override fun initModuleApp(application: Application?) {
        try {
            val clz = Class.forName("com.example.test2login.LoginApp")
            val baseApp = clz.newInstance() as BaseApp
            baseApp.initModuleApp(this)
        } catch (e: Exception) {
        }
    }

    override fun onCreate() {
        super.onCreate()
        if (isDebug()) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
        ToolApplication.init(this)
        initModuleApp(this)
    }

}