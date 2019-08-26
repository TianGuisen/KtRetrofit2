package com.lb.baselib.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.CheckResult
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.gyf.immersionbar.ImmersionBar
import com.lb.baselib.BR
import com.lb.baselib.R
import com.lb.baselib.customView.TitleBar
import com.lb.baselib.util.KeyboardUtils
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.android.RxLifecycleAndroid
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import me.yokeyword.fragmentation.SupportFragment

/**
 * Created by YoKeyword on 16/2/3.
 */

abstract class BaseFra<D : ViewDataBinding> : SupportFragment(), LifecycleProvider<FragmentEvent> {
    public var tb: TitleBar? = null
    open lateinit var bind: @UnsafeVariance D
    abstract fun getLayoutId(): Int

    private val lifecycleSubject = BehaviorSubject.create<FragmentEvent>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        bind = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        bind.setVariable(BR.v, this)
        bind.setLifecycleOwner(this)
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
        return bind.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val toolbar = view.findViewById<View>(setToolBar())
        if (toolbar != null) {
            ImmersionBar.setTitleBar(activity, toolbar)
        }
        val statusBarView = view.findViewById<View>(setStatusBarView())
        if (statusBarView != null) {
            ImmersionBar.setStatusBarView(activity, statusBarView)
        }
        if (toolbar is TitleBar) {
            tb = toolbar
            tb?.setOnLeftImageListener {
                onBackClick()
            }
        }
        inited()
    }

    fun title(title: String): TextView {
        val tv_title = tb!!.setTitle(title)
        return tv_title
    }


    open fun onBackClick() {
        pop()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        //请在onSupportVisible实现沉浸式
        if (isImmersionBarEnabled()) {
            initImmersionBar()
        }
    }

    open fun initImmersionBar() {
        ImmersionBar.with(this)
            .keyboardEnable(true)
            .statusBarDarkFont(true)//深色字体
            .init()
    }

    open fun isImmersionBarEnabled(): Boolean {
        return true
    }

    open fun setToolBar(): Int {
        return R.id.toolbar
    }

    open fun setStatusBarView(): Int {
        return R.id.status
    }

    protected abstract fun inited()

    @CheckResult
    override fun lifecycle(): Observable<FragmentEvent> {
        return lifecycleSubject.hide()
    }

    @CheckResult
    override fun <T> bindUntilEvent(event: FragmentEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event)
    }

    @CheckResult
    override fun <T> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleSubject.onNext(FragmentEvent.CREATE)
    }


    override fun onStart() {
        super.onStart()
        lifecycleSubject.onNext(FragmentEvent.START)
    }

    override fun onResume() {
        super.onResume()
        lifecycleSubject.onNext(FragmentEvent.RESUME)
    }

    override fun onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE)
        super.onPause()
    }

    override fun onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP)
        super.onStop()
    }

    override fun pop() {
        super.pop()
        KeyboardUtils.hideSoftInput(_mActivity!!)
    }

    override fun onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW)
        super.onDestroyView()
    }

    override fun onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY)
        super.onDestroy()
    }

    override fun onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH)
        super.onDetach()
    }
}
