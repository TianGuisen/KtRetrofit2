package com.lb.baselib.util

import com.lb.baselib.ToolApplication.appContext


fun Int.dp2px() = (appContext.getResources().getDisplayMetrics().density * this + 0.5).toInt()

fun Int.px2dp() = (this / (appContext.getResources().getDisplayMetrics().density) + 0.5).toInt()
