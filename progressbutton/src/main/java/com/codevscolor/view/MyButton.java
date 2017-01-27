package com.codevscolor.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.codevscolor.pb.R;
import com.codevscolor.util.UserConfig;
import com.codevscolor.callback.ButtonCallback;

public class MyButton extends ImageButton {
    private int height;
    private int width;
    private int circle_width = 60;
    private int circle_height = 60;
    private int circle_corner = 60;
    private int duration = 500;

    private int button_corner = 2;
    private int colorLight;
    private int colorDark;

    private CustomDrawable buttonBackDrawable;
    private CustomDrawable buttonBackDrawablePressed;
    private ButtonCallback mCallback;
    private Animation animationFadeIn;

    public MyButton() {
        super(null);
    }

    public MyButton(Context context) {
        super(context);
        init(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void setCallback(ButtonCallback callback) {
        this.mCallback = callback;
    }

    private boolean isEnable = true;

    private boolean isProgressEnable(){
        return UserConfig.getInstance().getProgressStatus();
    }

    public void convertToRect(boolean flag) {
        if(isProgressEnable())
            convertBackToRect(flag);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;

        if (isEnable) {
            circle_width = circle_height = circle_corner = height + height;
            isEnable = false;
        }
    }

    @Override
    public void setOnClickListener(final OnClickListener l) {

        super.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //convertToCircle();
                l.onClick(v);
            }
        });
    }

    private void init(Context context) {

        animationFadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);

        colorLight = UserConfig.getInstance().getButtonColor();
        colorDark = UserConfig.getInstance().getButtonPressedColor();
        buttonBackDrawable = createDrawable(UserConfig.getInstance().getButtonColor(), button_corner, 0);
        buttonBackDrawablePressed = createDrawable(UserConfig.getInstance().getButtonPressedColor(), button_corner, 0);
        StateListDrawable background = new StateListDrawable();
        background.addState(new int[]{android.R.attr.state_pressed}, buttonBackDrawablePressed);
        background.addState(StateSet.WILD_CARD, buttonBackDrawable);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
            setBackgroundDrawable(background);
        } else {
            setBackground(background);
        }

    }

    private CustomDrawable createDrawable(int color, int cornerRadius, int strokeWidth) {
        CustomDrawable drawable = new CustomDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setDrawableColor(color);
        drawable.setCornerRadius(cornerRadius);
        drawable.setStrokeColor(color);
        drawable.setStrokeWidth(strokeWidth);
        return drawable;
    }

    public void convertToCircle() {
        UserConfig.getInstance().setProgress(true);

        ObjectAnimator cornerAnimation =
                ObjectAnimator.ofFloat(buttonBackDrawable, "cornerRadius", button_corner, circle_corner);

        ObjectAnimator bgCornerAnimation =
                ObjectAnimator.ofFloat(buttonBackDrawablePressed, "cornerRadius", button_corner, circle_corner);

        AnimatorSet animatorSet = new AnimatorSet();


        ValueAnimator heightAnimation = ValueAnimator.ofInt(height, circle_height);
        heightAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = getLayoutParams();
                layoutParams.height = val;
                setLayoutParams(layoutParams);
            }
        });


        ValueAnimator widthAnimation = ValueAnimator.ofInt(width, circle_height);
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = getLayoutParams();
                layoutParams.width = val;
                setLayoutParams(layoutParams);
            }
        });

        int resultColor = UserConfig.getInstance().getButtonColor();

        ObjectAnimator bgColorAnimation = ObjectAnimator.ofInt(buttonBackDrawable, "color", resultColor);
        bgColorAnimation.setEvaluator(new ArgbEvaluator());


      /*  ObjectAnimator bgColorAnimation = ObjectAnimator.ofInt(buttonBackDrawable, "color", colorLight, colorDark);
        bgColorAnimation.setEvaluator(new ArgbEvaluator());

        ObjectAnimator bgColorPressedAnimation = ObjectAnimator.ofInt(buttonBackDrawablePressed, "color", colorDark, colorDark);
        bgColorAnimation.setEvaluator(new ArgbEvaluator());*/

        animatorSet.setDuration(duration);
        animatorSet.playTogether(cornerAnimation, widthAnimation, heightAnimation, bgCornerAnimation,bgColorAnimation);
        animatorSet.start();

        setImageResource(UserConfig.getInstance().getProgressDrawableId());

        //startAnimation(animationFadeIn);

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //now show progressbar
                mCallback.onAnimationComplete(true);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }

        });
    }


    private void convertBackToRect(final boolean isSuccess) {
        UserConfig.getInstance().setProgress(false);

        ObjectAnimator cornerAnimation =
                ObjectAnimator.ofFloat(buttonBackDrawable, "cornerRadius", circle_corner, button_corner);

        ObjectAnimator backCornerAnimation =
                ObjectAnimator.ofFloat(buttonBackDrawablePressed, "cornerRadius", circle_corner, button_corner);

        AnimatorSet animatorSet = new AnimatorSet();


        ValueAnimator heightAnimation = ValueAnimator.ofInt(circle_height, UserConfig.getInstance().getHeight());
        heightAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = getLayoutParams();
                layoutParams.height = val;
                setLayoutParams(layoutParams);
            }
        });


        ValueAnimator widthAnimation = ValueAnimator.ofInt(circle_height, UserConfig.getInstance().getWidth());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = getLayoutParams();
                layoutParams.width = val;
                setLayoutParams(layoutParams);
            }
        });

        //todo: change color drawable according to success failure
        int resultColor;
        if(isSuccess)
            resultColor = UserConfig.getInstance().getSuccessColor();
        else
            resultColor = UserConfig.getInstance().getFailedColor();

        ObjectAnimator bgColorAnimation = ObjectAnimator.ofInt(buttonBackDrawable, "color", colorDark, resultColor);
        bgColorAnimation.setEvaluator(new ArgbEvaluator());

        ObjectAnimator bgColorPressedAnimation = ObjectAnimator.ofInt(buttonBackDrawablePressed, "color", colorDark, resultColor);
        bgColorAnimation.setEvaluator(new ArgbEvaluator());

        animatorSet.setDuration(duration);
        animatorSet.playTogether(cornerAnimation, widthAnimation, heightAnimation, bgColorAnimation, backCornerAnimation,bgColorPressedAnimation);
        animatorSet.start();

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //now show progressbar
                if (!isSuccess)
                    setImageResource(UserConfig.getInstance().getFailedDrawableId());
                else
                    setImageResource(UserConfig.getInstance().getSuccessDrawableId());
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }

        });
    }
}