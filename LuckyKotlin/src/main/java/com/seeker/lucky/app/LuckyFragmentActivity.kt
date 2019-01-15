package com.seeker.lucky.app

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.collection.SparseArrayCompat
import com.seeker.lucky.extensions.checkForValidMessageWhat
import com.seeker.lucky.extensions.displayFragmentAndHideCurrent
import com.seeker.lucky.extensions.findFragmentByWho
import java.lang.ref.WeakReference

/**
 *@author  Seeker
 *@date    2018/11/12/012  10:29
 *@describe 基于 kotlin的 LuckyFragmentActivity
 */
abstract class LuckyFragmentActivity : AppCompatActivity(), LuckyUIHelper {

    companion object {
        private const val MAX_NUM_PENDING_FRAGMENT_MESSAGES: Int = 0xfff - 1
    }

    private var mHandler: SafeHandler? = null

    protected lateinit var mActivity: LuckyFragmentActivity

    protected var currentFragment: LuckyFragment? = null

    //是否处于活跃状态，是否在前台运行当前activity
    var atForeground: Boolean = false

    private val mPendingFragmentMessages: SparseArrayCompat<Long> = SparseArrayCompat()

    private var mNextCandidateWhatIndex: Int = 0

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = this
        init()
        val view = LayoutInflater.from(this).inflate(layoutResId(),null);
        setContentView(view)
        onViewCreated(view)
    }

    private fun init() {
        mPendingFragmentMessages.clear()
        mNextCandidateWhatIndex = 0
    }

    override fun onResume() {
        super.onResume()
        atForeground = true
    }

    override fun onPause() {
        super.onPause()
        atForeground = false
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler?.removeCallbacksAndMessages(null)
    }

    /**
     * 显示一个LuckyFragment
     */
    fun displayFragment(fragment: LuckyFragment, tag: String = fragment.javaClass.name) {
        supportFragmentManager.displayFragmentAndHideCurrent(fragment,fragmentContainerId(),tag = tag,currentFragment = currentFragment)
        this.currentFragment = fragment
    }

    /**
     * 返回安全的handler对象
     */
    fun safeHandler(): SafeHandler? {
        mHandler = mHandler ?: SafeHandler(this)
        return mHandler
    }

    /**
     * 处理handler回调信息
     */
    open fun handleMessage(message: Message?) {
        message?.let { it ->
            var whatIndex = it.what shr 16
            if (whatIndex != 0) {
                val who = mPendingFragmentMessages.get(--whatIndex)
                mPendingFragmentMessages.remove(whatIndex)
                who?.let {
                    findFragmentByWho(it)?.handleMessage(message)
                }
            }
        }
    }

    /**
     * 来自fragment的handler消息发送
     */
    fun sendMessageFromFragment(fragment: LuckyFragment, msg: Message, delay: Long) {
        msg.checkForValidMessageWhat()
        msg.what = allocateMessageWhatIndex(fragment).let {
            ((it + 1) shl 16) + (it and 0xffff)
        }
        safeHandler()?.sendMessageDelayed(msg, delay)
    }

    // Allocates the next available sendMessage what index.
    private fun allocateMessageWhatIndex(fragment: LuckyFragment): Int {
        if (mPendingFragmentMessages.size() >= MAX_NUM_PENDING_FRAGMENT_MESSAGES) {
            throw IllegalStateException("Too many pending Fragment messages.");
        }
        while (mPendingFragmentMessages.indexOfKey(mNextCandidateWhatIndex) >= 0) {
            mNextCandidateWhatIndex = (mNextCandidateWhatIndex + 1) % MAX_NUM_PENDING_FRAGMENT_MESSAGES
        }
        val whatIndex = mNextCandidateWhatIndex
        mPendingFragmentMessages.put(whatIndex, fragment.mWho)
        mNextCandidateWhatIndex = (mNextCandidateWhatIndex + 1) % MAX_NUM_PENDING_FRAGMENT_MESSAGES
        return whatIndex
    }

    class SafeHandler(basicActivity: LuckyFragmentActivity) : Handler() {
        private val mActivity = WeakReference<LuckyFragmentActivity>(basicActivity)
        override fun handleMessage(msg: Message?) {
            mActivity.get()?.handleMessage(msg)
        }
    }
}