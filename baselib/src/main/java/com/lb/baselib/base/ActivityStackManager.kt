package com.lb.baselib.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.util.*

/**
 * @author 田桂森 2019/5/16
 */
object ActivityStackManager : Application.ActivityLifecycleCallbacks {


    private val stack: Stack<Activity>

    init {
        stack = Stack()
    }

    fun register(app: Application) {
        app.registerActivityLifecycleCallbacks(this)
    }

    fun unRegister(app: Application) {
        app.unregisterActivityLifecycleCallbacks(this)
    }

    /**
     * @param activity 需要添加进栈管理的activity
     */
    fun addActivity(activity: Activity?) {
        stack.add(activity)
    }

    fun getSize ()= stack.size

    /**
     * @param activity 需要从栈管理中删除的activity
     * @return
     */
    fun removeActivity(activity: Activity?): Boolean {
        return stack.remove(activity)
    }

    /**
     * @param activity 查询指定activity在栈中的位置，从栈顶开始
     * @return
     */
    fun searchActivity(activity: Activity): Int {
        return stack.search(activity)
    }

    /**
     * @param activity 将指定的activity从栈中删除然后finish()掉
     */
    fun finishActivity(activity: Activity) {
        stack.pop().finish()
    }

    /**
     * @param activity 将指定类名的activity从栈中删除并finish()掉
     */
    fun finishActivityClass(activity: Class<Activity>?) {
        if (activity != null) {
            val iterator = stack.iterator()
            while (iterator.hasNext()) {
                val next = iterator.next()
                if (next.javaClass == activity) {
                    iterator.remove()
                    finishActivity(next)
                }
            }
        }
    }

    /**
     * 销毁所有的activity
     */
    fun finishAllActivity() {
        while (!stack.isEmpty()) {
            stack.pop().finish()
        }
    }

    override fun onActivityDestroyed(activity: Activity?) {
        removeActivity(activity)
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        addActivity(activity)
    }

    override fun onActivityPaused(activity: Activity?) {

    }

    override fun onActivityResumed(activity: Activity?) {

    }

    override fun onActivityStarted(activity: Activity?) {

    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {

    }

    override fun onActivityStopped(activity: Activity?) {

    }
}