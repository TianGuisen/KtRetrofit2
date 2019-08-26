package com.lb.baselib.retrofit


data class ResWrapper<out T>(val code: Int = -1,val message: String,val data: T?)