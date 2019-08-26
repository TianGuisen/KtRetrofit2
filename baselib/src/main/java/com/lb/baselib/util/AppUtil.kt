package com.lb.baselib.util

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.lb.baselib.ToolApplication.appContext

object AppUtil {

    /**
     * 网络是否链接
     */
    fun isConnected(): Boolean {
        (appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo.also {
            if (null != it && it.isConnected) {
                if (it.state == NetworkInfo.State.CONNECTED) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * 获得values里的colors
     */
    fun getColor(colorId: Int) = ContextCompat.getColor(appContext, colorId)

    /**
     * 设备id
     */
    val deviceId: String
        get() = Settings.Secure.getString(appContext.contentResolver, Settings.Secure.ANDROID_ID)

    /**
     * 拨打电话,需要权限
     */
    fun callPhone(num: String) {
        val intent = Intent(Intent.ACTION_CALL)
        val data = Uri.parse("tel:${num}")
        intent.data = data
        appContext.startActivity(intent)
    }

}
