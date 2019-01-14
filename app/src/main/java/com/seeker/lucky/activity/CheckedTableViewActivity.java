package com.seeker.lucky.activity;

import android.os.Bundle;
import android.widget.CheckedTextView;
import android.widget.Toast;

import com.seeker.lucky.R;
import com.seeker.lucky.widget.CheckedTableView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Seeker
 * @date 2019/1/10/010  17:38
 */
public class CheckedTableViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkedtableview);
        final CheckedTableView checkedTableView = findViewById(R.id.checkTable);
        checkedTableView.setCheckedIndex(1);
        checkedTableView.setOnTabCheckedListener(new CheckedTableView.OnTabCheckedListener() {
            @Override
            public void onChecked(CheckedTextView checkedView, int position) {
                Toast.makeText(CheckedTableViewActivity.this,checkedTableView.getCheckedText(position),Toast.LENGTH_SHORT).show();
            }
        });


        final CheckedTableView checkedTableView2 = findViewById(R.id.checkTable2);
        checkedTableView2.setOnTabCheckedListener(new CheckedTableView.OnTabCheckedListener() {
            @Override
            public void onChecked(CheckedTextView checkedView, int position) {
                Toast.makeText(CheckedTableViewActivity.this,checkedTableView2.getCheckedText(position),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
