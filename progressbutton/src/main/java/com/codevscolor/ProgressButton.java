package com.codevscolor;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codevscolor.callback.OnProgressButtonClickListener;
import com.codevscolor.pb.R;
import com.codevscolor.callback.ButtonCallback;
import com.codevscolor.util.UserConfig;
import com.codevscolor.view.MyButton;
import com.codevscolor.view.ProgressBarCircularIndeterminate;


public class ProgressButton extends RelativeLayout implements ButtonCallback {

    MyButton button;
    private static boolean start = false;
    private OnProgressButtonClickListener clickListener = null;
    private ProgressBarCircularIndeterminate progressBarCircularIndeterminate = null;


    public ProgressButton(Context context) {
        super(context);
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        getUserDefinedAttributes(context, attrs, 0);
        init();
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        getUserDefinedAttributes(context, attrs, defStyle);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        addButton();
    }

    public boolean isProgressEnable() {
        return UserConfig.getInstance().getProgressStatus();
    }

    private void getUserDefinedAttributes(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.progressButton, defStyle, 0);
        try {
            UserConfig.getInstance().setButtonColor(a.getColor(R.styleable.progressButton_buttonColor, 0));
            UserConfig.getInstance().setButtonPressedColor(a.getColor(R.styleable.progressButton_buttonPressedColor, 0));
            UserConfig.getInstance().setProgressBarColor(a.getColor(R.styleable.progressButton_progressColor, 0));
            UserConfig.getInstance().setSuccessDrawableId(a.getResourceId(R.styleable.progressButton_success_icon, R.drawable.emptydrawable));
            UserConfig.getInstance().setFailedDrawableId(a.getResourceId(R.styleable.progressButton_failure_icon, R.drawable.emptydrawable));
            UserConfig.getInstance().setProgressDrawableId(a.getResourceId(R.styleable.progressButton_progress_icon, R.drawable.emptydrawable));
            UserConfig.getInstance().setSuccessColor(a.getColor(R.styleable.progressButton_successColor, 0));
            UserConfig.getInstance().setFailedColor(a.getColor(R.styleable.progressButton_failedColor, 0));
            UserConfig.getInstance().setWidth(a.getInt(R.styleable.progressButton_buttonWidth, 0));
            UserConfig.getInstance().setHeight(a.getInt(R.styleable.progressButton_buttonHeight, 0));
            UserConfig.getInstance().setTitle(a.getString(R.styleable.progressButton_buttonTitle));
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void init() {
        button = new MyButton(getContext());
        button.setCallback(this);
        RelativeLayout.LayoutParams imageparams = new RelativeLayout.LayoutParams(
                UserConfig.getInstance().getWidth(), UserConfig.getInstance().getHeight());
        imageparams.addRule(RelativeLayout.CENTER_IN_PARENT);
        final TextView textView = new TextView(getContext());
        textView.setText(UserConfig.getInstance().getTitle());
        textView.setTextColor(getResources().getColor(R.color.progressbutton_textColor));

        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (5 * scale + 0.5f);
        button.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);

        addView(button, imageparams);
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        textView.setTextSize(15);
        textView.setTypeface(null, Typeface.BOLD);
        addView(textView, imageparams);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setVisibility(GONE);
                if (clickListener != null)
                    clickListener.onClick();

                if (!start) {
                    startButtonAnimation();
                    start = true;
                } else {
                    start = false;
                    stopAnimation(false);
                }
            }
        });

    }

    private void addButton() {

    }

    private void startButtonAnimation() {
        button.convertToCircle();
    }

    public void stopAnimation(boolean success) {
        button.convertToRect(success);
        if (progressBarCircularIndeterminate != null) {
            removeView(progressBarCircularIndeterminate);
        }
    }

    private void addProgressbar() {
        ProgressBarCircularIndeterminate.setWheelColor(UserConfig.getInstance().getProgressBarColor());
        progressBarCircularIndeterminate = new ProgressBarCircularIndeterminate(getContext());

        progressBarCircularIndeterminate.setEnabled(true);
        progressBarCircularIndeterminate.setScrollBarSize(1);

        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params1.addRule(RelativeLayout.CENTER_IN_PARENT);

        addView(progressBarCircularIndeterminate, params1);
    }

    @Override
    public void onAnimationComplete(boolean showProgressBar) {
        if (showProgressBar && start)
            addProgressbar();
    }

    public void setOnProgressButtonClickListener(OnProgressButtonClickListener listener) {
        this.clickListener = listener;
    }
}
