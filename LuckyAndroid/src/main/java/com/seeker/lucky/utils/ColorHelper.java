package com.seeker.lucky.utils;

import android.content.res.ColorStateList;
import android.graphics.Color;

import java.util.Random;

/**
 * @author Seeker
 * @date 2018/7/25/025  17:54
 * @describe 颜色变化辅助
 */
public final class ColorHelper {

    private static final Random RANDOM = new Random();

    private ColorHelper(){
        throw new IllegalStateException("no need instance.");
    }

    /**
     * 生成随机颜色
     * @return
     */
    public static int getRandomColor(){
        int a = RANDOM.nextInt(256);
        int r = RANDOM.nextInt(256);
        int g = RANDOM.nextInt(256);
        int b = RANDOM.nextInt(256);
        return Color.argb(a,r,g,b);
    }

    /**
     * 判断是否为ARGB格式的十六进制颜色，例如：FF990587
     * @param string String
     * @return boolean
     */
    public static boolean isColorString(String string) {
        try {
            Color.parseColor(string);
            return true;
        }catch (IllegalArgumentException e){
            return false;
        }
    }

    /**
     * 颜色加深
     * @param colorInt ARGB颜色值
     * @param darkValue 0~255 加深范围
     * @return
     */
    public static int translateDark(int colorInt, int darkValue) {
        String argbColor = intToString(colorInt);
        return translateDark(argbColor,darkValue);
    }

    /**
     * 颜色加深
     * @param argbColor ARGB颜色值
     * @param darkValue 0-255 加深范围
     * @return
     */
    public static int translateDark(String argbColor, int darkValue) {
        int startAlpha = Integer.parseInt(argbColor.substring(0, 2), 16);
        int startRed = Integer.parseInt(argbColor.substring(2, 4), 16);
        int startGreen = Integer.parseInt(argbColor.substring(4, 6), 16);
        int startBlue = Integer.parseInt(argbColor.substring(6), 16);
        startRed -= darkValue;
        startGreen -= darkValue;
        startBlue -= darkValue;
        if (startRed < 0) startRed = 0;
        if (startGreen < 0) startGreen = 0;
        if (startBlue < 0) startBlue = 0;
        return Color.argb(startAlpha, startRed, startGreen, startBlue);
    }

    //颜色变浅，可调度数：0~255
    public static int translateLight(int colorInt, int lightValue) {
        String argbColor = intToString(colorInt);
        return translateLight(argbColor,lightValue);
    }

    //颜色变浅，可调度数：0~255
    public static int translateLight(String color, int lightValue) {
        int startAlpha = Integer.parseInt(color.substring(0, 2), 16);
        int startRed = Integer.parseInt(color.substring(2, 4), 16);
        int startGreen = Integer.parseInt(color.substring(4, 6), 16);
        int startBlue = Integer.parseInt(color.substring(6), 16);

        startRed += lightValue;
        startGreen += lightValue;
        startBlue += lightValue;

        if (startRed > 255) startRed = 255;
        if (startGreen > 255) startGreen = 255;
        if (startBlue > 255) startBlue = 255;

        return Color.argb(startAlpha, startRed, startGreen, startBlue);
    }

    //透明度加强，可调度数：0~255
    public static int lightAlpha(int colorInt, int darkValue) {
        String argbColor = intToString(colorInt);
        return lightAlpha(argbColor,darkValue);
    }

    //透明度加强，可调度数：0~255
    public static int lightAlpha(String argbColor, int darkValue) {
        int startAlpha = Integer.parseInt(argbColor.substring(0, 2), 16);
        int startRed = Integer.parseInt(argbColor.substring(2, 4), 16);
        int startGreen = Integer.parseInt(argbColor.substring(4, 6), 16);
        int startBlue = Integer.parseInt(argbColor.substring(6), 16);
        startAlpha -= darkValue;
        if (startAlpha < 0) startAlpha = 0;
        return Color.argb(startAlpha, startRed, startGreen, startBlue);
    }

    //不透明度加强，可调度数：0~255
    public static int darkAlpha(int colorInt, int addValue) {
        String argbColor = intToString(colorInt);
        return darkAlpha(argbColor,addValue);
    }

    //不透明度加强，可调度数：0~255
    public static int darkAlpha(String color, int addValue) {
        int startAlpha = Integer.parseInt(color.substring(0, 2), 16);
        int startRed = Integer.parseInt(color.substring(2, 4), 16);
        int startGreen = Integer.parseInt(color.substring(4, 6), 16);
        int startBlue = Integer.parseInt(color.substring(6), 16);
        startAlpha += addValue;
        if (startAlpha > 255) startAlpha = 255;
        return Color.argb(startAlpha, startRed, startGreen, startBlue);
    }

    //将10进制颜色（int）值转换成16进制(String)
    public static String intToString(int value) {
        String hexString = Integer.toHexString(value);
        if (hexString.length() == 1) {
            hexString = "0" + hexString;
        }
        return hexString;
    }

    public static ColorStateList createCheckedColorStateList(int normal,int checked){
        int[] colors = new int[]{checked, normal};
        int[][] states = new int[][]{
                {android.R.attr.state_checked},
                {}
        };
        return new ColorStateList(states, colors);
    }


}
