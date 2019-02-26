package com.seeker.lucky.widget.pager;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Seeker
 * @date 2019/2/18/018  10:54
 * @describe 适配器
 */
public class CardAdapter<VH extends CardAdapter.CardViewHolder,T extends CardEntity> extends RecyclerView.Adapter<VH> {

    private List<Card> cards;

    public CardAdapter(){
        cards = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     * 添加一个卡片
     * @param card
     */
    public void addCard(@NonNull Card card){
        cards.add(card);
    }



    public abstract class CardViewHolder extends RecyclerView.ViewHolder{

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
