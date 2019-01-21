package com.seeker.lucky.widget.recycleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Seeker
 * @date 2018/7/28/028  11:14
 */
public abstract class LuckyRecyclerAdapter<T> extends RecyclerView.Adapter<LuckyViewHolder>{

    public Context mContext;

    public LayoutInflater mInflater;

    public List<T> mData;

    private RecyclerView mRecyclerView;

    public LuckyRecyclerAdapter(@NonNull Context context, @NonNull List<T> data){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mData = data;
    }

    @NonNull
    @Override
    public final LuckyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = mInflater.inflate(getItemLayoutResId(viewType),parent,false);
        LuckyViewHolder vh;
        if (showItemSwipeLayout()){
            final View overflowView = mInflater.inflate(getItemSwipeLayoutResId(viewType),parent,false);
            LuckyContainerLayout luckyContainerLayout = new LuckyContainerLayout(mContext);
            luckyContainerLayout.addOverflowLayout(overflowView)
                    .addContentItemLayout(itemView)
                    .finishAdd();
            luckyContainerLayout.setLayoutParams(new RecyclerView.LayoutParams(-1,-2));
            vh = new LuckyViewHolder(luckyContainerLayout);
        }else {
            vh = new LuckyViewHolder(itemView);
        }
        return vh;
    }

    @Override
    public final void onBindViewHolder(@NonNull LuckyViewHolder holder, int position) {
        convertDataViewHolder(holder,position);
    }

    @Override
    public final int getItemCount() {
        return mData == null?0:mData.size();
    }

    final void setRecycleView(RecyclerView recycleView){
        this.mRecyclerView = recycleView;
    }

    public final void  notifyDataChangedWithLayoutAnim(boolean anim){
        notifyDataSetChanged();
        if (mRecyclerView != null && anim){
            mRecyclerView.scheduleLayoutAnimation();
        }
    }

    /**
     * 往当前数据源添加新的数据
     * @param data
     * @param index
     */
    public void addData(T data,@IntRange(from = 0) int index){
        if (data == null){
            return;
        }
        if (mData.size() < index){
            index = mData.size();
        }
        mData.add(index,data);
        notifyItemInserted(index);
    }

    /**
     * 从数据源删除一个存在的数据信息
     * @param data
     */
    public void deleteData(T data){
        if (data == null){
            return;
        }
        int index = mData.indexOf(data);
        if (index >= 0) {
            mData.remove(index);
            notifyItemRemoved(index);
        }
    }

    /**
     * 给View赋值内容显示
     * @param viewHolder
     * @param position
     */
    public abstract void convertDataViewHolder(LuckyViewHolder viewHolder,int position);

    /**
     * 设置item布局
     * @param viewType
     * @return
     */
    public abstract int getItemLayoutResId(int viewType);

    /**
     * 设置overflow布局
     * @param viewType
     * @return
     */
    public int getItemSwipeLayoutResId(int viewType){
        return -1;
    }

    /**
     * 是否有overflow
     * @return
     */
    public boolean showItemSwipeLayout(){
        return false;
    }

}
