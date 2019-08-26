package com.lb.baselib

import android.app.Application
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lb.baselib.base.ActivityStackManager
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.tencent.bugly.crashreport.CrashReport
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.unit.Subunits
import me.yokeyword.fragmentation.Fragmentation


object ToolApplication {
    lateinit var appContext: Application
    fun init(appContext: Application) {
        ActivityStackManager.register(appContext)
        this.appContext = appContext
        initLog()
        initBugly()
        initAutoSize()
        initRefresh()
        initLiveEventBus()
        initFragmentBall()
    }

    private fun initLog() {
        val strategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)  // 是否显示线程信息，默认为ture
            .methodCount(1)         // 显示的方法行数
            .methodOffset(0)        // 隐藏内部方法调用到偏移量
            .tag(AppConfig.LOGGER_TAG)
            .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(strategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }


    private fun initAutoSize() {
        AutoSizeConfig.getInstance()
            .setCustomFragment(true)
            .setUseDeviceSize(true)
            .unitsManager
            .setSupportDP(false)
            .setSupportSP(false).supportSubunits = Subunits.PT
    }

    private fun initBugly() {
        CrashReport.initCrashReport(
            appContext,
            if (BuildConfig.DEBUG) AppConfig.BUGLY_APPID_DEBUG else AppConfig.BUGLY_APPID_RELEASE,
            BuildConfig.DEBUG
        )
    }

    private fun initRefresh() {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white)//全局设置主题颜色
            MaterialHeader(context)//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        }
    }

    private fun initLiveEventBus() {
        LiveEventBus.get()
            .config()
            .supportBroadcast(appContext)
            .lifecycleObserverAlwaysActive(true)
    }


    private fun initFragmentBall() {
        if (BuildConfig.DEBUG) {
            Fragmentation.builder()
                // 设置 栈视图 模式为 悬浮球模式   SHAKE: 摇一摇唤出   NONE：隐藏
                .stackViewMode(Fragmentation.BUBBLE)
                // ture时，遇到异常："Can not perform this action after onSaveInstanceState!"时，会抛出
                // false时，不会抛出，会捕获，可以在handleException()里监听到
                .debug(BuildConfig.DEBUG)
                // 线上环境时，可能会遇到上述异常，此时debug=false，不会抛出该异常（避免crash），会捕获
                // 建议在回调处上传至我们的Crash检测服务器
                .handleException {
                    // 以Bugtags为例子: 手动把捕获到的 Exception 传到 Bugtags 后台。
                    // Bugtags.sendException(e);
                }
                .install()
        }
    }

}
