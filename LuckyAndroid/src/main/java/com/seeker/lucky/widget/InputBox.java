package com.seeker.lucky.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.seeker.lucky.R;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * @author Seeker
 * @date 2019/1/10/010  13:11
 * @describe 输入框，仿ios，微信支付等
 */
public class InputBox extends AppCompatEditText {

    private static final String REPLACECHAR_DEFAULT = "●";

    private static final int COUNT = 6;

    private static final char[] EMPTY = new char[0];

    private int boxCount;

    private int boxWidth;

    private int boxHeight;

    private float boxRadius;

    private int boxSpace;

    private int boxStrokeWidth;

    private int strokeNormalColor;

    private int strokeInputingColor;

    private boolean charDisplay;

    private String replaceChar;

    private Paint boxPaint;

    private boolean charAllCaps;

    private RectF[] boxs;

    private final float leftRadius[] = new float[8];

    private final float rightRadius[] = new float[8];

    private final Path boxPath = new Path();

    public InputBox(Context context) {
        this(context,null);
    }

    public InputBox(Context context, AttributeSet attrs) {
        this(context, attrs, androidx.appcompat.R.attr.editTextStyle);
    }

    public InputBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttrs(context,attrs,defStyleAttr);
    }

    private void setAttrs(Context context,AttributeSet attrs,int defStyleAttr){
        final Resources.Theme theme = context.getTheme();
        TypedArray ta = theme.obtainStyledAttributes(attrs, R.styleable.InputBox,defStyleAttr,0);
        if (ta != null){
            try {
                boxCount = ta.getInt(R.styleable.InputBox_boxCount,COUNT);
                boxWidth = ta.getDimensionPixelSize(R.styleable.InputBox_boxWidth,0);
                boxHeight = ta.getDimensionPixelSize(R.styleable.InputBox_boxHeight,0);
                boxSpace = ta.getDimensionPixelSize(R.styleable.InputBox_boxSpace,0);
                boxSpace = Math.max(0,boxSpace);
                boxRadius = ta.getDimensionPixelSize(R.styleable.InputBox_boxRadius,0);
                boxStrokeWidth = ta.getDimensionPixelSize(R.styleable.InputBox_boxStrokeWidth,0);
                strokeNormalColor = ta.getColor(R.styleable.InputBox_boxStrokeNormalColor,0xFF5A5A5A);
                strokeInputingColor = ta.getColor(R.styleable.InputBox_boxStrokeInputingColor,0xFFFA8C23);
                charAllCaps = ta.getBoolean(R.styleable.InputBox_boxCharAllCaps,false);
                charDisplay = ta.getBoolean(R.styleable.InputBox_boxCharDisplay,true);
                String replaceChars = ta.getString(R.styleable.InputBox_boxReplaceChar);
                setReplaceChar(replaceChars);
            }finally {
                ta.recycle();
            }
        }
        setSingleLine();
        setCursorVisible(false);
        if (charAllCaps){
            setFilters(new InputFilter[]{new InputFilter.LengthFilter(boxCount),AllCaps.getInstance()});
        }else {
            setFilters(new InputFilter[]{new InputFilter.LengthFilter(boxCount)});
        }
        initBox();
    }

    private void initBox(){
        boxs = new RectF[boxCount];
        for (int i = 0;i < boxCount;i++){
            boxs[i] = new RectF();
        }
        boxPaint = new Paint();
        boxPaint.setStrokeWidth(boxStrokeWidth);
        boxPaint.setColor(strokeNormalColor);
        boxPaint.setAntiAlias(true);
        boxPaint.setStyle(Paint.Style.STROKE);

        leftRadius[0] = leftRadius[1] = leftRadius[6] = leftRadius[7] = boxRadius;
        rightRadius[2] = rightRadius[3] = rightRadius[4] = rightRadius[5] = boxRadius;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(boxWidth * boxCount +boxSpace*(boxCount-1),boxHeight);
        for (int i = 0;i < boxCount;i++){
            RectF target = boxs[i];
            if (i == 0) {
                target.left = (boxWidth + boxSpace) * i + boxStrokeWidth;
                target.right = target.left+boxWidth - boxStrokeWidth;
            }else if (i == boxCount-1){
                target.left = (boxWidth + boxSpace) * i;
                target.right = target.left+boxWidth-boxStrokeWidth;
            }else {
                target.left = (boxWidth + boxSpace) * i;
                target.right = target.left+boxWidth;
            }
            target.top = boxStrokeWidth;
            target.bottom = boxHeight-boxStrokeWidth;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        char[] chars = getChars();
        final TextPaint textPaint = getPaint();
        textPaint.setColor(getCurrentTextColor());
        textPaint.drawableState = getDrawableState();
        final Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        for (int i = 0;i < boxCount;i++){
            RectF target = boxs[i];
            boxPaint.setColor(strokeNormalColor);
            if (boxSpace > 0) {
                canvas.drawRoundRect(target,boxRadius,boxRadius,boxPaint);
            }else if (i == 0){
                boxPath.reset();
                boxPath.addRoundRect(target,leftRadius,Path.Direction.CW);
                canvas.drawPath(boxPath,boxPaint);
            }else if (i == boxCount-1){
                boxPath.reset();
                boxPath.addRoundRect(target,rightRadius,Path.Direction.CW);
                canvas.drawPath(boxPath,boxPaint);
            }else {
                canvas.drawRect(target, boxPaint);
            }
            if (i < chars.length) {
                int baseLine = (int) ((target.bottom + target.top - fontMetrics.bottom - fontMetrics.top) / 2.0f);
                if (charDisplay) {
                    float width = textPaint.measureText(chars,i,1);
                    canvas.drawText(chars, i, 1, target.centerX() - width / 2, baseLine, textPaint);
                }else {
                    float width = textPaint.measureText(replaceChar);
                    canvas.drawText(replaceChar,target.centerX() - width / 2, baseLine, textPaint);
                }
            }
        }
        if (chars.length < boxCount) {
            boxPaint.setColor(strokeInputingColor);
            if (boxSpace > 0){
                canvas.drawRoundRect(boxs[chars.length],boxRadius,boxRadius,boxPaint);
            }else if (chars.length == 0){
                boxPath.reset();
                boxPath.addRoundRect(boxs[chars.length],leftRadius,Path.Direction.CW);
                canvas.drawPath(boxPath,boxPaint);
            }else if (chars.length == boxCount-1){
                boxPath.reset();
                boxPath.addRoundRect(boxs[chars.length],rightRadius,Path.Direction.CW);
                canvas.drawPath(boxPath,boxPaint);
            }else {
                canvas.drawRect(boxs[chars.length], boxPaint);
            }
        }
    }

    private char[] getChars(){
        Editable editable = getText();
        return editable == null?EMPTY:editable.toString().toCharArray();
    }

    public void setCharDisplay(boolean charDisplay) {
        this.charDisplay = charDisplay;
        invalidate();
    }

    public void setReplaceChar(String replaceChars) {
        replaceChar = TextUtils.isEmpty(replaceChars)?REPLACECHAR_DEFAULT:replaceChars.substring(0,1);
        invalidate();
    }

    public boolean isCharDisplay() {
        return charDisplay;
    }

    public boolean isCharAllCaps() {
        return charAllCaps;
    }

    /**
     * 获取输入的数据文字
     * @return
     */
    public String getInputCode(){
        return new String(getChars());
    }

    private static final class AllCaps implements InputFilter {

        private static AllCaps instance;

        private static char[] LOWER = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z' };

        private static char[] UPPER = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z' };

        public static AllCaps getInstance(){
            if (instance == null){
                synchronized (AllCaps.class){
                    if (instance == null){
                        instance = new AllCaps();
                    }
                }
            }
            return instance;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (source == null){
                return "";
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0,len = source.length();i < len;i++){
                char c = source.charAt(i);
                boolean find = false;
                for (int j = 0,llen = LOWER.length;j < llen;j++){
                    if (LOWER[j] == c){
                        sb.append(UPPER[j]);
                        find = true;
                    }
                }
                if (!find){
                    sb.append(c);
                }
            }
            return sb;
        }
    }

}
