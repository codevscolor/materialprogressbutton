package com.codevscolor.util;

/**
 * Singleton class to store user configured values
 */

public class UserConfig {
    private static UserConfig mInstance = null;
    private int mWidth = 0;
    private int mHeight = 0;
    private int mButtonColor = 0;
    private int mButtonPressedColor = 0;
    private int mProgressBarColor = 0;
    private int mSuccessDrawableId;
    private int mFailedDrawableId;
    private int mProgressDrawableId;
    private boolean isProgressEnable = false;
    private int mSuccessColor = 0;
    private int mFailedColor = 0;
    private String mTitle;

    private UserConfig() {
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {
        this.mWidth = width;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int height) {
        this.mHeight = height;
    }

    public void setButtonColor(int color) {
        this.mButtonColor = color;
    }

    public int getButtonColor() {
        return mButtonColor;
    }

    public void setButtonPressedColor(int color) {
        this.mButtonPressedColor = color;
    }

    public int getButtonPressedColor() {
        return mButtonPressedColor;
    }

    public void setProgressBarColor(int color) {
        this.mProgressBarColor = color;
    }

    public int getProgressBarColor() {
        return mProgressBarColor;
    }

    public void setSuccessDrawableId(int id) {
        this.mSuccessDrawableId = id;
    }

    public int getSuccessDrawableId() {
        return mSuccessDrawableId;
    }

    public void setFailedDrawableId(int id) {
        this.mFailedDrawableId = id;
    }

    public int getFailedDrawableId() {
        return mFailedDrawableId;
    }

    public void setProgressDrawableId(int id) {
        this.mProgressDrawableId = id;
    }

    public int getProgressDrawableId() {
        return mProgressDrawableId;
    }

    public void setSuccessColor(int color) {
        this.mSuccessColor = color;
    }

    public void setFailedColor(int color) {
        this.mFailedColor = color;
    }

    public int getSuccessColor() {
        return this.mSuccessColor;
    }

    public int getFailedColor() {
        return this.mFailedColor;
    }

    public void setProgress(boolean enable) {
        this.isProgressEnable = enable;
    }

    public boolean getProgressStatus() {
        return isProgressEnable;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public static UserConfig getInstance() {
        if (mInstance == null) {
            mInstance = new UserConfig();
        }
        return mInstance;
    }
}
