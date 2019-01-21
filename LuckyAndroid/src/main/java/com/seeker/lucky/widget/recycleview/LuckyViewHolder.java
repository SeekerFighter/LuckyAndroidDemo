package com.seeker.lucky.widget.recycleview;

import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Seeker
 * @date 2018/7/28/028  11:02
 */
public class LuckyViewHolder extends RecyclerView.ViewHolder{

    private SparseArray<View> mViews;

    public LuckyViewHolder(View itemView) {
        super(itemView);
        this.mViews = new SparseArray<>();
    }

    public View getItemView(){
        return itemView;
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V getView(int viewId){

        View view = mViews.get(viewId);
        if (view == null){
            view = itemView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        if (view == null){
            throw new NullPointerException("according viewId["+viewId+"] does't find any view,check it.");
        }
        return (V) view;
    }

    public void setText(int viewId,CharSequence charSequence){
        TextView textView = getView(viewId);
        textView.setText(charSequence);
    }

    public void setText(int viewId,Drawable left,Spannable text){
        TextView textView = getView(viewId);
        textView.setText(text);
        if (left != null) {
            Paint paint = textView.getPaint();
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            int size = (int) Math.floor((fontMetrics.descent - fontMetrics.ascent));
            left.setBounds(0, 0,size,size);
        }
        textView.setCompoundDrawables(left,null,null,null);
    }

    public void setVisibility(int viewId,int visiblity){
        View view = getView(viewId);
        view.setVisibility(visiblity);
    }

    public void setImageDrawable(int viewId, Drawable drawable){
        ImageView imageView = getView(viewId);
        imageView.setImageDrawable(drawable);
    }

    public void setImageDrawable(int viewId, int resId){
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resId);
    }

}
