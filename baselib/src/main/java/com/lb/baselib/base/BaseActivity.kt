package com.lb.baselib.base

import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Resources
import android.content.res.TypedArray
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.CallSuper
import com.gyf.immersionbar.ImmersionBar
import com.lb.baselib.R
import com.lb.baselib.util.AppUtil
import com.lb.baselib.util.ToastUtil
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.RxLifecycleAndroid
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import me.jessyan.autosize.AutoSizeCompat
import me.yokeyword.fragmentation.SupportActivity

open class BaseActivity : SupportActivity(), LifecycleProvider<ActivityEvent> {

    /**
     * 是否禁用返回键
     */
    open protected var banBack = false
    private val lifecycleSubject = BehaviorSubject.create<ActivityEvent>()


    override fun onCreate(savedInstanceState: Bundle?) {
        //修复8.0屏幕bug
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating) {
            fixOrientation()
        }
        super.onCreate(savedInstanceState)
        ImmersionBar.with(this).init()
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        lifecycleSubject.onNext(ActivityEvent.RESUME)
    }

    fun title(title: String) {
        findViewById<TextView>(R.id.tv_title)?.setText(title)
    }

    fun whiteTitle() {
        findViewById<TextView>(R.id.tv_title)?.setTextColor(AppUtil.getColor(R.color.white))
        findViewById<ImageView>(R.id.iv_back)?.setImageResource(R.drawable.iv_back_white)
    }

    override fun getResources(): Resources {//老代码的坑,release不知道哪里修改了density导致不能适配,加这个就好了.
        AutoSizeCompat.autoConvertDensityOfGlobal((super.getResources()))
        return super.getResources()
    }

    private fun fixOrientation(): Boolean {
        try {
            val field = Activity::class.java.getDeclaredField("mActivityInfo")
            field.isAccessible = true
            val o = field.get(this) as ActivityInfo
            o.screenOrientation = -1
            field.isAccessible = false
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }

    private val isTranslucentOrFloating: Boolean
        get() {
            var isTranslucentOrFloating = false
            try {
                val styleableRes = Class.forName("com.android.internal.R\$styleable").getField("Window").get(null) as IntArray
                val ta = obtainStyledAttributes(styleableRes)
                val m = ActivityInfo::class.java.getMethod("isTranslucentOrFloating", TypedArray::class.java)
                m.isAccessible = true
                isTranslucentOrFloating = m.invoke(null, ta) as Boolean
                m.isAccessible = false
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return isTranslucentOrFloating
        }

    override fun lifecycle(): Observable<ActivityEvent> {
        return lifecycleSubject.hide()
    }

    override fun <T> bindUntilEvent(event: ActivityEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event)
    }

    override fun <T> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject)
    }

    @CallSuper
    override fun onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE)
        super.onPause()
    }

    @CallSuper
    override fun onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP)
        super.onStop()
    }

    override fun onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY)
        super.onDestroy()
    }

    private var firstTime: Long = 0
    
    override fun onBackPressedSupport() {
        //fragmantation中的fragment栈返回
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop()
        } else if (ActivityStackManager.getSize() == 1) {
            //acitivty栈只有一个就提示退出
            val secondTime = System.currentTimeMillis()
            if (secondTime - firstTime > 2000) {
                ToastUtil.normal("再按一次退出程序")
                firstTime = secondTime;
            } else {
                System.exit(0);
            }
        } else {
            finish()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //禁用了返回键
            if (banBack) {
                return true
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
