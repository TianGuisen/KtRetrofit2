package com.lb.baselib.util

import android.app.Activity
import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.alibaba.fastjson.JSON

fun Any.toJson(): String = JSON.toJSONString(this)

/**
 * 获取泛型的.class
 * 以reified修饰类型后，就能够在函数内部使用相关类型。
 */
inline fun <reified T> classOf() = T::class.java

//以reified修饰类型后，就能够在函数内部使用相关类型。
private inline fun <reified T : Activity> Activity.startActivity() {
    startActivity(Intent(this, T::class.java))
}