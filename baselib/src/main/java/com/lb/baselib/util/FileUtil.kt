package com.lb.baselib.util


import android.os.Environment
import com.lb.baselib.ToolApplication
import java.io.File


object FileUtil {
    /**
     * 分隔符.
     */
    val FILE_EXTENSION_SEPARATOR = "."

    /**
     * "/"
     */
    val SEP = File.separator

    /**
     * SD卡根目录
     */
    val SDPATH = Environment.getExternalStorageDirectory().toString() + File.separator

    /**
     * 判断SD卡是否可用
     *
     * @return SD卡可用返回true
     */
    fun hasSdcard(): Boolean {
        val status = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == status
    }

    fun getDirImage(): String {
        return getFileDir() + "/image"
    }

    fun getDirAPK(): String {
        return getFileDir() + "/apk"
    }

    /**
     * 获取缓存路径
     *
     * @return
     */
    private fun getFileDir(): String {
        val cache_path: String
        if (ToolApplication.appContext.getExternalFilesDir(null) != null) {
            cache_path = ToolApplication.appContext.getExternalFilesDir(null)!!.getAbsolutePath()
        } else {
            cache_path = Environment.getExternalStorageDirectory().absolutePath + "/Android/data/" + ToolApplication.appContext.getPackageName() + "/files"
        }
        return cache_path
    }

    /**
     * 获取文件路径
     *
     * @return
     */
    private fun getCacheDir(): String {
        val cache_path: String
        if (ToolApplication.appContext.getExternalFilesDir(null) != null) {
            cache_path = ToolApplication.appContext.getExternalCacheDir()!!.getAbsolutePath()
        } else {
            cache_path = Environment.getExternalStorageDirectory().absolutePath + "/Android/data/" + ToolApplication.appContext.getPackageName() + "/cache"
        }
        return cache_path
    }
}

