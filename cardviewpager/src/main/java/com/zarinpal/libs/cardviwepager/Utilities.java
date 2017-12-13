package com.zarinpal.libs.cardviwepager;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Utilities Created by farshid roohi on 12/13/17.
 */

public class Utilities {

    public static int dpToPx(Context context, int dp) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }
}
