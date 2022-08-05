package com.rainbow0o0.base.ext.util

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy


/**
 * Author : pengyu
 * Date   : 2022/7/29
 * Desc   :
 */
const val TAG = "调试日志"

fun initLog(isLoggable: Boolean) {
    val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
        .showThreadInfo(true) // (Optional) Whether to show thread info or not. Default true
        .methodCount(2) // (Optional) How many method line to show. Default 2
        .methodOffset(5) // (Optional) Hides internal method calls up to offset. Default 5
        .tag(TAG) // (Optional) Global tag for every log. Default PRETTY_LOGGER
        .build()

    Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
        override fun isLoggable(priority: Int, tag: String?): Boolean {
            return isLoggable
        }
    })
}

fun String.logV() = Logger.v(this)
fun String.logD() = Logger.d(this)
fun String.logI() = Logger.i(this)
fun String.logW() = Logger.w(this)
fun String.logE() = Logger.e(this)
fun String.logJson() = Logger.json(this)
fun String.logXML() = Logger.xml(this)
