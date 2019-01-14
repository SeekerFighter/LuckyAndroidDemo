package com.seeker.lucky.log

import android.util.Log
import java.io.IOException
import java.io.PrintWriter
import java.io.StringWriter

/**
 *@author  Seeker
 *@date    2018/11/12/012  17:19
 *@describe 打印
 */
object LuckyLogger {

    private const val GLOBAL_TAG: String = "LuckyLogger"

    private val LINE_SEP: String? = System.getProperty("line.separator");

    @JvmOverloads
    fun v(tag: String? = null,msg: String) = print2Console(Log.VERBOSE, tag, msg)

    @JvmOverloads
    fun d(tag: String? = null,msg: String) = print2Console(Log.DEBUG, tag, msg)

    @JvmOverloads
    fun i(tag: String? = null,msg: String) = print2Console(Log.INFO, tag, msg)

    @JvmOverloads
    fun w(tag: String? = null,msg: String) = print2Console(Log.WARN, tag, msg)

    @JvmOverloads
    fun e(tag: String? = null,msg: String, throwable: Throwable? = null) =
        print2Console(Log.ERROR, tag, msg, throwable)

    private fun print2Console(priority: Int, tag: String?, msg: String, throwable: Throwable? = null) {
        val contender = StringBuffer()
        contender.append(msg).append(LINE_SEP)
        throwable?.let {
            contender.append(getThrowableInfo(it)).append(LINE_SEP)
        }
        Log.println(priority, tag?.let { "${GLOBAL_TAG}_$it" } ?: GLOBAL_TAG, contender.toString())
    }

    private fun getThrowableInfo(throwable: Throwable): String {
        return throwable.let {
            val sw = StringWriter()
            val pw = PrintWriter(sw)
            it.printStackTrace(pw)
            val error = sw.toString()
            try {
                pw.close()
                sw.close()
            } catch (e: IOException) {
            }
            error
        }
    }
}