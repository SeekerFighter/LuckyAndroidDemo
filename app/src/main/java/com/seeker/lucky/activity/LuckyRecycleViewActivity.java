package com.seeker.lucky.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.seeker.lucky.R;
import com.seeker.lucky.recycleview.LuckyAnimRecycleView;
import com.seeker.lucky.recycleview.LuckyItemToucListener;
import com.seeker.lucky.recycleview.LuckyLinearDecoration;
import com.seeker.lucky.recycleview.LuckyRecyleAdapter;
import com.seeker.lucky.recycleview.LuckyViewHolder;

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

        makeDatas();

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

    private void makeDatas(){
        for (int i = 0;i<30;i++){
            datas.add(String.valueOf(i));
        }
        adapter.notifyDataChanged();
    }

    private static final class DataAdapter extends LuckyRecyleAdapter<String,LuckyViewHolder>{

        public DataAdapter(Context context, List<String> datas) {
            super(context, datas);
        }

        @Override
        public LuckyViewHolder convertCreateViewHolder(View itemView, int viewType) {
            return new LuckyViewHolder(itemView);
        }

        @Override
        public void convertBindViewHolder(LuckyViewHolder viewHolder, int position) {
            viewHolder.setText(R.id.textView,mDatas.get(position));
        }

        @Override
        public int getItemChildLayoutId(int viewType) {
            return R.layout.item_linear;
        }

        @Override
        public boolean hasOverflowChild() {
            return true;
        }

        @Override
        public int getOverflowChildLayoutId(int viewType) {
            return R.layout.item_linear_overflow;
        }
    }


}
