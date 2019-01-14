package com.seeker.lucky.tab;

import android.graphics.drawable.Drawable;

import com.seeker.lucky.utils.DrawableHelper;

import androidx.fragment.app.Fragment;

/**
 * @author Seeker
 * @date 2019/1/12/012  9:35
 */
public class Tab {

    private Drawable tabIcon;//图标显示

    private CharSequence tabText;//文字显示

    private Fragment fragment;

    private Tab(Builder builder){
        this.tabIcon = builder.getStateIcon();
        this.tabText = builder.tabText;
        this.fragment = builder.fragment;
    }

    public Drawable getTabIcon() {
        return tabIcon;
    }

    public CharSequence getTabText() {
        return tabText;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public static final class Builder{

        private Drawable normalDrawable;//正常图标

        private Drawable selectedDrawable;//选中时图标

        private CharSequence tabText;//文字显示

        private Fragment fragment;

        public Builder setDrawable(Drawable normalDrawable,Drawable selectedDrawable) {
            this.normalDrawable = normalDrawable;
            this.selectedDrawable = selectedDrawable;
            return this;
        }

        public Builder setTabText(CharSequence tabText) {
            this.tabText = tabText;
            return this;
        }

        public Builder setFragment(Fragment fragment) {
            this.fragment = fragment;
            return this;
        }

        private Drawable getStateIcon(){
            return DrawableHelper.createSelectedListDrawable(normalDrawable,selectedDrawable);
        }

        public Tab Build(){
            return new Tab(this);
        }
    }

}
