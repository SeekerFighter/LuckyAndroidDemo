package com.seeker.lucky.recycleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Seeker
 * @date 2018/7/28/028  11:14
 */
public abstract class LuckyRecyleAdapter <T,VH extends LuckyViewHolder> extends RecyclerView.Adapter<VH>{

    public Context mContext;

    public LayoutInflater mInflater;

    public List<T> mDatas;

    private RecyclerView mRecyclerView;

    public LuckyRecyleAdapter(Context context,List<T> datas){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = datas;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = mInflater.inflate(getItemChildLayoutId(viewType),parent,false);
        VH vh;
        if (hasOverflowChild()){
            final View overflowView = mInflater.inflate(getOverflowChildLayoutId(viewType),parent,false);
            LuckyContainerLayout luckyContainerLayout = new LuckyContainerLayout(mContext);
            luckyContainerLayout.addOverflowLayout(overflowView)
                    .addContentItemLayout(itemView)
                    .finishAdd();
            luckyContainerLayout.setLayoutParams(new RecyclerView.LayoutParams(-1,-2));
            vh = convertCreateViewHolder(luckyContainerLayout,viewType);
        }else {
            vh = convertCreateViewHolder(itemView,viewType);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        convertBindViewHolder(holder,position);
    }

    @Override
    public int getItemCount() {
        return mDatas == null?0:mDatas.size();
    }

    protected final void setRecycleView(RecyclerView recycleView){
        this.mRecyclerView = recycleView;
    }

    /**
     * 刷新新数据
     */
    public final void  notifyDataChanged(){
        notifyDataChanged(true);
    }

    public final void  notifyDataChanged(boolean anim){
        notifyDataSetChanged();
        if (mRecyclerView != null && anim){
            mRecyclerView.scheduleLayoutAnimation();
        }
    }

    /**
     * 对外接口，viewHolder与itemView进行绑定
     * @param itemView
     * @return VH
     */
    public abstract VH convertCreateViewHolder(View itemView,int viewType);

    /**
     * 给View赋值内容显示
     * @param viewHolder
     * @param position
     */
    public abstract void convertBindViewHolder(VH viewHolder,int position);

    /**
     * 设置item布局
     * @param viewType
     * @return
     */
    public abstract int getItemChildLayoutId(int viewType);

    /**
     * 设置overflow布局
     * @param viewType
     * @return
     */
    public int getOverflowChildLayoutId(int viewType){
        return -1;
    }

    /**
     * 是否有overflow
     * @return
     */
    public boolean hasOverflowChild(){
        return false;
    }

}
