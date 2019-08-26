package com.lb.baselib.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.lb.baselib.BR
import com.lb.baselib.util.ProgressDiaUtil

/**
 * 复杂页面继承这个
 */
abstract class BaseVMFra<D : ViewDataBinding> : BaseFra<D>() {

    private lateinit var vm: BaseVM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        vm = setVM()
        bind.setVariable(BR.vm, vm)
        vm.onCreateView()
        return bind.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.onViewCreated()
        vm.showLoading.observe(this, Observer {
            if (it) {
                ProgressDiaUtil.show(context!!)
            } else {
                ProgressDiaUtil.dismiss()
            }
        })

    }

    abstract fun setVM(): BaseVM

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        vm.onLazyInitView(savedInstanceState)
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        vm.onSupportVisible()
    }

    override fun onStart() {
        super.onStart()
        vm.onStart()
    }

    override fun onResume() {
        super.onResume()
        vm.onResume()
    }

    override fun onPause() {
        super.onPause()
        vm.onPause()
    }

    override fun onStop() {
        super.onStop()
        vm.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        vm.onDestroyView()
    }
    override fun onDestroy() {
        vm.onDestroy()
        super.onDestroy()
    }
}

