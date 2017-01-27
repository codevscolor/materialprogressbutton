package com.codevscolor.demoapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.codevscolor.ProgressButton;
import com.codevscolor.callback.OnProgressButtonClickListener;


public class MainActivity extends Activity {
    private ProgressButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (ProgressButton) findViewById(R.id.progress_button);

        button.setOnProgressButtonClickListener(new OnProgressButtonClickListener() {
            @Override
            public void onClick() {

                if (!button.isProgressEnable()) {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            button.stopAnimation(false);
                        }
                    }, 2000);
                }
            }
        });


    }

    private static class SampleView extends View {

        // CONSTRUCTOR
        public SampleView(Context context) {
            super(context);
            setFocusable(true);

        }

        @Override
        protected void onDraw(Canvas canvas) {
            Paint paint = new Paint();

            canvas.drawColor(Color.TRANSPARENT);


            // you need to insert a image flower_blue into res/drawable folder
            paint.setFilterBitmap(true);
            Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),
                    R.drawable.wood);

            Bitmap croppedBmp = Bitmap.createBitmap(bitmapOrg, 0, 0,
                    bitmapOrg.getWidth(), bitmapOrg.getHeight() / 2);
            int h = bitmapOrg.getHeight();
            canvas.drawBitmap(bitmapOrg, 0, 0, paint);
            canvas.drawBitmap(croppedBmp, 0, h, paint);

        }

    }
}
