package com.lb.baselib.base

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.android.RxLifecycleAndroid
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

abstract class BaseVM : ViewModel(), LifecycleProvider<FragmentEvent> {

    public open val defLoadState = MutableLiveData<Boolean>()
    public open val showLoading = MutableLiveData<Boolean>()
    private val lifecycleSubject = BehaviorSubject.create<FragmentEvent>()

    open fun onCreateView() {
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
    }

    open fun onViewCreated() {

    }

    open fun onLazyInitView(savedInstanceState: Bundle?) {

    }

    open fun onStart() {
        lifecycleSubject.onNext(FragmentEvent.START)
    }

    open fun onResume() {
        lifecycleSubject.onNext(FragmentEvent.RESUME)

    }

    open fun onSupportVisible() {

    }

    override fun lifecycle(): Observable<FragmentEvent> {
        return lifecycleSubject.hide()
    }

    override fun <T> bindUntilEvent(event: FragmentEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event)
    }

    override fun <T> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject)
    }
    

    open fun onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE)

    }

    open fun onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP)
    }

    open fun onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW)
    }

    open fun onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY)
    }


}
 