
@file:JvmMultifileClass
@file:JvmName("AppKt")

package com.seeker.lucky.extensions

import android.os.Bundle
import android.os.Message
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.seeker.lucky.R
import com.seeker.lucky.app.LuckyFragmentActivity
import com.seeker.lucky.app.LuckyFragment
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
inline fun <reified F:LuckyFragment> LuckyFragmentActivity.instantiateFragment(bundle: Bundle? = null):F{
    return Fragment.instantiate(this,F::class.java.name,bundle) as F
}

/**
 * 返回安全的handler对象,当前对象在宿主LuckyFragmentActivity实例化
 */
fun LuckyFragment.safeHandler(): LuckyFragmentActivity.SafeHandler? = mHostActivity.safeHandler()

/**
 * 发送一个handler消息
 */
fun LuckyFragment.sendSmartMessage(message: Message, delayMill: Long = 0) =
    mHostActivity.sendMessageFromFragment(this, message, delayMill)

/**
 * 弹一个Toast消息
 */
private fun LuckyFragment.makeToast(message: CharSequence?, duration: Int = Toast.LENGTH_SHORT) =
    mHostActivity.makeToast(message, duration)

/**
 * 判断2个fragment是否是同一个
 */
private fun LuckyFragment.ifSameOne(other:LuckyFragment, same:()->Unit, not:()->Unit):Boolean = other.let {
        if (mWho == it.mWho){
            same()
            true
        }else{
            not()
            false
        }
    }

/**
 * 显示一个LuckyFragment
 */
internal fun FragmentActivity.displayFragment(
        fragment: LuckyFragment,
        containerId: Int,
        tag: String? = fragment.javaClass.name,
        preFragment: LuckyFragment? = null
): LuckyFragment {
    val transaction = supportFragmentManager.beginTransaction()
    val displayed = preFragment?.ifSameOne(fragment,
        same = preFragment::echoShowTodoWork,
        not =  {transaction.hide(preFragment)})?:false

    if (!displayed) {
        supportFragmentManager.findFragmentByTag(tag)?.let {
            transaction.show(it)
        } ?: transaction.add(containerId, fragment, tag)
        transaction.commitAllowingStateLoss()
    }
    return fragment
}

/**
 * 显示一个dialogFragment
 */
fun FragmentActivity.displayDialog(title:String ?= null,
                                   message:String,
                                   themeResId:Int = R.style.dialogStyle,
                                   /**
                                    * titleTv，messageTv，positiveBtn，negativeBtn
                                    */
                                   custom:(TextView, TextView, Button, Button)->Unit = { _, _, _, _ -> Unit},
                                   onPositiveClick:(Button)->Boolean = {true},
                                   onNegativeClick:(Button)->Boolean = {true},
                                   tag:String = this::class.java.simpleName) = with(this.supportFragmentManager){
    LuckyDialog().initSetting(title,message,themeResId,custom,onPositiveClick,onNegativeClick)
    .show(this,tag)
}