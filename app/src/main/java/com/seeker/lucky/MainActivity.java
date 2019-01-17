package com.seeker.lucky;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.seeker.lucky.activity.CheckedTableViewActivity;
import com.seeker.lucky.activity.DrawableHelperActivity;
import com.seeker.lucky.activity.InputBoxActivity;
import com.seeker.lucky.activity.LoadViewActivity;
import com.seeker.lucky.activity.LuckyRecycleViewActivity;
import com.seeker.lucky.activity.MemberViewActivity;
import com.seeker.lucky.activity.RoundImageViewActivity;
import com.seeker.lucky.activity.SwitchButtonActivity;
import com.seeker.lucky.activity.TabSegmentActivity;
import com.seeker.lucky.entiy.DemoPager;
import com.seeker.lucky.recycleview.LuckyAnimRecycleView;
import com.seeker.lucky.recycleview.LuckyGridDecoration;
import com.seeker.lucky.recycleview.LuckyItemToucListener;
import com.seeker.lucky.recycleview.LuckyRecyleAdapter;
import com.seeker.lucky.recycleview.LuckyViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private static final int SPAN_COUNT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LuckyAnimRecycleView recycleView = findViewById(R.id.mainContainer);
        recycleView.setLayoutManager(new GridLayoutManager(this,SPAN_COUNT));
        recycleView.addItemDecoration(new LuckyGridDecoration(this,SPAN_COUNT));
        final List<DemoPager> pagers = getDemoPagerDatas();
        DemoPagerAdapter adapter = new DemoPagerAdapter(this,pagers);
        recycleView.setAdapter(adapter);
        recycleView.setOnItemClickListener(new LuckyItemToucListener.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView rv, View view, int position) {
                startActivity(pagers.get(position).getIntent());
            }
        });
    }

    private List<DemoPager> getDemoPagerDatas(){
        List<DemoPager> pagers = new ArrayList<>();
        pagers.add(new DemoPager("MemberView",new Intent(this,MemberViewActivity.class)));
        pagers.add(new DemoPager("LuckyRecycleView",new Intent(this,LuckyRecycleViewActivity.class)));
        pagers.add(new DemoPager("InputBox",new Intent(this,InputBoxActivity.class)));
        pagers.add(new DemoPager("CheckedTableView",new Intent(this,CheckedTableViewActivity.class)));
        pagers.add(new DemoPager("SwitchButton",new Intent(this,SwitchButtonActivity.class)));
        pagers.add(new DemoPager("RoundImageView",new Intent(this,RoundImageViewActivity.class)));
        pagers.add(new DemoPager("DrawableHelper",new Intent(this,DrawableHelperActivity.class)));
        pagers.add(new DemoPager("TabSegment",new Intent(this,TabSegmentActivity.class)));
        pagers.add(new DemoPager("LoadingView",new Intent(this,LoadViewActivity.class)));
        return pagers;
    }

    private static final class DemoPagerAdapter extends LuckyRecyleAdapter<DemoPager,LuckyViewHolder>{

        public DemoPagerAdapter(Context context, List<DemoPager> datas) {
            super(context, datas);
        }

        @Override
        public LuckyViewHolder convertCreateViewHolder(View itemView, int viewType) {
            return new LuckyViewHolder(itemView);
        }

        @Override
        public void convertBindViewHolder(LuckyViewHolder viewHolder, int position) {
            viewHolder.setText(R.id.demoTitle,mDatas.get(position).getDemoTitle());
        }

        @Override
        public int getItemChildLayoutId(int viewType) {
            return R.layout.item_grid;
        }
    }


}
