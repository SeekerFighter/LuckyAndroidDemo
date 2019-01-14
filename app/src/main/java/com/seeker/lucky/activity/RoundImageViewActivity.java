package com.seeker.lucky.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.seeker.lucky.R;
import com.seeker.lucky.widget.RoundImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Seeker
 * @date 2019/1/11/011  13:32
 */
public class RoundImageViewActivity extends AppCompatActivity implements View.OnClickListener {

    private RoundImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roundimageview);
        imageView = findViewById(R.id.roundImageView);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        reset();
    }

    private void reset(){
        imageView.setBorderColor(0xFF008577);
        imageView.setBorderWidth(2);
        imageView.setCornerRadius(10);
        imageView.setSelectedMaskColor(0x7FEF5362);
        imageView.setSelectedBorderColor(0xFFEF5362);
        imageView.setSelectedBorderWidth(3);
        imageView.setTouchSelectModeEnabled(true);
        imageView.setCircle(false);
        imageView.setOval(false);
    }

    @Override
    public void onClick(View v) {
        reset();
        switch (v.getId()){
            case R.id.btn1:
                imageView.setBorderColor(Color.BLACK);
                imageView.setBorderWidth(5);
                break;
            case R.id.btn2:
                imageView.setSelectedBorderWidth(6);
                imageView.setSelectedBorderColor(Color.GREEN);
                break;
            case R.id.btn3:
                imageView.setSelectedMaskColor(0x0FEF5362);
                break;
            case R.id.btn4:
                if (imageView.isSelected()) {
                    imageView.setSelected(false);
                } else {
                    imageView.setSelected(true);
                }
                break;
            case R.id.btn5:
                imageView.setCornerRadius(50);
                break;
            case R.id.btn6:
                imageView.setCircle(true);
                break;
            case R.id.btn7:
                imageView.setOval(true);
                break;
            case R.id.btn8:
                imageView.setTouchSelectModeEnabled(false);
                break;
            case R.id.btn9:

                break;
        }
    }
}
