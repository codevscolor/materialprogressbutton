package com.codevscolor.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

import com.codevscolor.util.UserConfig;
import com.codevscolor.util.Utils;


public class ProgressBarCircularIndeterminate extends CustomView {

    static int backgroundColor = Color.parseColor("#1E88E5");

    public static void setWheelColor(int color) {
        backgroundColor = color;
    }

    public ProgressBarCircularIndeterminate(Context context) {
        super(context);
        setAttributes();

    }


    protected void setAttributes() {
        setMinimumHeight(UserConfig.getInstance().getWidth());
        setMinimumWidth(UserConfig.getInstance().getWidth());
        setBackgroundColor(Color.parseColor("#1E88E5"));

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSecondAnimation(canvas);
        invalidate();

    }


    int arcD = 1;
    int arcO = 0;
    float rotateAngle = 0;
    int limite = 0;

    private void drawSecondAnimation(Canvas canvas) {
        if (arcO == limite)
            arcD += 6;
        if (arcD >= 290 || arcO > limite) {
            arcO += 6;
            arcD -= 6;
        }
        if (arcO > limite + 290) {
            limite = arcO;
            arcO = limite;
            arcD = 1;
        }
        rotateAngle += 4;
        canvas.rotate(rotateAngle, getWidth() / 2, getHeight() / 2);

        Bitmap bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas temp = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(backgroundColor);
        temp.drawArc(new RectF(0, 0, getWidth(), getHeight()), arcO, arcD, true, paint);

        Paint transparentPaint = new Paint();
        transparentPaint.setAntiAlias(true);
        transparentPaint.setColor(getResources().getColor(android.R.color.transparent));
        transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        temp.drawCircle(getWidth() / 2, getHeight() / 2, (getWidth() / 2) - Utils.dpToPx(4, getResources()), transparentPaint);

        canvas.drawBitmap(bitmap, 0, 0, new Paint());
    }


    // Set color of background
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        if (isEnabled())
            beforeBackground = backgroundColor;
        this.backgroundColor = color;
    }

}
