@file:JvmMultifileClass
@file:JvmName("LuckyApp")

package com.seeker.lucky.extensions

import android.os.Bundle
import android.os.Message
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.seeker.lucky.R
import com.seeker.lucky.app.LuckyFragment
import com.seeker.lucky.app.LuckyFragmentActivity
import com.seeker.lucky.widget.LuckyDialog

/**
 *@author  Seeker
 *@date    2018/11/20/020  16:29
 *@describe 定义的基础库函数扩展
 */


/**
 * 查找一个fragment
 */
fun LuckyFragmentActivity.findFragmentByWho(who: Long): LuckyFragment? {
    val fragments = supportFragmentManager.fragments
    var bf: LuckyFragment? = null
    for (f in fragments) {
        bf = (f as? LuckyFragment)?.let {
            if (who == it.mWho) it else null
        }
        if (bf != null) break
    }
    return bf
}

/**
 * 实例化生成一个Fragment
 */
@JvmOverloads
inline fun <reified F : LuckyFragment> LuckyFragmentActivity.instantiateFragment(bundle: Bundle? = null): F {
    return Fragment.instantiate(this, F::class.java.name, bundle) as F
}

/**
 * 返回安全的handler对象,当前对象在宿主LuckyFragmentActivity实例化
 */
fun LuckyFragment.safeHandler(): LuckyFragmentActivity.SafeHandler? = mHostActivity?.safeHandler()

/**
 * 发送一个handler消息
 */
@JvmOverloads
fun LuckyFragment.sendSmartMessage(message: Message, delayMill: Long = 0) =
        mHostActivity?.sendMessageFromFragment(this, message, delayMill)

/**
 * 弹一个Toast消息
 */
private fun LuckyFragment.makeToast(message: CharSequence?, duration: Int = Toast.LENGTH_SHORT) =
        mHostActivity?.makeToast(message, duration)

/**
 * 显示一个LuckyFragment
 */
@JvmOverloads
internal fun FragmentManager.displayFragmentAndHideCurrent(
        fragment: LuckyFragment,
        containerId: Int,
        tag: String = fragment.javaClass.name,
        currentFragment: LuckyFragment? = null
) = currentFragment?.let {
    if (it.same(fragment)) {
        it.echoShowTodoWork()
    } else {
        hideAndShow(this, currentFragment, fragment, tag, containerId)
    }
} ?: hideAndShow(this, currentFragment, fragment, tag, containerId)

private fun LuckyFragment.same(fragment: LuckyFragment) = mWho == fragment.mWho

private val hideAndShow: (manager: FragmentManager, hide: LuckyFragment?, show: LuckyFragment, tag: String, containerId: Int) -> Unit = { manager: FragmentManager, hide: LuckyFragment?, show: LuckyFragment, tag: String, id: Int ->
    val transaction = manager.beginTransaction()
    hide?.let { transaction.hide(it) }
    manager.findFragmentByTag(tag)?.let {
        transaction.show(it)
    } ?: transaction.add(id, show, tag)
    transaction.commit()
}

/**
 * 显示一个dialogFragment
 */
@JvmOverloads
fun FragmentManager.displayDialog(title: String? = null,
                                   message: String,
                                   themeResId: Int = R.style.dialogStyle,
                                   /**
                                    * titleTv，messageTv，positiveBtn，negativeBtn
                                    */
                                   custom: (TextView, TextView, Button, Button) -> Unit = { _, _, _, _ -> Unit },
                                   onPositiveClick: (Button) -> Boolean = { true },
                                   onNegativeClick: (Button) -> Boolean = { true },
                                   tag: String = this::class.java.simpleName) = with(this) {
    LuckyDialog().initSetting(title, message, themeResId, custom, onPositiveClick, onNegativeClick)
            .show(this, tag)
}