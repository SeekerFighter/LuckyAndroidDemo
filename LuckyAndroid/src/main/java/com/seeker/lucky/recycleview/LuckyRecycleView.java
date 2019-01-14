package com.seeker.lucky.recycleview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.seeker.lucky.recycleview.wrppper.GridLayoutManagerWrapper;
import com.seeker.lucky.recycleview.wrppper.LinearLayoutManagerWrapper;
import com.seeker.lucky.recycleview.wrppper.StaggeredGridLayoutManagerWrapper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * @author Seeker
 * @date 2018/7/28/028  10:47
 */
public class LuckyRecycleView extends RecyclerView {

    /**
     * 数据为空时显示的布局
     */
    private View emptyView;

    /**
     * 数据监听
     */
    private DataEmptyObserview dataEmptyObserview;

    private LuckyItemToucListener mOnItemToucListenerImpl;

    public LuckyRecycleView(Context context) {
        this(context,null);
    }

    public LuckyRecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LuckyRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mOnItemToucListenerImpl = new LuckyItemToucListener(context);
        this.addOnItemTouchListener(mOnItemToucListenerImpl);
        this.dataEmptyObserview = new DataEmptyObserview();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        /**
         *拦截多点触控
         */
        if(event.getActionMasked() == MotionEvent.ACTION_POINTER_UP ||
                event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN){
            return true;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        Adapter oldAdapter = getAdapter();
        if(oldAdapter != null){
            oldAdapter.unregisterAdapterDataObserver(dataEmptyObserview);
        }

        if(adapter == null){
            throw new NullPointerException("adapter is null!");
        }

        if (adapter instanceof LuckyRecyleAdapter){
            ((LuckyRecyleAdapter) adapter).setRecycleView(this);
        }
        adapter.registerAdapterDataObserver(dataEmptyObserview);
        super.setAdapter(adapter);
        dataEmptyObserview.onChanged();
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        LayoutManager manager = layout;
        if (manager instanceof StaggeredGridLayoutManagerWrapper
                || manager instanceof GridLayoutManagerWrapper
                || manager instanceof LinearLayoutManagerWrapper){
            super.setLayoutManager(manager);
            return;
        }else if (manager instanceof StaggeredGridLayoutManager){
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) manager;
            manager = new StaggeredGridLayoutManagerWrapper(layoutManager.getSpanCount(),layoutManager.getOrientation());
        }else if (manager instanceof GridLayoutManager){
            manager = new GridLayoutManagerWrapper(getContext(),((GridLayoutManager) manager).getSpanCount());
        }else if (manager instanceof LinearLayoutManager){
            manager = new LinearLayoutManagerWrapper(getContext());
        }
        super.setLayoutManager(manager);
    }

    /**
     * 设置点击事件
     * @param clickListener
     */
    public void setOnItemClickListener(LuckyItemToucListener.OnItemClickListener clickListener) {
        setClickable(true);
        if (mOnItemToucListenerImpl != null){
            mOnItemToucListenerImpl.setOnItemClickListener(clickListener);
        }
    }

    /**
     * 设置长按事件
     * @param longClickListener
     */
    public void setOnItemLongClickListner(LuckyItemToucListener.OnItemLongClickListner longClickListener) {
        setLongClickable(true);
        if (mOnItemToucListenerImpl != null){
            mOnItemToucListenerImpl.setOnItemLongClickListner(longClickListener);
        }
    }

    /**
     * 设置overflow事件监听
     * @param listener
     */
    public void setOnOverflowItemClickListener(LuckyItemToucListener.OnOverflowItemClickListener listener){
        if (mOnItemToucListenerImpl != null){
            mOnItemToucListenerImpl.setOnOverflowItemClickListener(listener);
        }
    }

    /**
     * 设置overflow变化状态监听
     * @param listener
     */
    public void setOnOverflowChangedAnimator(LuckyItemToucListener.OnOverflowChangedAnimator listener){
        if (mOnItemToucListenerImpl != null){
            mOnItemToucListenerImpl.setOnOverflowChangedAnimator(listener);
        }
    }


    /**
     * 主动关闭打开的overflow
     */
    public void closeOpenOverflow(long delay){
        postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mOnItemToucListenerImpl != null){
                    mOnItemToucListenerImpl.closeOpedOverflow();
                }
            }
        },delay);
    }

    /**
     * 设置数据为空时要显示的布局
     * @param emptyView
     */
    public void setEmptyView(@NonNull View emptyView){
        this.emptyView = emptyView;
//        if (emptyView.getParent() != getParent()){
//            throw new IllegalStateException("emptyView must have a same parent with RecycleView.");
//        }
        dataEmptyObserview.onChanged();
    }

    /**
     * 数据观察者
     */
    private final class DataEmptyObserview extends AdapterDataObserver{

        @Override
        public void onChanged() {
            checkEmpty();
        }
        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            checkEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkEmpty();
        }
        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkEmpty();
        }
        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            checkEmpty();
        }

        private void checkEmpty(){
            Adapter adapter = getAdapter();
            if(adapter != null && emptyView != null){
                final boolean isEmpty = adapter.getItemCount() == 0;
                emptyView.setVisibility(isEmpty?VISIBLE:GONE);
                LuckyRecycleView.this.setVisibility(isEmpty?GONE:VISIBLE);
            }
        }

    }

}
