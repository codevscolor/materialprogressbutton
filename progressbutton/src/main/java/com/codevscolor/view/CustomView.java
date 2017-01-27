package com.codevscolor.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.codevscolor.util.UserConfig;

public class CustomView extends RelativeLayout{

    final int disabledBackgroundColor = Color.parseColor("#E2E2E2");
    int beforeBackground;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context,AttributeSet attributeSet) {
        super(context,attributeSet);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(2* UserConfig.getInstance().getHeight(), 2*UserConfig.getInstance().getHeight());
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if(enabled)
            setBackgroundColor(beforeBackground);
        else
            setBackgroundColor(disabledBackgroundColor);
        invalidate();
    }

    boolean animation = false;

    @Override
    protected void onAnimationStart() {
        super.onAnimationStart();
        animation = true;
    }

    @Override
    protected void onAnimationEnd() {
        super.onAnimationEnd();
        animation = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(animation)
            invalidate();
    }
}