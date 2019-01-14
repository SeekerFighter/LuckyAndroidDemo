package com.seeker.lucky.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.seeker.lucky.R;
import com.seeker.lucky.utils.ColorHelper;
import com.seeker.lucky.utils.DrawableHelper;

import java.util.Random;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Seeker
 * @date 2019/1/11/011  15:59
 * @describe TODO
 */
public class DrawableHelperActivity extends AppCompatActivity implements View.OnClickListener {

    private Random random = new Random(10);
    private Random random2 = new Random(4);
    private Random random3 = new Random(1);

    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawablehelper);
        imageView = findViewById(R.id.imageView);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.btn10).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Drawable drawable = null;
        switch (v.getId()){
            case R.id.btn1:
                drawable = DrawableHelper.createCornerDrawable(randomShape(),
                        ColorHelper.getRandomColor(),
                        ColorHelper.getRandomColor(),
                        random2.nextInt(12),
                        random.nextInt(90),
                        DrawableHelper.LEFT_TOP,
                        null);
                break;
            case R.id.btn2:
                drawable = DrawableHelper.createCornerDrawable(randomShape(),
                        ColorHelper.getRandomColor(),
                        ColorHelper.getRandomColor(),
                        random2.nextInt(12),
                        random.nextInt(90),
                        DrawableHelper.RIGHT_TOP,
                        null);
                break;
            case R.id.btn3:
                drawable = DrawableHelper.createCornerDrawable(randomShape(),
                        ColorHelper.getRandomColor(),
                        ColorHelper.getRandomColor(),
                        random2.nextInt(12),
                        random.nextInt(90),
                        DrawableHelper.RIGHT_BOTTOM,
                        null);
                break;
            case R.id.btn4:
                drawable = DrawableHelper.createCornerDrawable(randomShape(),
                        ColorHelper.getRandomColor(),
                        ColorHelper.getRandomColor(),
                        random2.nextInt(12),
                        random.nextInt(90),
                        DrawableHelper.LEFT_BOTTOM,
                        null);
                break;
            case R.id.btn5:
                drawable = DrawableHelper.createCornerDrawable(randomShape(),
                        ColorHelper.getRandomColor(),
                        ColorHelper.getRandomColor(),
                        random2.nextInt(12),
                        random.nextInt(90),
                        DrawableHelper.LEFT,
                        null);
                break;
            case R.id.btn6:
                drawable = DrawableHelper.createCornerDrawable(randomShape(),
                        ColorHelper.getRandomColor(),
                        ColorHelper.getRandomColor(),
                        random2.nextInt(12),
                        random.nextInt(90),
                        DrawableHelper.RIGHT,
                        null);
                break;
            case R.id.btn7:
                drawable = DrawableHelper.createCornerDrawable(randomShape(),
                        ColorHelper.getRandomColor(),
                        ColorHelper.getRandomColor(),
                        random2.nextInt(12),
                        random.nextInt(90),
                        DrawableHelper.TOP,
                        null);
                break;
            case R.id.btn8:
                drawable = DrawableHelper.createCornerDrawable(randomShape(),
                        ColorHelper.getRandomColor(),
                        ColorHelper.getRandomColor(),
                        random2.nextInt(12),
                        random.nextInt(90),
                        DrawableHelper.BOTTOM,
                        null);
                break;
            case R.id.btn9:
                drawable = DrawableHelper.createCornerDrawable(randomShape(),
                        ColorHelper.getRandomColor(),
                        ColorHelper.getRandomColor(),
                        random2.nextInt(12),
                        random.nextInt(90),
                        DrawableHelper.ALL,
                        null);
                break;
            case R.id.btn10:
                drawable = DrawableHelper.createCornerDrawable(randomShape(),
                        ColorHelper.getRandomColor(),
                        ColorHelper.getRandomColor(),
                        random2.nextInt(12),
                        random.nextInt(90),
                        DrawableHelper.NONE,
                        null);
                break;
        }
        imageView.setImageDrawable(drawable);
    }

    private @DrawableHelper.Shape int randomShape(){
        switch (random3.nextInt(2)){
            case 0:
                return DrawableHelper.RECTANGLE;
            case 1:
                return DrawableHelper.OVAL;
            default:return DrawableHelper.RECTANGLE;
        }
    }

}
