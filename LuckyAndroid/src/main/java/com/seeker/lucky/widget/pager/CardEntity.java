package com.seeker.lucky.widget.pager;

/**
 * @author Seeker
 * @date 2019/2/18/018  11:20
 */
public interface CardEntity<T> {

    /**
     * 设置卡片数据
     * @param entity
     */
    void setEntity(T entity);

    /**
     * 获取卡片数据
     * @return
     */
    T getEntity();

}
