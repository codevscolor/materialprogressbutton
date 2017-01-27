package com.codevscolor.util;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Simple utility class
 */

public class Utils {

    public static int dpToPx(float dp, Resources resources){
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }
}
