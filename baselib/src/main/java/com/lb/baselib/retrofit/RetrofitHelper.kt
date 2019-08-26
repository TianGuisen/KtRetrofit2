package com.lb.baselib.retrofit

import android.annotation.SuppressLint
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 订阅在io,回调在main
 * 传入fragment会绑定生命周期在destroy的时候取消订阅
 */
fun <T> ioMain(life: LifecycleProvider<FragmentEvent>): ObservableTransformer<T, T> {
    return object : ObservableTransformer<T, T> {
        @SuppressLint("CheckResult")
        override fun apply(upstream: Observable<T>): ObservableSource<T> {
            return upstream
                    .compose(life.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}

/**
 * 订阅在io,回调在io
 * 传入fragment会绑定生命周期在destroy的时候取消订阅
 */
fun <T> io(life: LifecycleProvider<FragmentEvent>): ObservableTransformer<T, T> {
    return object : ObservableTransformer<T, T> {
        @SuppressLint("CheckResult")
        override fun apply(upstream: Observable<T>): ObservableSource<T> {
            return upstream
                    .compose(life.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
        }
    }
}

/**
 * 订阅在io,回调在main
 * 传入fragment会绑定生命周期在destroy的时候取消订阅
 */
fun <T> ioMainAct(act: LifecycleProvider<ActivityEvent>): ObservableTransformer<T, T> {
    return object : ObservableTransformer<T, T> {
        @SuppressLint("CheckResult")
        override fun apply(upstream: Observable<T>): ObservableSource<T> {
            return upstream
                    .compose(act.bindUntilEvent(ActivityEvent.DESTROY))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}

/**
 * 订阅在io,回调在io
 * 传入fragment会绑定生命周期在destroy的时候取消订阅
 */
fun <T> ioAct(act: LifecycleProvider<ActivityEvent>): ObservableTransformer<T, T> {
    return object : ObservableTransformer<T, T> {
        @SuppressLint("CheckResult")
        override fun apply(upstream: Observable<T>): ObservableSource<T> {
            return upstream
                    .compose(act.bindUntilEvent(ActivityEvent.DESTROY))
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
        }
    }
}

