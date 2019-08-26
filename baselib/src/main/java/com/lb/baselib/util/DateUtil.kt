package com.lb.baselib.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * <h3>日期工具类</h3>
 *
 * 主要实现了日期的常用操作
 */
object DateUtil {

    /**
     * yyyy-MM-dd HH:mm:ss字符串
     */
    val DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"

    /**
     * yyyy-MM-dd字符串
     */
    val DEFAULT_FORMAT_DATE = "yyyy-MM-dd"

    /**
     * HH:mm:ss字符串
     */
    val DEFAULT_FORMAT_TIME = "HH:mm:ss"

    /**
     * yyyy-MM-dd HH:mm:ss格式
     */
    val defaultDateTimeFormat: ThreadLocal<SimpleDateFormat> = object : ThreadLocal<SimpleDateFormat>() {

        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT)
        }

    }

    /**
     * yyyy-MM-dd格式
     */
    val defaultDateFormat: ThreadLocal<SimpleDateFormat> = object : ThreadLocal<SimpleDateFormat>() {

        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat(DEFAULT_FORMAT_DATE)
        }

    }

    /**
     * HH:mm:ss格式
     */
    val defaultTimeFormat: ThreadLocal<SimpleDateFormat> = object : ThreadLocal<SimpleDateFormat>() {

        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat(DEFAULT_FORMAT_TIME)
        }

    }

    /**
     * 将long时间转成yyyy-MM-dd HH:mm:ss字符串<br></br>
     *
     * @param timeInMillis 时间long值
     * @return yyyy-MM-dd HH:mm:ss
     */
    fun getDateTimeFromMillis(timeInMillis: Long): String {
        return getDateTimeFormat(Date(timeInMillis))
    }

    /**
     * 将long时间转成yyyy-MM-dd字符串<br></br>
     *
     * @param timeInMillis
     * @return yyyy-MM-dd
     */
    fun getDateFromMillis(timeInMillis: Long): String {
        return getDateFormat(Date(timeInMillis))
    }

    /**
     * 将date转成yyyy-MM-dd HH:mm:ss字符串
     * <br></br>
     *
     * @param date Date对象
     * @return yyyy-MM-dd HH:mm:ss
     */
    fun getDateTimeFormat(date: Date): String {
        return dateSimpleFormat(date, defaultDateTimeFormat.get())
    }

    /**
     * 将年月日的int转成yyyy-MM-dd的字符串
     *
     * @param year  年
     * @param month 月 1-12
     * @param day   日
     * 注：月表示Calendar的月，比实际小1
     * 对输入项未做判断
     */
    fun getDateFormat(year: Int, month: Int, day: Int): String {
        return getDateFormat(getDate(year, month, day))
    }

    /**
     * 将date转成yyyy-MM-dd字符串<br></br>
     *
     * @param date Date对象
     * @return yyyy-MM-dd
     */
    fun getDateFormat(date: Date): String {
        return dateSimpleFormat(date, defaultDateFormat.get())
    }

    /**
     * 获得HH:mm:ss的时间
     *
     * @param date
     * @return
     */
    fun getTimeFormat(date: Date): String {
        return dateSimpleFormat(date, defaultTimeFormat.get())
    }

    /**
     * 格式化日期显示格式
     *
     * @param sdate  原始日期格式 "yyyy-MM-dd"
     * @param format 格式化后日期格式
     * @return 格式化后的日期显示
     */
    fun dateFormat(sdate: String, format: String): String {
        val formatter = SimpleDateFormat(format)
        val date = java.sql.Date.valueOf(sdate)
        return dateSimpleFormat(date, formatter)
    }

    /**
     * 格式化日期显示格式
     *
     * @param date   Date对象
     * @param format 格式化后日期格式
     * @return 格式化后的日期显示
     */
    fun dateFormat(date: Date, format: String): String {
        val formatter = SimpleDateFormat(format)
        return dateSimpleFormat(date, formatter)
    }

    /**
     * 将date转成字符串
     *
     * @param date   Date
     * @param format SimpleDateFormat
     * <br></br>
     * 注： SimpleDateFormat为空时，采用默认的yyyy-MM-dd HH:mm:ss格式
     * @return yyyy-MM-dd HH:mm:ss
     */
    fun dateSimpleFormat(date: Date?, format: SimpleDateFormat?): String {
        var format2 = format
        if (format == null)
            format2 = defaultDateTimeFormat.get()
        return if (date == null) "" else format2!!.format(date)
    }

    /**
     * 将"yyyy-MM-dd HH:mm:ss" 格式的字符串转成Date
     *
     * @param strDate 时间字符串
     * @return Date
     */
    fun getDateByDateTimeFormat(strDate: String): Date? {
        return getDateByFormat(strDate, defaultDateTimeFormat.get())
    }

    /**
     * 将"yyyy-MM-dd" 格式的字符串转成Date
     *
     * @param strDate
     * @return Date
     */
    fun getDateByDateFormat(strDate: String): Date? {
        return getDateByFormat(strDate, defaultDateFormat.get())
    }

    /**
     * 将指定格式的时间字符串转成Date对象
     *
     * @param strDate 时间字符串
     * @param format  格式化字符串
     * @return Date
     */
    fun getDateByFormat(strDate: String, format: String): Date? {
        return getDateByFormat(strDate, SimpleDateFormat(format))
    }

    /**
     * 将String字符串按照一定格式转成Date<br></br>
     * 注： SimpleDateFormat为空时，采用默认的yyyy-MM-dd HH:mm:ss格式
     *
     * @param strDate 时间字符串
     * @param format  SimpleDateFormat对象
     * @throws ParseException 日期格式转换出错
     */
    private fun getDateByFormat(strDate: String, format: SimpleDateFormat?): Date? {
        var format = format
        if (format == null)
            format = defaultDateTimeFormat.get()
        try {
            return format!!.parse(strDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * 将年月日的int转成date
     *
     * @param year  年
     * @param month 月 1-12
     * @param day   日
     * 注：月表示Calendar的月，比实际小1
     */
    fun getDate(year: Int, month: Int, day: Int): Date {
        val mCalendar = Calendar.getInstance()
        mCalendar.set(year, month - 1, day)
        return mCalendar.time
    }

    /**
     * 求两个日期相差天数
     *
     * @param strat 起始日期，格式yyyy-MM-dd
     * @param end   终止日期，格式yyyy-MM-dd
     * @return 两个日期相差天数
     */
    fun getIntervalDays(strat: String, end: String): Long {
        return (java.sql.Date.valueOf(end).time - java.sql.Date
                .valueOf(strat).time) / (3600 * 24 * 1000)
    }

    /**
     * 获得当前年份
     *
     * @return year(int)
     */
    val currentYear: Int
        get() {
            val mCalendar = Calendar.getInstance()
            return mCalendar.get(Calendar.YEAR)
        }

    /**
     * 获得当前月份
     *
     * @return month(int) 1-12
     */
    val currentMonth: Int
        get() {
            val mCalendar = Calendar.getInstance()
            return mCalendar.get(Calendar.MONTH) + 1
        }

    /**
     * 获得当月几号
     *
     * @return day(int)
     */
    val dayOfMonth: Int
        get() {
            val mCalendar = Calendar.getInstance()
            return mCalendar.get(Calendar.DAY_OF_MONTH)
        }

    /**
     * 获得今天的日期(格式：yyyy-MM-dd)
     *
     * @return yyyy-MM-dd
     */
    val today: String
        get() {
            val mCalendar = Calendar.getInstance()
            return getDateFormat(mCalendar.time)
        }

    /**
     * 获得年月日数据
     *
     * @param sDate yyyy-MM-dd格式
     * @return arr[0]:年， arr[1]:月 0-11 , arr[2]日
     */
    fun getYearMonthAndDayFrom(sDate: String): IntArray {
        return getYearMonthAndDayFromDate(getDateByDateFormat(sDate))
    }

    /**
     * 获得年月日数据
     *
     * @return arr[0]:年， arr[1]:月 0-11 , arr[2]日
     */
    fun getYearMonthAndDayFromDate(date: Date?): IntArray {
        val calendar = Calendar.getInstance()
        calendar.time = date!!
        val arr = IntArray(3)
        arr[0] = calendar.get(Calendar.YEAR)
        arr[1] = calendar.get(Calendar.MONTH)
        arr[2] = calendar.get(Calendar.DAY_OF_MONTH)
        return arr
    }

     fun getCurrentAgeByBirthdate(brithday: String): Int {
        try {
            val calendar = Calendar.getInstance()
            val formatDate = SimpleDateFormat(DEFAULT_FORMAT_DATE)
            val currentTime = formatDate.format(calendar.time)
            val today = formatDate.parse(currentTime)
            val brithDay = formatDate.parse(brithday)
            return today.year - brithDay.year
        } catch (e: Exception) {
            return 0
        }

    }
}
