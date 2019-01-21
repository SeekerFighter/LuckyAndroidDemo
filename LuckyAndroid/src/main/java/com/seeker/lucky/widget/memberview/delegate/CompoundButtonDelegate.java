package com.seeker.lucky.widget.memberview.delegate;

import android.widget.CompoundButton;

import com.seeker.lucky.widget.memberview.MemberView;


/**
 * @author Seeker
 * @date 2019/1/8/008  18:01
 */
public abstract class CompoundButtonDelegate<T extends CompoundButton> extends BaseDelegate{

    public T compoundButton;

    public CompoundButtonDelegate(MemberView container) {
        super(container);
    }

    public void setOnCheckedChangeListener(final OnDelegateCheckedChangeListener listener){
        if(listener != null){
            compoundButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    listener.onCheckedChanged(container,isChecked);
                }
            });
        }
    }

    public void setChecked(boolean isChecked){
        compoundButton.setChecked(isChecked);
    }

}
