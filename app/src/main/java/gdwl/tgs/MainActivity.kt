package gdwl.tgs

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.connect.connectManage
import com.hjq.permissions.OnPermission
import com.hjq.permissions.XXPermissions
import com.lb.baselib.base.BaseActivity
import com.orhanobut.logger.Logger
import gdwl.tgs.bean.Test1
import gdwl.tgs.home.HomeFra


@Route(path = "/main/MainActivity")
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl, HomeFra.newInstans())
        }
        XXPermissions.with(this)
            //.constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
            //.permission(Permission.SYSTEM_ALERT_WINDOW, Permission.REQUEST_INSTALL_PACKAGES) //支持请求6.0悬浮窗权限8.0请求安装权限
            //.permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE, Manifest.permission.RECORD_AUDIO) //不指定权限则自动获取清单中的危险权限
            .request(object : OnPermission {

                override fun hasPermission(granted: List<String>, isAll: Boolean) {

                }

                override fun noPermission(denied: List<String>, quick: Boolean) {

                }
            })  
        testClassLoader()
        testLoginState()
    }

    /**
     * 测试反射
     */
    private fun testClassLoader() {
        var classLoader: ClassLoader? = classLoader
        while (classLoader != null) {
            Logger.d("classLoader: $classLoader")
            classLoader = classLoader.parent
        }
        val clz = Class.forName("gdwl.tgs.bean.Test1")
        val method = clz.getMethod("getA")
        val constructor = clz.getConstructor()
        val obj = constructor.newInstance()
        val java = Test1::class.java.getConstructor().newInstance()
        Logger.d(java)
        val invoke = method.invoke(obj)
        Logger.d(invoke)
    }

    /**
     * 测试组件化通信,登录状态
     */
    private fun testLoginState() {
        Logger.d(connectManage.getAccountConnect().isLogin())
    }
}
