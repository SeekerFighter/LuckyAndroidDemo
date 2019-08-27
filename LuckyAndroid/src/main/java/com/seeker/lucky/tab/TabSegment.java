package com.seeker.lucky.tab;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.seeker.luckya.R;
import com.seeker.lucky.utils.ColorHelper;

/**
 * @author Seeker
 * @date 2019/1/12/012  10:04
 */
public class TabSegment extends LinearLayout implements View.OnClickListener {

    private int tabTextSize;//文字大小

    private ColorStateList tabTextColor;//文字正常颜色

    private int space;//图标和文字之间的距离

    private int tabIconSize;//图标大小

    private OnTabClickListener tabClickListener;

    private TabStateView currentSelectedTab;

    private int currentSelectedIndex = 0;

    public TabSegment(Context context) {
        this(context,null);
    }

    public TabSegment(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TabSegment(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttrs(context,attrs,defStyleAttr);
    }

    private void setAttrs(Context context,AttributeSet attrs,int defStyleAttr){
        final Resources.Theme theme = context.getTheme();
        TypedArray ta = theme.obtainStyledAttributes(attrs, R.styleable.TabSegment,defStyleAttr,0);
        if (ta != null){
            try {
                tabTextSize = ta.getDimensionPixelSize(R.styleable.TabSegment_tabTextSize,14);
                createTextColor(ta);
                space = ta.getDimensionPixelSize(R.styleable.TabSegment_tabInnerSpace,5);
                tabIconSize = ta.getDimensionPixelSize(R.styleable.TabSegment_tabIconSize,-2);
            }finally {
                ta.recycle();
            }
        }
    }

    private void createTextColor(TypedArray ta){
        int normalColor = ta.getColor(R.styleable.TabSegment_tabTextNormalColor,0xFF333333);
        int checkedColor  = ta.getColor(R.styleable.TabSegment_tabTextCheckedColor,0xFF4886FF);
        tabTextColor = ColorHelper.createCheckedColorStateList(normalColor,checkedColor);
    }

    public TabSegment addTabs(Tab... tabs){
        if (tabs != null && tabs.length > 0) {
            int childCount = getChildCount();
            for (int i = 0,len = tabs.length;i < len;i++) {
                Tab tab = tabs[i];
                if (tab == null){
                    continue;
                }
                int index = childCount + i;
                TabStateView tabStateView = new TabStateView(getContext());
                tabStateView.setTag(index);
                tabStateView.setOnClickListener(this);
                tabStateView.setAttrs(tabIconSize,tabTextSize,tabTextColor,space)
                            .bindTab(tab)
                            .notifyBind();
                addView(tabStateView,index,generaLayoutParams());
            }
        }
        return this;
    }

    public TabSegment setSelectedTabIndex(int index){
        this.currentSelectedIndex = index;
        return this;
    }

    public void notifyTabChanged(){
        onClick(getChildAt(currentSelectedIndex));
    }

    @Override
    public void onClick(View v) {
        if (currentSelectedTab != null){
            currentSelectedTab.setSelected(false);
        }
        if (!(v instanceof TabStateView)){
            throw new ClassCastException(v.getClass().getName()+"can't cast to TabStateView.");
        }
        currentSelectedTab = (TabStateView) v;
        currentSelectedTab.setSelected(true);
        currentSelectedIndex = Integer.parseInt(currentSelectedTab.getTag().toString());
        if (tabClickListener != null){
            tabClickListener.onTabClick(currentSelectedTab,currentSelectedIndex);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0){
            throw new IllegalStateException("you don't need add child in xml.");
        }
    }

    private LayoutParams generaLayoutParams(){
        LayoutParams params = new LayoutParams(0,LayoutParams.MATCH_PARENT,1);
        params.gravity = Gravity.CENTER_VERTICAL;
        return params;
    }

    public TabSegment setOnTabClickListener(OnTabClickListener tabClickListener) {
        this.tabClickListener = tabClickListener;
        return this;
    }

    public interface OnTabClickListener{
        void onTabClick(TabStateView stateView,int position);
    }

}
