package com.seeker.lucky.widget

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.seeker.lucky.R
import kotlinx.android.synthetic.main.lucky_dialog_layout.*

/**
 *@author  Seeker
 *@date    2018/12/17/017  10:56
 *@describe dialog
 */
internal class LuckyDialog :DialogFragment(),View.OnClickListener{

    private var mTitle:String? = null

    private lateinit var mMessage:String

    private var mThemeResId:Int = R.style.dialogStyle

    private var mCustom:(TextView,TextView,Button,Button)->Unit = { _, _, _, _ -> Unit}

    private var mOnPositiveClick:(Button)->Boolean = {true}

    private var mOnNegativeClick:(Button)->Boolean = {true}

    fun initSetting(title:String?,
                    message:String,
                    themeResId:Int = R.style.dialogStyle,
                    custom:(TextView, TextView, Button, Button)->Unit,
                    onPositiveClick:(Button)->Boolean,
                    onNegativeClick:(Button)->Boolean):LuckyDialog = apply {
        this.mTitle = title
        this.mMessage = message
        this.mThemeResId = themeResId
        this.mCustom = custom
        this.mOnPositiveClick = onPositiveClick
        this.mOnNegativeClick = onNegativeClick
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.lucky_dialog_layout,container,false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(activity,mThemeResId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mCustom(title,message,positiveBtn,negativeBtn)
        title.text = mTitle
        message.text = mMessage
        positiveBtn.setOnClickListener(this)
        negativeBtn.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.positiveBtn -> {
                if (mOnPositiveClick(v as Button)){
                    dismiss()
                }
            }
            R.id.negativeBtn -> {
                if (mOnNegativeClick(v as Button)){
                    dismiss()
                }
            }
        }
    }
}