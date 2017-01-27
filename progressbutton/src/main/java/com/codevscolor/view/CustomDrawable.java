package com.codevscolor.view;

import android.graphics.drawable.GradientDrawable;

public class CustomDrawable extends GradientDrawable {

    private int mStrokeWidth;
    private int mStrokeColor;
    private int mColor;

    //setter


    public void setStrokeWidth(int width){
        mStrokeWidth = width;
        this.setStroke(width,getStrokeColor());
    }

    public void setStrokeColor(int strokeColor){
        mStrokeColor = strokeColor;
        this.setStroke(getStrokeWidth(),strokeColor);
    }

    public void setDrawableColor(int color){
        mColor = color;
        this.setColor(color);
    }



    //getter

    public int getStrokeColor(){
        return mStrokeColor;
    }


    public int getStrokeWidth(){
        return mStrokeWidth;
    }
}
