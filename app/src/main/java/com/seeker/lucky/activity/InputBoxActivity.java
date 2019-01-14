package com.seeker.lucky.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.seeker.lucky.R;
import com.seeker.lucky.widget.InputBox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Seeker
 * @date 2019/1/10/010  13:14
 */
public class InputBoxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputbox);
        final InputBox inputBox = findViewById(R.id.inputBox);
        findViewById(R.id.getCode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InputBoxActivity.this,inputBox.getInputCode(),Toast.LENGTH_SHORT).show();
            }
        });

        final Button display = findViewById(R.id.display);

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputBox.isCharDisplay()){
                    inputBox.setCharDisplay(false);
                    display.setText("明文显示");
                }else {
                    inputBox.setCharDisplay(true);
                    display.setText("密码显示");
                }
            }
        });
    }
}
