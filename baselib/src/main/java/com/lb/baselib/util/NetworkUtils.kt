package com.lb.baselib.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Proxy
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.telephony.TelephonyManager
import android.text.TextUtils


/**
 * @author 田桂森 2019/7/5
 */
object NetworkUtil {

    val TAG = "NetworkUtil"

    val CURRENT_NETWORK_TYPE_NONE: Byte = 0

    /*
     * 根据APN区分网络类型
     */
    val CURRENT_NETWORK_TYPE_WIFI: Byte = 1// wifi

    val CURRENT_NETWORK_TYPE_CTNET: Byte = 2// ctnet

    val CURRENT_NETWORK_TYPE_CTWAP: Byte = 3// ctwap

    val CURRENT_NETWORK_TYPE_CMWAP: Byte = 4// cmwap

    val CURRENT_NETWORK_TYPE_UNIWAP: Byte = 5// uniwap,3gwap

    val CURRENT_NETWORK_TYPE_CMNET: Byte = 6// cmnet

    val CURRENT_NETWORK_TYPE_UNIET: Byte = 7// uninet,3gnet

    /**
     * 根据运营商区分网络类型
     */
    val CURRENT_NETWORK_TYPE_CTC: Byte = 10// ctwap,ctnet

    val CURRENT_NETWORK_TYPE_CUC: Byte = 11// uniwap,3gwap,uninet,3gnet

    val CURRENT_NETWORK_TYPE_CM: Byte = 12// cmwap,cmnet

    /**
     * apn值
     */
    private val CONNECT_TYPE_WIFI = "wifi"

    private val CONNECT_TYPE_CTNET = "ctnet"

    private val CONNECT_TYPE_CTWAP = "ctwap"

    private val CONNECT_TYPE_CMNET = "cmnet"

    private val CONNECT_TYPE_CMWAP = "cmwap"

    private val CONNECT_TYPE_UNIWAP = "uniwap"

    private val CONNECT_TYPE_UNINET = "uninet"

    private val CONNECT_TYPE_UNI3GWAP = "3gwap"

    private val CONNECT_TYPE_UNI3GNET = "3gnet"

    private val PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn")

    var curNetworkType = CURRENT_NETWORK_TYPE_NONE

    /**
     * 当网络为wifi时,直接返回空代理: 当ctwap,cmwap,uniwap,3gwap开启时同时开启wifi网络
     * ,通过下面的getDefaultHost接口将得到对应wap网络代理ip ,这是错误的,所以在此判断当前网络是否为wifi
     */
    val networkProxyUrl: String?
        get() {
            if (curNetworkType == CURRENT_NETWORK_TYPE_WIFI) {
                return null
            }
            val proxyHost = android.net.Proxy.getDefaultHost()
            return proxyHost
        }

    val networkProxyPort: Int
        get() = Proxy.getDefaultPort()

    /**
     * 是否需要设置代理(网络请求,一般用于wap网络,但有些机型设置代理会导致系统异常)
     *
     * @return
     */
    // #00044 +
    val isNeedSetProxyForNetRequest: Boolean
        get() = if (Build.MODEL == "SCH-N719" || Build.MODEL == "SCH-I939D") {
            false
        } else {
            true
        }

    /**
     * 网络类型
     */
    val NETWORK_CLASS_UNKNOWN = 0

    val NETWORK_CLASS_2_G = 1

    val NETWORK_CLASS_3_G = 2

    val NETWORK_CLASS_4_G = 3

    val NETWORK_CLASS_WIFI = 10

    //中国电信
    val ISP_CTCC = 0
    //中国联通
    val ISP_CUCC = 1
    //中国移动
    val ISP_CMCC = 2
    //中国铁通
    val ISP_CTT = 3
    //其他
    val ISP_OTHERS = -1

    /*
     *
     * 获取网络类型
     *
     */
    fun getNetType(context: Context): Int {
        val networkInfo = getActiveNetworkInfo(context)
        return networkInfo?.type ?: -1
    }


    /**
     * 判断当前网络类型。WIFI,NET,WAP
     *
     * @param context
     * @return
     */
    fun getCurrentNetType(context: Context): Byte {
        val networkInfo = getActiveNetworkInfo(context)
        var type = CURRENT_NETWORK_TYPE_NONE
        if (networkInfo != null) {
            // String typeName = networkInfo.getTypeName();
            // XT800
            var typeName = networkInfo.extraInfo
            if (TextUtils.isEmpty(typeName)) {
                typeName = networkInfo.typeName
            }
            if (!TextUtils.isEmpty(typeName)) {
                val temp = typeName.toLowerCase()
                if (temp.indexOf(CONNECT_TYPE_WIFI) > -1) {// wifi
                    type = CURRENT_NETWORK_TYPE_WIFI
                } else if (temp.indexOf(CONNECT_TYPE_CTNET) > -1) {// ctnet
                    type = CURRENT_NETWORK_TYPE_CTNET
                } else if (temp.indexOf(CONNECT_TYPE_CTWAP) > -1) {// ctwap
                    type = CURRENT_NETWORK_TYPE_CTWAP
                } else if (temp.indexOf(CONNECT_TYPE_CMNET) > -1) {// cmnet
                    type = CURRENT_NETWORK_TYPE_CMNET
                } else if (temp.indexOf(CONNECT_TYPE_CMWAP) > -1) {// cmwap
                    type = CURRENT_NETWORK_TYPE_CMWAP
                } else if (temp.indexOf(CONNECT_TYPE_UNIWAP) > -1) {// uniwap
                    type = CURRENT_NETWORK_TYPE_UNIWAP
                } else if (temp.indexOf(CONNECT_TYPE_UNI3GWAP) > -1) {// 3gwap
                    type = CURRENT_NETWORK_TYPE_UNIWAP
                } else if (temp.indexOf(CONNECT_TYPE_UNINET) > -1) {// uninet
                    type = CURRENT_NETWORK_TYPE_UNIET
                } else if (temp.indexOf(CONNECT_TYPE_UNI3GNET) > -1) {// 3gnet
                    type = CURRENT_NETWORK_TYPE_UNIET
                }
            }
        }

        if (type == CURRENT_NETWORK_TYPE_NONE) {
            val apnType = getApnType(context)
            if (apnType != null && apnType == CONNECT_TYPE_CTNET) {// ctnet
                type = CURRENT_NETWORK_TYPE_CTNET
            } else if (apnType != null && apnType == CONNECT_TYPE_CTWAP) {// ctwap
                type = CURRENT_NETWORK_TYPE_CTWAP
            } else if (apnType != null && apnType == CONNECT_TYPE_CMWAP) {// cmwap
                type = CURRENT_NETWORK_TYPE_CMWAP
            } else if (apnType != null && apnType == CONNECT_TYPE_CMNET) {// cmnet
                type = CURRENT_NETWORK_TYPE_CMNET
            } else if (apnType != null && apnType == CONNECT_TYPE_UNIWAP) {// uniwap
                type = CURRENT_NETWORK_TYPE_UNIWAP
            } else if (apnType != null && apnType == CONNECT_TYPE_UNI3GWAP) {// 3gwap
                type = CURRENT_NETWORK_TYPE_UNIWAP
            } else if (apnType != null && apnType == CONNECT_TYPE_UNINET) {// uninet
                type = CURRENT_NETWORK_TYPE_UNIET
            } else if (apnType != null && apnType == CONNECT_TYPE_UNI3GNET) {// 3gnet
                type = CURRENT_NETWORK_TYPE_UNIET
            }
        }
        curNetworkType = type

        return type
    }

    /**
     * 判断APNTYPE
     *
     * @param context
     * @return
     */

    @Deprecated(
        "4.0\n" +
                "      doc:\n" +
                "      Since the DB may contain corp passwords, we should secure it. Using the same permission as writing to the DB as the read is potentially as damaging as a write"
    )
    fun getApnType(context: Context): String {

        var apntype = "nomatch"
        var c = context.getContentResolver().query(PREFERRED_APN_URI, null, null, null, null)
        if (c != null) {
            if (c!!.moveToFirst()) {
                val user = c!!.getString(c!!.getColumnIndex("user"))
                if (user != null && user!!.startsWith(CONNECT_TYPE_CTNET)) {
                    apntype = CONNECT_TYPE_CTNET
                } else if (user != null && user!!.startsWith(CONNECT_TYPE_CTWAP)) {
                    apntype = CONNECT_TYPE_CTWAP
                } else if (user != null && user!!.startsWith(CONNECT_TYPE_CMWAP)) {
                    apntype = CONNECT_TYPE_CMWAP
                } else if (user != null && user!!.startsWith(CONNECT_TYPE_CMNET)) {
                    apntype = CONNECT_TYPE_CMNET
                } else if (user != null && user!!.startsWith(CONNECT_TYPE_UNIWAP)) {
                    apntype = CONNECT_TYPE_UNIWAP
                } else if (user != null && user!!.startsWith(CONNECT_TYPE_UNINET)) {
                    apntype = CONNECT_TYPE_UNINET
                } else if (user != null && user!!.startsWith(CONNECT_TYPE_UNI3GWAP)) {
                    apntype = CONNECT_TYPE_UNI3GWAP
                } else if (user != null && user!!.startsWith(CONNECT_TYPE_UNI3GNET)) {
                    apntype = CONNECT_TYPE_UNI3GNET
                }
            }
            c!!.close()
            c = null
        }

        return apntype
    }

    /**
     * 判断是否有网络可用
     *
     * @param context
     * @return
     */
    fun isNetAvailable(context: Context): Boolean {
        val networkInfo = getActiveNetworkInfo(context)
        return networkInfo?.isAvailable ?: false
    }

    /**
     * 此判断不可靠
     *
     * @param context
     * @return
     */
    fun isNetworkConnected(context: Context): Boolean {
        val networkInfo = getActiveNetworkInfo(context)
        return networkInfo?.isConnected ?: false
    }

    /**
     * 获取可用的网络信息
     *
     * @param context
     * @return
     */
    private fun getActiveNetworkInfo(context: Context): NetworkInfo? {
        try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo
        } catch (e: Exception) {
            return null
        }

    }

    fun isWifiOr3G(context: Context): Boolean {
        return if (isWifi(context)) {
            true
        } else {
            is3G(context)
        }
    }

    fun is2G(context: Context): Boolean {
        return !isWifiOr3G(context)
    }

    fun is3G(context: Context): Boolean {
        val type = getNetworkClass(context)
        return if (type == NETWORK_CLASS_3_G || type == NETWORK_CLASS_4_G) {
            true
        } else {
            false
        }
    }

    /**
     * 当前网络是否是wifi网络
     *
     * @param context
     * @return
     */
    fun isWifi(context: Context): Boolean {
        try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val ni = cm.activeNetworkInfo
            return if (ni != null) {
                if (ni.type == ConnectivityManager.TYPE_WIFI) {
                    true
                } else {
                    false
                }
            } else {
                false
            }
        } catch (e: Exception) {
            return false
        }

    }

    fun getNetworkConnectionStatus(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager ?: return false

        val info = manager.activeNetworkInfo ?: return false

        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager ?: return false

        return if ((tm.dataState == TelephonyManager.DATA_CONNECTED || tm.dataState == TelephonyManager.DATA_ACTIVITY_NONE) && info.isAvailable) {
            true
        } else {
            false
        }
    }

    fun getNetworkProxyInfo(context: Context): String? {
        val proxyHost = android.net.Proxy.getDefaultHost()
        val proxyPort = android.net.Proxy.getDefaultPort()
        val szport = proxyPort.toString()
        var proxyInfo: String? = null

        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (manager == null) {
            return null
        } else {
            val info = manager.activeNetworkInfo
            if (info != null) {
                val typeName = info.typeName.toLowerCase()
                if (typeName != null && typeName == "wifi") {
                    return null
                }
            } else {
                return null
            }
        }

        if (proxyHost != null && 0 < proxyPort && proxyPort < 65535) {
            proxyInfo = "$proxyHost:$szport"
            return proxyInfo
        } else {
            return null
        }
    }

    fun getNetworkProxyUrl(context: Context): String? {
        if (isWifi(context)) {
            return null
        }

        val proxyHost = android.net.Proxy.getDefaultHost()
        return proxyHost
    }

    fun isCtwap(context: Context): Boolean {
        return if (getApnType(context) == CONNECT_TYPE_CTWAP) {
            true
        } else {
            false
        }
    }

    fun isUniwap(context: Context): Boolean {
        return if (getApnType(context) == CONNECT_TYPE_UNIWAP) {
            true
        } else {
            false
        }
    }

    fun isCmwap(context: Context): Boolean {
        return if (getApnType(context) == CONNECT_TYPE_CMWAP) {
            true
        } else {
            false
        }
    }

    /**
     * 判断是否是电信网络(ctwap,ctnet)
     *
     * @return
     */
    fun isCtcNetwork(context: Context): Boolean {
        val type = getCurrentNetType(context)

        return isCtcNetwork(type)
    }

    fun isCtcNetwork(apnName: String?): Boolean {
        if (apnName == null) {
            return false
        }

        return if (apnName == CONNECT_TYPE_CTWAP || apnName == CONNECT_TYPE_CTNET) {
            true
        } else {
            false
        }
    }

    fun isCtcNetwork(type: Byte): Boolean {
        return if (type == CURRENT_NETWORK_TYPE_CTWAP || type == CURRENT_NETWORK_TYPE_CTNET) {
            true
        } else {
            false
        }
    }

    /**
     * 判断是否是联通网络(uniwap,uninet,3gwap,3gnet)
     *
     * @return
     */
    fun isCucNetwork(context: Context): Boolean {
        val type = getCurrentNetType(context)

        return isCucNetwork(type)
    }

    fun isCucNetwork(apnName: String?): Boolean {
        if (apnName == null) {
            return false
        }

        return if (apnName == CONNECT_TYPE_UNIWAP || apnName == CONNECT_TYPE_UNINET
            || apnName == CONNECT_TYPE_UNI3GWAP || apnName == CONNECT_TYPE_UNI3GNET
        ) {
            true
        } else {
            false
        }
    }

    fun isCucNetwork(type: Byte): Boolean {
        return if (type == CURRENT_NETWORK_TYPE_UNIWAP || type == CURRENT_NETWORK_TYPE_UNIET) {
            true
        } else {
            false
        }
    }

    /**
     * 判断是否是移动网络(cmwap,cmnet)
     *
     * @return
     */
    fun isCmbNetwork(context: Context): Boolean {
        val type = getCurrentNetType(context)

        return isCmbNetwork(type)
    }

    fun isCmbNetwork(apnName: String?): Boolean {
        if (apnName == null) {
            return false
        }

        return if (apnName == CONNECT_TYPE_CMWAP || apnName == CONNECT_TYPE_CMNET) {
            true
        } else {
            false
        }
    }

    fun isCmbNetwork(type: Byte): Boolean {
        return if (type == CURRENT_NETWORK_TYPE_CMWAP || type == CURRENT_NETWORK_TYPE_CMNET) {
            true
        } else {
            false
        }
    }

    /**
     * 获取网络运营商类型(中国移动,中国联通,中国电信,wifi)
     *
     * @param context
     * @return
     */
    fun getNetworkOperators(context: Context): Byte {
        return if (isWifi(context)) {
            CURRENT_NETWORK_TYPE_WIFI
        } else if (isCtcNetwork(context)) {
            CURRENT_NETWORK_TYPE_CTC
        } else if (isCmbNetwork(context)) {
            CURRENT_NETWORK_TYPE_CM
        } else if (isCucNetwork(context)) {
            CURRENT_NETWORK_TYPE_CUC
        } else {
            CURRENT_NETWORK_TYPE_NONE
        }
    }

    fun getNetworkOperators(type: Byte): Byte {
        return if (type == CURRENT_NETWORK_TYPE_NONE) {
            CURRENT_NETWORK_TYPE_NONE
        } else if (type == CURRENT_NETWORK_TYPE_WIFI) {
            CURRENT_NETWORK_TYPE_WIFI
        } else if (type == CURRENT_NETWORK_TYPE_CTNET || type == CURRENT_NETWORK_TYPE_CTWAP) {
            CURRENT_NETWORK_TYPE_CTC
        } else if (type == CURRENT_NETWORK_TYPE_CMWAP || type == CURRENT_NETWORK_TYPE_CMNET) {
            CURRENT_NETWORK_TYPE_CM
        } else if (type == CURRENT_NETWORK_TYPE_UNIWAP || type == CURRENT_NETWORK_TYPE_UNIET) {
            CURRENT_NETWORK_TYPE_CUC
        } else {
            CURRENT_NETWORK_TYPE_NONE
        }
    }

    /**
     * get mac address of wifi if wifi is active
     */

    fun getActiveMacAddress(context: Context): String {
        val wifi = context.getSystemService(Context.WIFI_SERVICE) as WifiManager

        val info = wifi.connectionInfo

        return if (info != null) {
            info.macAddress
        } else ""

    }

    fun getNetworkInfo(context: Context): String {
        var info = ""
        val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity != null) {
            val activeNetInfo = connectivity.activeNetworkInfo
            if (activeNetInfo != null) {
                if (activeNetInfo.type == ConnectivityManager.TYPE_WIFI) {
                    info = activeNetInfo.typeName
                } else {
                    val sb = StringBuilder()
                    val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                    sb.append(activeNetInfo.typeName)
                    sb.append(" [")
                    if (tm != null) {
                        // Result may be unreliable on CDMA networks
                        sb.append(tm.networkOperatorName)
                        sb.append("#")
                    }
                    sb.append(activeNetInfo.subtypeName)
                    sb.append("]")
                    info = sb.toString()
                }
            }
        }
        return info
    }

    enum class NetworkSpeedMode {
        LOW, NORMAL, HIGH, UNKNOWN
    }

    /**
     * 仅判断Mobile网络的慢速.蓝牙等其他网络不做判断.
     *
     * @param context
     * @return
     */
    fun getNetworkSpeedModeInMobile(context: Context): NetworkSpeedMode {
        val connectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val networkInfo = connectivityManager.activeNetworkInfo
            if (networkInfo != null) {
                if (networkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                    when (networkInfo.subtype) {
                        TelephonyManager.NETWORK_TYPE_IDEN // ~25 kbps
                        -> return NetworkSpeedMode.LOW
                        TelephonyManager.NETWORK_TYPE_CDMA // ~ 14-64 kbps
                        -> return NetworkSpeedMode.LOW
                        TelephonyManager.NETWORK_TYPE_1xRTT // ~ 50-100 kbps
                        -> return NetworkSpeedMode.LOW
                        TelephonyManager.NETWORK_TYPE_EDGE // ~ 50-100 kbps
                        -> return NetworkSpeedMode.LOW
                        TelephonyManager.NETWORK_TYPE_GPRS // ~ 100 kbps
                        -> return NetworkSpeedMode.LOW

                        TelephonyManager.NETWORK_TYPE_EVDO_0 // ~ 400-1000
                        ->
                            // kbps
                            return NetworkSpeedMode.NORMAL
                        TelephonyManager.NETWORK_TYPE_EVDO_A // ~ 600-1400
                        ->
                            // kbps
                            return NetworkSpeedMode.NORMAL
                        TelephonyManager.NETWORK_TYPE_HSPA // ~ 700-1700 kbps
                        -> return NetworkSpeedMode.NORMAL
                        TelephonyManager.NETWORK_TYPE_UMTS // ~ 400-7000 kbps
                        -> return NetworkSpeedMode.NORMAL
                        14 // TelephonyManager.NETWORK_TYPE_EHRPD: // ~ 1-2
                        ->
                            // Mbps
                            return NetworkSpeedMode.NORMAL
                        12 // TelephonyManager.NETWORK_TYPE_EVDO_B: // ~ 5
                        ->
                            // Mbps
                            return NetworkSpeedMode.NORMAL

                        TelephonyManager.NETWORK_TYPE_HSDPA // ~ 2-14 Mbps
                        -> return NetworkSpeedMode.HIGH
                        TelephonyManager.NETWORK_TYPE_HSUPA // ~ 1-23 Mbps
                        -> return NetworkSpeedMode.HIGH
                        15 // TelephonyManager.NETWORK_TYPE_HSPAP: // ~ 10-20
                        ->
                            // Mbps
                            return NetworkSpeedMode.HIGH
                        13 // TelephonyManager.NETWORK_TYPE_LTE: // ~ 10+ Mbps
                        -> return NetworkSpeedMode.HIGH

                        TelephonyManager.NETWORK_TYPE_UNKNOWN -> return NetworkSpeedMode.NORMAL
                        else -> {
                        }
                    }
                }
            }
        }
        return NetworkSpeedMode.UNKNOWN
    }

    /**
     * 获取在Mobile网络下的网络类型. 2G,3G,4G
     *
     * @param context
     * @return
     */
    fun getNetworkClass(context: Context): Int {
        val connectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val networkInfo = connectivityManager.activeNetworkInfo
            if (networkInfo != null) {
                if (networkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                    when (networkInfo.subtype) {
                        TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN -> return NETWORK_CLASS_2_G
                        TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA, 12 // TelephonyManager.NETWORK_TYPE_EVDO_B:
                            , 14 // TelephonyManager.NETWORK_TYPE_EHRPD:
                            , 15 // TelephonyManager.NETWORK_TYPE_HSPAP:
                        -> return NETWORK_CLASS_3_G
                        13 // TelephonyManager.NETWORK_TYPE_LTE:
                        -> return NETWORK_CLASS_4_G
                        else -> return NETWORK_CLASS_UNKNOWN
                    }
                } else if (networkInfo.type == ConnectivityManager.TYPE_WIFI) {
                    return NETWORK_CLASS_WIFI
                }
            }
        }
        return NETWORK_CLASS_UNKNOWN
    }

    fun getNetworkTypeName(context: Context): String {
        var networkName = "UNKNOWN"
        val connectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val networkInfo = connectivityManager.activeNetworkInfo
            if (networkInfo != null) {
                networkName = getNetworkTypeName(networkInfo.type)
                if (networkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                    networkName += "#" + getNetworkTypeNameInMobile(networkInfo.subtype)
                }
            }
        }
        return networkName
    }

    private fun getNetworkTypeNameInMobile(type: Int): String {
        when (type) {
            TelephonyManager.NETWORK_TYPE_GPRS -> return "GPRS"
            TelephonyManager.NETWORK_TYPE_EDGE -> return "EDGE"
            TelephonyManager.NETWORK_TYPE_UMTS -> return "UMTS"
            TelephonyManager.NETWORK_TYPE_HSDPA -> return "HSDPA"
            TelephonyManager.NETWORK_TYPE_HSUPA -> return "HSUPA"
            TelephonyManager.NETWORK_TYPE_HSPA -> return "HSPA"
            TelephonyManager.NETWORK_TYPE_CDMA -> return "CDMA"
            TelephonyManager.NETWORK_TYPE_EVDO_0 -> return "CDMA - EvDo rev. 0"
            TelephonyManager.NETWORK_TYPE_EVDO_A -> return "CDMA - EvDo rev. A"
            TelephonyManager.NETWORK_TYPE_EVDO_B -> return "CDMA - EvDo rev. B"
            TelephonyManager.NETWORK_TYPE_1xRTT -> return "CDMA - 1xRTT"
            TelephonyManager.NETWORK_TYPE_LTE -> return "LTE"
            TelephonyManager.NETWORK_TYPE_EHRPD -> return "CDMA - eHRPD"
            TelephonyManager.NETWORK_TYPE_IDEN -> return "iDEN"
            TelephonyManager.NETWORK_TYPE_HSPAP -> return "HSPA+"
            else -> return "UNKNOWN"
        }
    }

    private fun getNetworkTypeName(type: Int): String {
        when (type) {
            ConnectivityManager.TYPE_MOBILE -> return "MOBILE"
            ConnectivityManager.TYPE_WIFI -> return "WIFI"
            ConnectivityManager.TYPE_MOBILE_MMS -> return "MOBILE_MMS"
            ConnectivityManager.TYPE_MOBILE_SUPL -> return "MOBILE_SUPL"
            ConnectivityManager.TYPE_MOBILE_DUN -> return "MOBILE_DUN"
            ConnectivityManager.TYPE_MOBILE_HIPRI -> return "MOBILE_HIPRI"
            ConnectivityManager.TYPE_WIMAX -> return "WIMAX"
            ConnectivityManager.TYPE_BLUETOOTH -> return "BLUETOOTH"
            ConnectivityManager.TYPE_DUMMY -> return "DUMMY"
            ConnectivityManager.TYPE_ETHERNET -> return "ETHERNET"
            10 // ConnectivityManager.TYPE_MOBILE_FOTA:
            -> return "MOBILE_FOTA"
            11 // ConnectivityManager.TYPE_MOBILE_IMS:
            -> return "MOBILE_IMS"
            12 // ConnectivityManager.TYPE_MOBILE_CBS:
            -> return "MOBILE_CBS"
            13 // ConnectivityManager.TYPE_WIFI_P2P:
            -> return "WIFI_P2P"
            else -> return Integer.toString(type)
        }
    }

    fun getSimOperator(context: Context): String? {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return tm?.simOperator
    }

    fun getNetworkOperator(context: Context): String? {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return tm?.networkOperator
    }


    interface LinkNetWorkType {
        companion object {
            val UNKNOWN = 0
            val WIFI = 1
            val WWAN = 2
            val _2G = 3
            val _3G = 4
            val _4G = 5
        }
    }

    fun getNetworkTypeForLink(context: Context): Int {
        try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val ni = cm.activeNetworkInfo
            if (ni != null) {
                if (ni.type == ConnectivityManager.TYPE_WIFI) {
                    return LinkNetWorkType.WIFI
                } else {
                    if (ni.type == ConnectivityManager.TYPE_MOBILE) {
                        when (ni.subtype) {
                            TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN -> return LinkNetWorkType._2G
                            TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA, 12 // TelephonyManager.NETWORK_TYPE_EVDO_B:
                                , 14 // TelephonyManager.NETWORK_TYPE_EHRPD:
                                , 15 // TelephonyManager.NETWORK_TYPE_HSPAP:
                            -> return LinkNetWorkType._3G
                            13 // TelephonyManager.NETWORK_TYPE_LTE:
                            -> return LinkNetWorkType._4G
                            else -> return LinkNetWorkType._2G
                        }
                    }
                }
            }
        } catch (e: Exception) {
            return LinkNetWorkType.UNKNOWN
        }

        return LinkNetWorkType.UNKNOWN
    }
}