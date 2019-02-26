package com.seeker.lucky.widget.pager;

import android.view.View;

import androidx.annotation.LayoutRes;

/**
 * @author Seeker
 * @date 2019/2/18/018  10:46
 * @describe 卡片，item
 */
public interface Card<T>{

    CardEntity<T> getCardEntity();//返回卡片数据

    int getCardType();//返回卡片类型

    @LayoutRes
    int getCardLayoutResId();//返回卡片布局

    void onCardChildClick(View child);//卡片中View的点击回调

    //数据绑定
    void onCardConvert(CardAdapter.CardViewHolder viewHolder,CardEntity<T> cardEntity);

}
