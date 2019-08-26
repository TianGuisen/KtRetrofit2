package gdwl.tgs.home

import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.launcher.ARouter
import com.lb.baselib.base.BaseVMFra
import com.lb.baselib.retrofit.ioMain
import com.lb.baselib.util.normalSub
import com.lb.baselib.util.setDoubleClickLis
import com.orhanobut.logger.Logger
import gdwl.tgs.R
import gdwl.tgs.databinding.FraHomeBinding
import gdwl.tgs.service.guideGson
import kotlinx.android.synthetic.main.fra_home.*

/**
 * @author 田桂森 2019/7/5
 */
class HomeFra : BaseVMFra<FraHomeBinding>() {
    val vm: HomeVM  by lazy { ViewModelProviders.of(this).get(HomeVM::class.java) }

    override fun getLayoutId(): Int = R.layout.fra_home

    override fun setVM() = vm

    
    override fun inited() {
        tv1.setDoubleClickLis {
            guideGson.getBannerInfo().compose(ioMain(this)).normalSub({
                Logger.d(it)
            })
        }
        tv2.setDoubleClickLis {
//            使用反射跳转
//            val forName = Class.forName("com.example.test2login.LoginActivity")
//            activity!!.startActivity(Intent(activity,forName))
//            ARouter.getInstance().build("/main/MainActivity").navigation();

            ARouter.getInstance().build("/login/LoginActivity").navigation();
        }
    }

    companion object {
        fun newInstans() = HomeFra()
    }
}