package com.seeker.lucky.memberview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.seeker.lucky.R;
import com.seeker.lucky.memberview.delegate.CompoundButtonDelegate;
import com.seeker.lucky.memberview.delegate.CustomDelegate;
import com.seeker.lucky.memberview.delegate.ImageDelegate;
import com.seeker.lucky.memberview.delegate.OnDelegateCheckedChangeListener;
import com.seeker.lucky.memberview.delegate.ArrowDelegate;
import com.seeker.lucky.memberview.delegate.MemberDelegate;
import com.seeker.lucky.memberview.delegate.CheckboxDelegate;
import com.seeker.lucky.memberview.delegate.SwitchDelegate;

/**
 * @author Seeker
 * @date 2018/12/24/024  17:54
 */
public class MemberView extends LinearLayout implements Delegate{

    private MemberDelegate mDelegate;

    private static final int TYPE_CUSTOM = 0;
    private static final int TYPE_ARROW = 1;
    private static final int TYPE_SWITCH = 2;
    private static final int TYPE_CHECKBOX = 3;
    private static final int TYPE_IMAGE = 4;

    public MemberView(Context context) {
        this(context,null);
    }

    public MemberView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MemberView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOrientation(HORIZONTAL);
        this.setGravity(Gravity.CENTER_VERTICAL);
        setUpAttrs(context,attrs,defStyleAttr);
    }

    private void setUpAttrs(Context context,AttributeSet attrs,int defStyleAttr){
        final Resources.Theme theme = context.getTheme();
        TypedArray ta = theme.obtainStyledAttributes(attrs, R.styleable.MemberView,defStyleAttr,0);
        if (ta != null){
            try {
                createDelegate(ta.getInt(R.styleable.MemberView_coreType,TYPE_CUSTOM));
                mDelegate.setUpAttrs(ta);
            }finally {
                ta.recycle();
            }
        }
    }

    private void createDelegate(int type){
        switch (type){
            case TYPE_CUSTOM:
                mDelegate = new CustomDelegate(this);
                break;
            case TYPE_ARROW:
                mDelegate = new ArrowDelegate(this);
                break;
            case TYPE_SWITCH:
                mDelegate = new SwitchDelegate(this);
                break;
            case TYPE_CHECKBOX:
                mDelegate = new CheckboxDelegate(this);
                break;
            case TYPE_IMAGE:
                mDelegate = new ImageDelegate(this);
                break;
            default:
                    throw new RuntimeException("unkonw current type:"+type);
        }
    }

    @Override
    public void setCoreText(CharSequence coreText) {
        mDelegate.setCoreText(coreText);
    }

    @Override
    public void appendCoreText(CharSequence appendedCoreText) {
        mDelegate.appendCoreText(appendedCoreText);
    }

    @Override
    public void setSubText(CharSequence subText) {
        mDelegate.setSubText(subText);
    }

    @Override
    public void appendSubText(CharSequence appendedSubText) {
        mDelegate.appendSubText(appendedSubText);
    }

    public void setOnCheckedChangeListener(OnDelegateCheckedChangeListener listener){
        if (mDelegate instanceof CompoundButtonDelegate){
            ((CompoundButtonDelegate) mDelegate).setOnCheckedChangeListener(listener);
        }
    }

    public void setChecked(boolean isChecked){
        if (mDelegate instanceof CompoundButtonDelegate){
            ((CompoundButtonDelegate) mDelegate).setChecked(isChecked);
        }
    }

}
