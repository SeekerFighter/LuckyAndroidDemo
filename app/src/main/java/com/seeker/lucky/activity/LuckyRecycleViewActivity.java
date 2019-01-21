package com.seeker.lucky.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.seeker.lucky.R;
import com.seeker.lucky.widget.recycleview.LuckyAnimRecycleView;
import com.seeker.lucky.widget.recycleview.LuckyItemToucListener;
import com.seeker.lucky.widget.recycleview.decoration.LuckyLinearDecoration;
import com.seeker.lucky.widget.recycleview.LuckyRecyclerAdapter;
import com.seeker.lucky.widget.recycleview.LuckyViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Seeker
 * @date 2019/1/9/009  16:29
 */
public class LuckyRecycleViewActivity extends AppCompatActivity {

    private LuckyAnimRecycleView recycleView;

    private DataAdapter adapter;

    private List<String> datas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lucky_recycleview_layout);

        recycleView = findViewById(R.id.luckyAnimRecycleView);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.addItemDecoration(new LuckyLinearDecoration(this,LuckyLinearDecoration.VERTICAL));

        adapter = new DataAdapter(this,datas);

        recycleView.setAdapter(adapter);

        makeData();

        recycleView.setOnItemClickListener(new LuckyItemToucListener.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView rv, View view, int position) {
                Toast.makeText(LuckyRecycleViewActivity.this,"点击"+datas.get(position),Toast.LENGTH_SHORT).show();
            }
        });
        recycleView.setOnItemLongClickListner(new LuckyItemToucListener.OnItemLongClickListner() {
            @Override
            public void onItemLongClick(RecyclerView rv, View view, int position) {
                Toast.makeText(LuckyRecycleViewActivity.this,"长按"+datas.get(position),Toast.LENGTH_SHORT).show();
            }
        });
        recycleView.setOnOverflowItemClickListener(new LuckyItemToucListener.OnOverflowItemClickListener() {
            @Override
            public boolean onOverflowItemClick(RecyclerView rv, View view, int position) {
                if (view.getId() == R.id.delete) {
                    Toast.makeText(LuckyRecycleViewActivity.this,"删除"+datas.get(position),Toast.LENGTH_SHORT).show();
                    datas.remove(position);
                    adapter.notifyItemRemoved(position);
                    recycleView.closeOpenOverflow(500);
                    return false;
                }else if (view.getId() == R.id.mark){
                    Toast.makeText(LuckyRecycleViewActivity.this,"备注"+datas.get(position),Toast.LENGTH_SHORT).show();
                    return true;
                }
                return true;
            }
        });
    }

    private void makeData(){
        for (int i = 0;i<30;i++){
            datas.add(String.valueOf(i));
        }
        adapter.notifyDataChangedWithLayoutAnim(true);
    }

    private static final class DataAdapter extends LuckyRecyclerAdapter<String> {

        public DataAdapter(Context context, List<String> data) {
            super(context, data);
        }

        @Override
        public void convertDataViewHolder(LuckyViewHolder viewHolder, int position) {
            viewHolder.setText(R.id.textView,mData.get(position));
        }

        @Override
        public int getItemLayoutResId(int viewType) {
            return R.layout.item_linear;
        }

        @Override
        public boolean showItemSwipeLayout() {
            return true;
        }

        @Override
        public int getItemSwipeLayoutResId(int viewType) {
            return R.layout.item_linear_overflow;
        }
    }


}
