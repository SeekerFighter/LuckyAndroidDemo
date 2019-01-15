@file:JvmMultifileClass
@file:JvmName("LuckyFile")

package com.seeker.lucky.extensions

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.IOException

/**
 *@author  Seeker
 *@date    2018/11/15/015  14:59
 *@describe File文件相关扩展函数
 */

/**
 * 检测外部存储设备是否已经挂载
 */
fun isMediaMounted(): Boolean = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

/**
 * 获取外部私有存储根目录，没有则创建
 * @param name 私有存储目录名
 */
fun Context.externalStoragePrivateDirPath(name: String): String? =
    if (isMediaMounted()) getExternalFilesDir(name).absolutePath else null

/**
 * 创建外部私有文件
 * @param name 私有文件名字
 */
fun Context.createExternalStoragePrivateFile(name: String): String? {
    if (isMediaMounted()) {
        File(getExternalFilesDir(null), name).let { it ->
            if (createNewDirs(it.parent) != null) {
                return createNewFile(it.absolutePath)?.let {
                    return it.absolutePath
                }
            }
        }
    }
    return null
}

/**
 * 新建目录
 * @param dirPath 目录绝对路径
 */
fun createNewDirs(dirPath: String): File? {
    val path = if (dirPath.endsWith(File.separatorChar)) dirPath else dirPath + File.separatorChar
    return File(path).createNewDirs()
}

/**
 * 新建目录
 */
fun File.createNewDirs(): File? = if (exists() || mkdirs()) this else null

/**
 * 新建文件
 * @param filePath 文件绝对路径
 */
fun createNewFile(filePath: String): File? = File(filePath).fileCreate()

/**
 * 新建一个文件
 */
fun File.fileCreate(): File? = if (exists()) this else {
    parent?.let { it ->
        createNewDirs(it)?.let {
            try {
                if (createNewFile()) this else null
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }
}

/**
 * 判断文件是否存在
 * @param filePath 文件绝对路径
 */
fun fileExists(filePath: String?): Boolean = filePath?.let { File(it).exists() } ?: false

/**
 * 文件删除:递归删除此文件夹下的所有文件,不在此做文件存在的检查
 * @param filePath 将要被删除的文件
 */
fun fileDelete(filePath: String?): Boolean = if (fileExists(filePath)) File(filePath).deleteRecursively() else true