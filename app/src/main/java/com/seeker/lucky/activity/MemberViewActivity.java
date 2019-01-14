package com.seeker.lucky.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.seeker.lucky.R;
import com.seeker.lucky.memberview.MemberView;
import com.seeker.lucky.memberview.delegate.OnDelegateCheckedChangeListener;
import com.seeker.lucky.widget.SwitchButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Seeker
 * @date 2019/1/9/009  11:19
 * @describe TODO
 */
public class MemberViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memberview);

        findViewById(R.id.nextCell).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MemberViewActivity.this,"to next pager",Toast.LENGTH_SHORT).show();
            }
        });

        ((MemberView)findViewById(R.id.switcherCell)).setOnCheckedChangeListener(new OnDelegateCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MemberView cellItemView, boolean checked) {
                Toast.makeText(MemberViewActivity.this,checked?"打开":"关闭",Toast.LENGTH_SHORT).show();
            }
        });

        ((MemberView)findViewById(R.id.checkboxCell)).setOnCheckedChangeListener(new OnDelegateCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MemberView cellItemView, boolean checked) {
                Toast.makeText(MemberViewActivity.this,checked?"选中":"反选",Toast.LENGTH_SHORT).show();
            }
        });

        ((SwitchButton)findViewById(R.id.switchButton)).setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                Toast.makeText(MemberViewActivity.this,isChecked?"选中":"反选",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
