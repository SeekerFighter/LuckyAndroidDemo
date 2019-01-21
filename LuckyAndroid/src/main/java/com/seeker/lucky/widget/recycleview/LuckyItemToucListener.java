package com.seeker.lucky.widget.recycleview;

import android.content.Context;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.seeker.lucky.widget.recycleview.wrppper.LinearLayoutManagerWrapper;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Seeker
 * @date 2018/7/28/028  9:41
 * @describe recycleview添加点击、长按状态响应
 */
public class LuckyItemToucListener implements RecyclerView.OnItemTouchListener{

    /**
     * Indicates that we are not in the middle of a touch gesture
     */
    private static final int TOUCH_MODE_REST = -1;

    /**
     * Indicates we just received the touch event and we are waiting to see if the it is a tap or a
     * scroll gesture.
     */
    private static final int TOUCH_MODE_DOWN = 0;

    /**
     * Indicates the touch has been recognized as a tap and we are now waiting to see if the touch
     * is a longpress
     */
    private static final int TOUCH_MODE_TAP = 1;

    /**
     * Indicates we have waited for everything we can wait for, but the user's finger is still down
     */
    private static final int TOUCH_MODE_DONE_WAITING = 2;

    /**
     * One of TOUCH_MODE_REST, TOUCH_MODE_DOWN, TOUCH_MODE_TAP, TOUCH_MODE_SCROLL, or
     * TOUCH_MODE_DONE_WAITING
     */
    private int mTouchMode = TOUCH_MODE_REST;

    private static final int SLIDE_NONE = 0;//手指没有发生滑动

    private static final int SLIDE_H = 1;//手指横向滑动

    private static final int SLIDE_V = 2;//手指纵向滑动

    private int mSlideState = SLIDE_NONE;

    /**
     * The X value associated with the the down motion event
     */
    private float mInitialMotionX;

    private float mInitialMotionRawX;

    /**
     * The Y value associated with the the down motion event
     */
    private float mInitialMotionY;

    private float mInitialMotionRawY;

    /**
     * The X value associated with the the pre motion event
     */
    private float mLastMotionX;

    private static final int OVERFLOW_STATE_OPENED = 0;

    private static final int OVERFLOW_STATE_CLOSED = 1;

    private static final int OVERFLOW_STATE_OPENING = 2;

    private int overflowState = OVERFLOW_STATE_CLOSED;

    private OnItemClickListener mClickListener;

    private OnItemLongClickListner mLongClickListener;

    private OnOverflowItemClickListener mOverflowClickListener;

    private OnOverflowChangedAnimator mOverflowChangedAnimator;

    private int tapTimeout;

    private int longPressTimeout;

    private int touchSlop;

    private int pressedStateDuration;

    private RecyclerView mRecyclerView;

    private View touchedChildView;

    private LuckyContainerLayout luckyContainerLayout;

    private LuckyItemContentLayout luckyItemContentLayout;

    private CheckForTap mPendingCheckForTap;

    private CheckForLongPress mPendingCheckForLongPress;

    private PerformClick mPerformClick;

    /**
     * 当recycleView滑动时，拦截此次所有事件
     */
    private boolean canIntercept;

    private boolean canAnswerClick = false;//是否可以响应点击事件,包括长按

    private boolean isTouchedMove = false;//是否发生滚动情况

    private final Rect outRect = new Rect();

    private final Rect overChildRect = new Rect();

    LuckyItemToucListener(Context context){
        this.tapTimeout = ViewConfiguration.getTapTimeout();
        this.longPressTimeout = ViewConfiguration.getLongPressTimeout();
        this.touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.pressedStateDuration = ViewConfiguration.getPressedStateDuration();
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

        this.mRecyclerView = rv;

        final int actionMasked = e.getActionMasked();

        if (actionMasked == MotionEvent.ACTION_DOWN) {
            canIntercept = rv.getScrollState() != RecyclerView.SCROLL_STATE_IDLE;
        }

        if (!canIntercept){
            switch (actionMasked){
                case MotionEvent.ACTION_DOWN:
                    onTouchDown(e);
                    break;
                case MotionEvent.ACTION_MOVE:
                    onTouchMove(e);
                    break;
                case MotionEvent.ACTION_UP:
                    onTouchUp(e);
                    setScrollVerticallyEnable(true);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    checkTouchedView();
                    if (touchedChildView != null){
                        touchedChildView.setPressed(false);
                        mRecyclerView.refreshDrawableState();
                    }
                    setScrollVerticallyEnable(true);
                    break;
            }
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    private void onTouchDown(MotionEvent event){
        mLastMotionX = mInitialMotionX = event.getX();
        mInitialMotionY = event.getY();
        mInitialMotionRawX = event.getRawX();
        mInitialMotionRawY = event.getRawY();
        if (mPendingCheckForTap == null){
            mPendingCheckForTap = new CheckForTap();
        }
        closeOverflowIfNeed();
        mRecyclerView.postDelayed(mPendingCheckForTap,tapTimeout);
        fitTouchDown();
        touchedChildView = mRecyclerView.findChildViewUnder(mInitialMotionX, mInitialMotionY);
        initScrollView();
    }

    private void fitTouchDown(){
        mTouchMode = TOUCH_MODE_DOWN;
        isTouchedMove = false;
        mSlideState = SLIDE_NONE;
    }

    private void onTouchMove(MotionEvent event){
        checkTouchedView();
        switch (mTouchMode){
            case TOUCH_MODE_DOWN:
            case TOUCH_MODE_TAP:
            case TOUCH_MODE_DONE_WAITING:
                checkIfMoved(event);
                if (isTouchedMove){
                    mRecyclerView.removeCallbacks(mTouchMode == TOUCH_MODE_DOWN?mPendingCheckForTap:mPendingCheckForLongPress);
                    if (touchedChildView != null) {
                        touchedChildView.setPressed(false);
                        mRecyclerView.refreshDrawableState();
                    }
                    if (mSlideState == SLIDE_H) {
                        performSlideWork(event);
                    }
                }
                break;
        }
    }

    private void onTouchUp(MotionEvent event){
        checkTouchedView();
        if (canAutoOpenOrCloseOverflowIfHalf(event)){
            return;
        }

        if (touchedInOverflow(event)){
            mRecyclerView.removeCallbacks(mTouchMode == TOUCH_MODE_DOWN?mPendingCheckForTap:mPendingCheckForLongPress);
            if (touchedChildView != null) {
                touchedChildView.setPressed(false);
                mRecyclerView.refreshDrawableState();
            }
            return;
        }

        switch (mTouchMode){
            case TOUCH_MODE_DOWN:
            case TOUCH_MODE_TAP:
            case TOUCH_MODE_DONE_WAITING:
                if (touchedChildView != null){
                    if (mTouchMode != TOUCH_MODE_DOWN){
                        touchedChildView.setPressed(false);
                    }
                    final float x = event.getX();
                    final boolean inner = x >= mRecyclerView.getLeft() && x <= mRecyclerView.getRight();
                    if (inner && !ViewCompat.hasExplicitFocusable(touchedChildView)
                            && !TouchedHelper.isTouchedInClickableView(touchedChildView,(int) mInitialMotionRawX,(int) mInitialMotionRawY)){
                        if (mTouchMode == TOUCH_MODE_DOWN || mTouchMode == TOUCH_MODE_TAP){
                            mRecyclerView.removeCallbacks(mTouchMode == TOUCH_MODE_DOWN?mPendingCheckForTap:mPendingCheckForLongPress);
                        }
                        if (mRecyclerView.isClickable() && canAnswerClick) {
                            if (mPerformClick == null) {
                                mPerformClick = new PerformClick();
                            }
                            touchedChildView.setPressed(true);
                            mRecyclerView.refreshDrawableState();
                            mRecyclerView.postDelayed(mPerformClick,pressedStateDuration);
                        }
                    }
                }
                mTouchMode = TOUCH_MODE_REST;
                break;
        }
    }

    private void checkTouchedView(){
        if (touchedChildView == null) {
            touchedChildView = mRecyclerView.findChildViewUnder(mInitialMotionX, mInitialMotionY);
        }
        initScrollView();
    }


    private void initScrollView(){
        if (touchedChildView != null){
            if (touchedChildView instanceof  LuckyContainerLayout){
                luckyContainerLayout = (LuckyContainerLayout) touchedChildView;
                luckyItemContentLayout = luckyContainerLayout.getItemContentLayout();
                luckyItemContentLayout.setParent(luckyContainerLayout);
                luckyItemContentLayout.setOnOverflowChangedAnimator(mOverflowChangedAnimator);
            }
        }else if (!isChildOverflowOpen()){
            luckyContainerLayout = null;
            luckyItemContentLayout = null;
        }
    }

    /**
     * 开始处理滑动
     * @param event
     */
    private void performSlideWork(MotionEvent event) {
        if (null == luckyContainerLayout || null == luckyItemContentLayout){
            return;
        }
        final float x = event.getX();
        final int deltaX = (int) (mLastMotionX - x);
        final int overflowWidth = luckyContainerLayout.getOverflowLayoutWidth();
        final int scrollToX = luckyItemContentLayout.getScrollX()+deltaX;
        final float percent;
        if (scrollToX <= 0){//禁止向右滑出屏幕
            overflowState = OVERFLOW_STATE_CLOSED;
            luckyItemContentLayout.scrollTo(0,0);
            mLastMotionX = x;
            percent = 0f;
        }else if (scrollToX >= overflowWidth){//overflow界面显示完全之后，禁止往左滑动，但可以往右滑动
            overflowState = OVERFLOW_STATE_OPENED;
            luckyItemContentLayout.scrollTo(overflowWidth,0);
            mLastMotionX = x;
            percent = 1f;
        }else {
            overflowState = OVERFLOW_STATE_OPENING;
            luckyItemContentLayout.scrollTo(scrollToX,0);
            mLastMotionX = x;
            percent = 1f * scrollToX / overflowWidth;
        }
        if (mOverflowChangedAnimator != null){
            mOverflowChangedAnimator.onOverflowChanged(luckyContainerLayout.getOverflowView(),overflowWidth,percent);
        }
    }

    private boolean isChildOverflowOpen(){
        return overflowState == OVERFLOW_STATE_OPENED;
    }

    /**
     * 判断overflow是否打开还是关闭
     * @param event
     * @return
     */
    private boolean canAutoOpenOrCloseOverflowIfHalf(MotionEvent event){

        if (null == luckyContainerLayout){
            return false;
        }

        final float x = event.getX();
        if (mSlideState == SLIDE_H && overflowState == OVERFLOW_STATE_OPENING){
            final float distance = Math.abs(x - mInitialMotionX);
            final int overflowWidth = luckyContainerLayout.getOverflowLayoutWidth();
            if (x < mInitialMotionX) {
                if (distance > overflowWidth / 2f) {
                    autoScroll(distance, overflowWidth - distance, true);
                } else {
                    autoScroll(distance, -distance, false);
                }
            }else {
                if (distance > overflowWidth / 2f) {
                    autoScroll(overflowWidth-distance,distance-overflowWidth, false);
                } else {
                    autoScroll(overflowWidth-distance,distance, true);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 处理判断是否点击在overflow区域
     * @param event
     * @return
     */
    private boolean touchedInOverflow(MotionEvent event){

        if (luckyContainerLayout == null){
            return false;
        }

        int x = (int) event.getX();
        int y = (int) event.getY();

        if (isChildOverflowOpen() && checkIfInPreTouchedViewRect(x,y)){
            //点击在item上，关闭overflow
            if (checkIfInPreTouchedItemViewRect(x,y)){
                autoScroll(luckyContainerLayout.getOverflowLayoutWidth(),-luckyContainerLayout.getOverflowLayoutWidth(),false);
                return true;
            }else {
                View view = luckyContainerLayout.getOverflowView();
                initOverflowRect();
                if (view instanceof ViewGroup){
                    ViewGroup overflowGroup = (ViewGroup) view;
                    final int count = overflowGroup.getChildCount();
                    for (int i = 0; i < count;++i){
                        View child = overflowGroup.getChildAt(i);
                        if (innerOverflowItem(child,x,y,true)){
                            return true;
                        }
                    }
                    return false;
                }else {
                    return innerOverflowItem(view,x,y,false);
                }
            }
        }

        return false;
    }

    private boolean innerOverflowItem(View view,int x,int y,boolean hasParent){
        view.getHitRect(overChildRect);
        int width = overChildRect.width();
        if (hasParent) {
            overChildRect.left += outRect.left;
            overChildRect.right = overChildRect.left + width;
        }
        overChildRect.top = outRect.top;
        overChildRect.bottom = outRect.bottom;
        if (overChildRect.contains(x,y)){
            if (mOverflowClickListener != null){
                RecyclerView.ViewHolder viewHolder = mRecyclerView.getChildViewHolder(touchedChildView);
                if(mOverflowClickListener.onOverflowItemClick(mRecyclerView,view,viewHolder.getAdapterPosition())){
                    closeOpedOverflow();
                }
            }
            return true;
        }
        return false;
    }


    /**
     * 自动滑动
     * @param startX
     * @param delatx
     * @param isOpen
     */
    private void autoScroll(float startX,float delatx,boolean isOpen){
        if (luckyItemContentLayout != null) {
            luckyItemContentLayout.startScroll((int) startX, 0, (int) delatx, 0);
        }
        overflowState = isOpen ? OVERFLOW_STATE_OPENED : OVERFLOW_STATE_CLOSED;
    }

    /**
     * 当再次触摸屏幕的时候，主动关闭已经打开的overflow
     */
    private void closeOverflowIfNeed(){
        if (!isChildOverflowOpen() || luckyContainerLayout == null || touchedChildView == null){
            canAnswerClick = true;
            return;
        }
        final boolean innerPreTouchedView = checkIfInPreTouchedViewRect((int) mInitialMotionX,(int) mInitialMotionY);
        canAnswerClick = !innerPreTouchedView;
        if (!innerPreTouchedView){
            autoScroll(luckyContainerLayout.getOverflowLayoutWidth(),-luckyContainerLayout.getOverflowLayoutWidth(),false);
        }
    }

    /**
     * 判断当前手势操作是否还在rect之内
     * @return
     */
    private boolean pointInnerView(Rect rect,int x,int y) {
        Rect out = new Rect();
        mRecyclerView.getDrawingRect(out);
        return out.contains(x, y) && rect.contains(x, y);
    }

    private void initTouchedViewRect(){
        if (touchedChildView != null){
            final float translationX = touchedChildView.getTranslationX();
            final float translationY = touchedChildView.getTranslationY();
            outRect.left = (int) (touchedChildView.getLeft() + translationX);
            outRect.right = (int)(touchedChildView.getRight() + translationX);
            outRect.top = (int) (touchedChildView.getTop() + translationY);
            outRect.bottom = (int) (touchedChildView.getBottom() +translationY);
        }else {
            outRect.set(0,0,0,0);
        }
    }

    /**
     * 当再次touch屏幕的时候，判断当前位置是否是上次touched的同一个view
     * @return
     */
    private boolean checkIfInPreTouchedViewRect(int x,int y){
        initTouchedViewRect();
        return pointInnerView(outRect,x,y);
    }

    /**
     * 初始化overflow区域
     */
    private void initOverflowRect(){
        initTouchedViewRect();
        if (luckyContainerLayout != null) {
            outRect.left = outRect.right - luckyContainerLayout.getOverflowLayoutWidth();
        }
    }


    /**
     * 当再次touch屏幕的时候，判断当前位置是否是上次touched的同一个view,并touch在itemview布局上
     * @return
     */
    private boolean checkIfInPreTouchedItemViewRect(int x,int y){
        initTouchedViewRect();
        if (luckyContainerLayout != null){
            outRect.right -= luckyContainerLayout.getOverflowLayoutWidth();
        }else {
            outRect.set(0,0,0,0);
        }
        return pointInnerView(outRect,x,y);
    }

    /**
     * 判断是否滚动
     * @param event
     * @return
     */
    private void checkIfMoved(MotionEvent event){
        if (!isTouchedMove) {
            final float x = event.getX();
            final float y = event.getY();
            if (Math.max(Math.abs(x - mInitialMotionX), Math.abs(y - mInitialMotionY)) > touchSlop) {
                isTouchedMove = true;
                canAnswerClick = false;
                if (mSlideState == SLIDE_NONE) {
                    mSlideState = Math.abs(x - mInitialMotionX) > Math.abs(y - mInitialMotionY) ? SLIDE_H : SLIDE_V;
                }
                setScrollVerticallyEnable(mSlideState != SLIDE_H);
                if (mTouchMode == TOUCH_MODE_DOWN || mTouchMode == TOUCH_MODE_TAP){
                    mRecyclerView.removeCallbacks(mTouchMode == TOUCH_MODE_DOWN?mPendingCheckForTap:mPendingCheckForLongPress);
                    if (touchedChildView != null){
                        touchedChildView.setPressed(false);
                        mRecyclerView.refreshDrawableState();
                    }
                }
            }
        }
    }

    /**
     * 设置是否可以纵向滚动
     * @param scrollVerticallyEnable
     */
    private void setScrollVerticallyEnable(boolean scrollVerticallyEnable) {
        RecyclerView.LayoutManager manager = mRecyclerView.getLayoutManager();
        if (manager instanceof LinearLayoutManagerWrapper){
            LinearLayoutManagerWrapper wrapper = (LinearLayoutManagerWrapper) manager;
            wrapper.setScrollVerticallyEnable(scrollVerticallyEnable);
        }
    }

    protected void closeOpedOverflow(){
        if (isChildOverflowOpen() && luckyContainerLayout != null) {
            autoScroll(luckyContainerLayout.getOverflowLayoutWidth(), -luckyContainerLayout.getOverflowLayoutWidth(), false);
        }
    }

    protected void setOnItemClickListener(OnItemClickListener listener) {
        this.mClickListener = listener;
    }

    protected void setOnItemLongClickListner(OnItemLongClickListner listener) {
        this.mLongClickListener = listener;
    }

    protected void setOnOverflowItemClickListener(OnOverflowItemClickListener listener){
        this.mOverflowClickListener = listener;
    }

    protected void setOnOverflowChangedAnimator(OnOverflowChangedAnimator listener){
        this.mOverflowChangedAnimator = listener;
    }

    /**
     * 点击事件监听接口
     */
    public interface OnItemClickListener{
        /**
         * 点击事件回调
         * @param rv
         * @param view
         * @param position
         */
        void onItemClick(RecyclerView rv, View view, int position);
    }

    /**
     * 长按事件监听
     */
    public interface OnItemLongClickListner{
        /**
         * 长按事件回调
         * @param rv
         * @param view
         * @param position
         */
        void onItemLongClick(RecyclerView rv, View view, int position);
    }

    /**
     * overflow布局item点击事件接口
     */
    public interface OnOverflowItemClickListener{
        /**
         * @return true close opened overflow
         */
        boolean onOverflowItemClick(RecyclerView rv, View view, int position);
    }

    /**
     * overflow显现变化回调
     */
    public interface OnOverflowChangedAnimator{
        void onOverflowChanged(View overflowView, int overflowWidth, @FloatRange(from = 0f, to = 1f) float percent);
    }


    private final class CheckForTap implements Runnable{

        @Override
        public void run() {
            if (mTouchMode == TOUCH_MODE_DOWN){
                mTouchMode = TOUCH_MODE_TAP;
                if (touchedChildView != null && !ViewCompat.hasExplicitFocusable(touchedChildView) && canAnswerClick){
//                    touchedChildView.setPressed(true);
//                    mRecyclerView.refreshDrawableState();
                    if (mRecyclerView.isLongClickable()){
                        if (mPendingCheckForLongPress == null){
                            mPendingCheckForLongPress = new CheckForLongPress();
                        }
                        mRecyclerView.postDelayed(mPendingCheckForLongPress,longPressTimeout);
                    }else {
                        mTouchMode = TOUCH_MODE_DONE_WAITING;
                    }
                }
            }
        }
    }

    private final class  CheckForLongPress implements Runnable{

        @Override
        public void run() {
            mTouchMode = TOUCH_MODE_REST;
            if (touchedChildView != null) {
                touchedChildView.setPressed(false);
                mRecyclerView.refreshDrawableState();
                if (mLongClickListener != null){
                    RecyclerView.ViewHolder vh = mRecyclerView.getChildViewHolder(touchedChildView);
                    mLongClickListener.onItemLongClick(mRecyclerView,touchedChildView,vh.getAdapterPosition());
                }
            }
        }
    }

    private final class PerformClick implements Runnable {

        @Override
        public void run() {
            mTouchMode = TOUCH_MODE_REST;
            if (touchedChildView != null) {
                touchedChildView.setPressed(false);
                mRecyclerView.refreshDrawableState();
                if (mClickListener != null){
                    RecyclerView.ViewHolder vh = mRecyclerView.getChildViewHolder(touchedChildView);
                    mClickListener.onItemClick(mRecyclerView,touchedChildView,vh.getAdapterPosition());
                }
            }
        }
    }

}
