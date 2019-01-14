package com.seeker.lucky.tab;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatCheckedTextView;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * @author Seeker
 * @date 2019/1/12/012  9:52
 */
public class TabStateView extends LinearLayout {

    private Tab tab;

    private int tabTextSize;//文字大小

    private ColorStateList tabTextColor;//文字颜色

    private int tabIconSize;

    private int space;//图标和文字之间的距离

    private AppCompatCheckedTextView textView;

    private AppCompatImageView imageView;

    public TabStateView(Context context) {
        super(context);
        this.setGravity(Gravity.CENTER);
        this.setOrientation(LinearLayout.VERTICAL);
    }

    public TabStateView setAttrs(int tabIconSize,int tabTextSize,ColorStateList tabTextColor,int space){
        this.tabIconSize = tabIconSize;
        this.tabTextSize = tabTextSize;
        this.tabTextColor = tabTextColor;
        this.space = space;
        return this;
    }

    public TabStateView bindTab(Tab tab) {
        this.tab = tab;
        return this;
    }

    public void notifyBind(){
        addImageView();
        addTextView();
    }

    private void addTextView(){
        textView = new AppCompatCheckedTextView(getContext());
        textView.setText(tab.getTabText());
        textView.setTextColor(tabTextColor);
        textView.getPaint().setTextSize(tabTextSize);
        LayoutParams textParams = new LayoutParams(-2,-2);
        textParams.topMargin = space;
        addView(textView,textParams);
    }

    private void addImageView(){
        Drawable drawable = tab.getTabIcon();
        if (drawable != null){
            imageView = new AppCompatImageView(getContext());
            imageView.setImageDrawable(drawable);
            imageView.setLayoutParams(new LayoutParams(tabIconSize,tabIconSize));
            addView(imageView);
        }
    }

    public void setSelected(boolean selected){
        if (textView != null){
            textView.setChecked(selected);
        }
        if (imageView != null){
            imageView.setSelected(selected);
        }
    }

    public Tab getTab() {
        return tab;
    }
}
